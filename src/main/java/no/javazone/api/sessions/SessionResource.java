package no.javazone.api.sessions;

import io.dropwizard.jersey.caching.CacheControl;
import no.javazone.api.sessions.dto.SessionDTOMapper;
import no.javazone.api.sessions.dto.SessionDetaljerDTOMapper;
import no.javazone.devnull.DevNullUriCreator;
import no.javazone.http.PathResolver;
import no.javazone.sessions.Event;
import no.javazone.sessions.Session;
import no.javazone.sessions.SessionId;
import no.javazone.sessions.SessionRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Path("/events/{eventId}/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private PathResolver pathResolver;
    private SessionRepository sessionRepository;
    private DevNullUriCreator devNullUriCreator;

    @Context
    private UriInfo uriInfo;

    public SessionResource(
            PathResolver pathResolver,
            SessionRepository sessionRepository,
            DevNullUriCreator devNullUriCreator) {
        this.pathResolver = pathResolver;
        this.sessionRepository = sessionRepository;
        this.devNullUriCreator = devNullUriCreator;
    }

    @GET
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
    public Response getSessions(@PathParam("eventId") String eventSlug, @HeaderParam("X-Forwarded-Proto") String forwardedProto) {
        Optional<Event> eventOptional = sessionRepository.getSessions(eventSlug);
        URI contextRoot = resolveContextRoot(forwardedProto);

        return eventOptional
            .map(x -> SessionDTOMapper.toSessionDTOs(
                    x,
                    pathResolver.path(uriInfo),
                    contextRoot,
                    devNullUriCreator))
            .map(x -> Response.ok().entity(x).build())
            .orElse(Response.status(503).build());
    }


    @GET
    @Path("/{sessionId}")
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
    public Response getSession(
            @PathParam("eventId") String eventSlug,
            @PathParam("sessionId") String sessionId,
            @HeaderParam("X-Forwarded-Proto") String forwardedProto
    ) {
        Optional<Session> sessionOptional = sessionRepository.getSession(eventSlug, new SessionId(sessionId));
        URI contextRoot = resolveContextRoot(forwardedProto);
        return sessionOptional
                .map(session -> SessionDetaljerDTOMapper
                        .toSessionDetaljerDTO(session, contextRoot, devNullUriCreator))
                .map(x -> Response.ok().entity(x).build())
                .orElse(Response.status(503).build());

    }

    private URI resolveContextRoot(@HeaderParam("X-Forwarded-Proto") String forwardedProto) {
        return UriBuilder.fromUri(pathResolver.getContextRoot())
                .scheme(forwardedProto != null ? forwardedProto : uriInfo.getRequestUri().getScheme()).build();
    }


}
