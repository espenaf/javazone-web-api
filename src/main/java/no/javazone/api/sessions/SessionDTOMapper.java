package no.javazone.api.sessions;

import no.javazone.api.LinkDTO;
import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.Session;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SessionDTOMapper {
    public static List<SessionDTO> toSessionDTOs(Event event, UriInfo uriInfo) {
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
                        createLinks(uriInfo, session)))
                .collect(Collectors.toList());
    }

    public static SessionDetaljerDTO toSessionDetaljerDTO(Session session) {
        return new SessionDetaljerDTO(
                session.getTittel(),
                session.getSlot().getStarter(),
                session.getSlot().getStopper(),
                session.getNiva(),
                session.getOppsummering());
    }

    private static List<ForedragsholderDTO> toForedragsholderDTO(List<Foredragsholder> foredragsholdere) {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn(),
                        foredragsholder.getGravatarUrl()))
                .collect(Collectors.toList());
    }

    private static List<LinkDTO> createLinks(UriInfo uriInfo, Session session) {
        ArrayList<LinkDTO> links = new ArrayList<>();

        links.add(createDetaljerLink(session, uriInfo));

        return links;
    }

    private static LinkDTO createDetaljerLink(Session session, UriInfo uriInfo) {
        UriBuilder absolutePathBuilder = uriInfo.getAbsolutePathBuilder();
        return new LinkDTO("detaljer", absolutePathBuilder.path(session.getId().getValue()).build());
    }
}
