package no.javazone.speaker;

import io.dropwizard.jersey.caching.CacheControl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("/speakers/{speakerId}/image")
public class SpeakerResource {

    private final SpeakerImageCache speakerImageCache;

    public SpeakerResource(SpeakerImageCache speakerImageCache) {
        this.speakerImageCache = speakerImageCache;
    }

    @GET
    @Produces("image/png")
    @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
    public Response getImage(
            @PathParam("speakerId") String speakerId,
            @QueryParam("size") final String size) {
        SpeakerBilde speakerBilde = speakerImageCache.get(speakerId);
        if (speakerBilde == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (size != null && Integer.parseInt(size) > SpeakerBilde.SMALL) {
            return Response.ok(speakerBilde.getStortBilde()).build();
        } else {
            return Response.ok(speakerBilde.getLiteBilde()).build();
        }
    }
}
