package no.javazone.api.sessions;

import no.javazone.api.LinkDTO;
import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.Session;

import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SessionDTOMapper {
    public static List<SessionDTO> toSessionDTOs(Event event, UriBuilder absolutePathBuilder) {
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
                        createLinks(absolutePathBuilder, session)))
                .collect(Collectors.toList());
    }

    private static List<LinkDTO> createLinks(UriBuilder absolutePathBuilder, Session session) {
        ArrayList<LinkDTO> links = new ArrayList<>();
        links.add(new LinkDTO("detaljer", absolutePathBuilder.path(session.getId().getValue()).build()));
        return links;
    }

    private static List<ForedragsholderDTO> toForedragsholderDTO(List<Foredragsholder> foredragsholdere) {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn(),
                        foredragsholder.getGravatarUrl()))
                .collect(Collectors.toList());
    }
}
