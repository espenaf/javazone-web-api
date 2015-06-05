package no.javazone.api.sessions.dto;

import no.javazone.sessions.Session;

import java.util.List;
import java.util.stream.Collectors;

public class SessionDetaljerDTOMapper {
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
}
