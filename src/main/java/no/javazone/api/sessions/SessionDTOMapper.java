package no.javazone.api.sessions;

import no.javazone.api.LinkDTO;
import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.Session;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SessionDTOMapper {
    public static List<SessionDTO> toSessionDTOs(Event event, URI contextPath) {
        return event
                .getSessions()
                .stream()
                .map(session -> new SessionDTO(
                        session.getTittel(),
                        session.getFormat(),
                        session.getSlot().getStarter(),
                        session.getSlot().getStopper(),
                        toForedragsholderDTO(session.getForedragsholdere()),
                        session.getSprak(),
                        session.getNiva(),
                        createLinks(contextPath, session)))
                .collect(Collectors.toList());
    }

    public static SessionDetaljerDTO toSessionDetaljerDTO(Session session) {
        return new SessionDetaljerDTO(
                session.getTittel(),
                session.getSlot().getStarter(),
                session.getSlot().getStopper(),
                session.getNiva(),
                session.getOppsummering(),
                session.getBeskrivelse(),
                mapToForedragsholderDetaljerDTOs(session));
    }

    private static List<ForedragsholderDetaljerDTO> mapToForedragsholderDetaljerDTOs(Session session) {
        return session.getForedragsholdere()
                .stream()
                .map(foredragsholder -> new ForedragsholderDetaljerDTO(
                        foredragsholder.getNavn(),
                        foredragsholder.getBio()))
                .collect(Collectors.toList());
    }

    private static List<ForedragsholderDTO> toForedragsholderDTO(List<Foredragsholder> foredragsholdere) {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn()))
                .collect(Collectors.toList());
    }

    private static List<LinkDTO> createLinks(URI contextPath, Session session) {
        ArrayList<LinkDTO> links = new ArrayList<>();

        links.add(createDetaljerLink(session, contextPath));
        links.add(createVideoLink(session));

        return links;
    }

    private static LinkDTO createVideoLink(Session session) {
        return session.getVideoUri()
                .map(videoUri -> new LinkDTO("video", videoUri))
                .orElse(null);
    }

    private static LinkDTO createDetaljerLink(Session session, URI contextPath) {
        UriBuilder absolutePathBuilder = UriBuilder.fromUri(contextPath);
        return new LinkDTO("detaljer", absolutePathBuilder.path(session.getId().getValue()).build());
    }
}
