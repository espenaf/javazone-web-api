package no.javazone.api.sessions;

import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;

import java.util.List;
import java.util.stream.Collectors;

class SessionDTOMapper {
    public static List<SessionDTO> toSessionDTOs(Event event) {
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
                        session.getNiva()))
                .collect(Collectors.toList());
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
