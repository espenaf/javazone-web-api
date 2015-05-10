package no.javazone.api.foredrag;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/event/{eventId}/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private EmsAdapter emsAdapter;

    public SessionResource(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
    }

    @GET
    public Response getForedrag(@PathParam("eventId") String eventId) {
        List<Session> sessions = emsAdapter.getSessions(eventId);

        List<SessionDTO> response = new ArrayList<>();

        for (Session session : sessions) {
            List<ForedragsholderDTO> foredragsholdere = session.getForedragsholdere()
                    .stream()
                    .map(foredragsholder -> new ForedragsholderDTO(
                            foredragsholder.getNavn(), foredragsholder.getGravatarUrl()))
                    .collect(Collectors.toList());
            response.add(new SessionDTO(session.getTittel(), foredragsholdere));
        }

        return Response.ok().entity(response).build();
    }
}
