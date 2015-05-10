package no.javazone.api.foredrag;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Foredrag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/event/{eventId}/foredrag")
@Produces(MediaType.APPLICATION_JSON)
public class ForedragResource {

    private EmsAdapter emsAdapter;

    public ForedragResource(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
    }

    @GET
    public Response getForedrag(@PathParam("eventId") String eventId) {
        List<Foredrag> foredragsliste = emsAdapter.getForedragsliste(eventId);

        List<ForedragDTO> response = foredragsliste
                .stream()
                .map(foredrag -> new ForedragDTO(foredrag.getTittel()))
                .collect(Collectors.toList());

        return Response.ok().entity(response).build();
    }
}
