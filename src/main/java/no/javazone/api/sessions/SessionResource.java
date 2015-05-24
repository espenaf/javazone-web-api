package no.javazone.api.sessions;

import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.SessionRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/event/{eventId}/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private SessionRepository sessionRepository;

    public SessionResource(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GET
    public Response getForedrag(@PathParam("eventId") String eventSlug) {
        Optional<Event> eventOptional = sessionRepository.getSessions(eventSlug);
        if (eventOptional.isPresent()) {
            List<SessionDTO> response = toSessionDTOs(eventOptional.get());
            return Response.ok().entity(response).build();

        } else {
            return Response.status(503).build();
        }
    }

    private List<SessionDTO> toSessionDTOs(Event event) {
        return event
                .getSessions()
                .stream()
                .map(session -> new SessionDTO(
                        session.getTittel(),
                        session.getFormat(),
                        session.getSlot().getStarter(),
                        session.getSlot().getStopper(),
                        toForedragsholderDTO(session.getForedragsholdere()),
                        session.getLang()))
                .collect(Collectors.toList());
    }

    private List<ForedragsholderDTO> toForedragsholderDTO(List<Foredragsholder> foredragsholdere) {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn(),
                        foredragsholder.getGravatarUrl()))
                .collect(Collectors.toList());
    }
}
