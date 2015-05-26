package no.javazone.api.sessions;

import no.javazone.sessions.Event;
import no.javazone.sessions.Session;
import no.javazone.sessions.SessionId;
import no.javazone.sessions.SessionRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/event/{eventId}/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private SessionRepository sessionRepository;

    @Context
    private UriInfo uriInfo;

    public SessionResource(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GET
    public Response getSessions(@PathParam("eventId") String eventSlug) {
        Optional<Event> eventOptional = sessionRepository.getSessions(eventSlug);

        return eventOptional
            .map(x -> SessionDTOMapper.toSessionDTOs(x, uriInfo))
            .map(x -> Response.ok().entity(x).build())
            .orElse(Response.status(503).build());
    }

    @GET
    @Path("/{sessionId}")
    public Response getSession(
            @PathParam("eventId") String eventSlug,
            @PathParam("sessionId") String sessionId
    ) {
        Optional<Session> sessionOptional = sessionRepository.getSession(eventSlug, new SessionId(sessionId));

        return sessionOptional.map(SessionDTOMapper::toSessionDetaljerDTO)
            .map(x -> Response.ok().entity(x).build())
            .orElse(Response.status(503).build());

    }

}
