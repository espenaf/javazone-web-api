package no.javazone.api.foredrag;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Foredragsholder;
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
