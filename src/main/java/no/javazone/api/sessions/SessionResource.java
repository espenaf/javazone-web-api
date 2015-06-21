package no.javazone.api.sessions;

import io.dropwizard.jersey.caching.CacheControl;
import no.javazone.api.sessions.dto.SessionDTOMapper;
import no.javazone.api.sessions.dto.SessionDetaljerDTOMapper;
import no.javazone.http.PathResolver;
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
import java.util.concurrent.TimeUnit;

@Path("/events/{eventId}/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private PathResolver pathResolver;
    private SessionRepository sessionRepository;

    @Context
    private UriInfo uriInfo;

    public SessionResource(PathResolver pathResolver, SessionRepository sessionRepository) {
        this.pathResolver = pathResolver;
        this.sessionRepository = sessionRepository;
    }

    @GET
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
    public Response getSessions(@PathParam("eventId") String eventSlug) {
        Optional<Event> eventOptional = sessionRepository.getSessions(eventSlug);

        return eventOptional
            .map(x -> SessionDTOMapper.toSessionDTOs(
                    x, pathResolver.path(uriInfo), pathResolver.getContextRoot()))
            .map(x -> Response.ok().entity(x).build())
            .orElse(Response.status(503).build());
    }

    @GET
    @Path("/{sessionId}")
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
    public Response getSession(
            @PathParam("eventId") String eventSlug,
            @PathParam("sessionId") String sessionId
    ) {
        Optional<Session> sessionOptional = sessionRepository.getSession(eventSlug, new SessionId(sessionId));

        return sessionOptional
                .map(session -> SessionDetaljerDTOMapper
                        .toSessionDetaljerDTO(session, pathResolver.getContextRoot()))
                .map(x -> Response.ok().entity(x).build())
                .orElse(Response.status(503).build());

    }

}
