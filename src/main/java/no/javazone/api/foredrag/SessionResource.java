package no.javazone.api.foredrag;

import no.javazone.ems.Foredragsholder;
import no.javazone.ems.Session;
import no.javazone.sessions.SessionRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
        List<Session> sessions = sessionRepository.getSessions(eventSlug).getSessions();

        List<SessionDTO> response = sessions
                .stream()
                .map(foredrag -> new SessionDTO(foredrag.getTittel(), toDTO(foredrag.getForedragsholdere())))
                .collect(Collectors.toList());

        return Response.ok().entity(response).build();
    }

    private List<ForedragsholderDTO> toDTO(List<Foredragsholder> foredragsholdere) {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn(), foredragsholder.getGravatarUrl()))
                .collect(Collectors.toList());
    }
}
