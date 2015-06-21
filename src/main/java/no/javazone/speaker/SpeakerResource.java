package no.javazone.speaker;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/speakers/{speakerId}/image")
public class SpeakerResource {

    private final SpeakerImageCache speakerImageCache;

    public SpeakerResource(SpeakerImageCache speakerImageCache) {
        this.speakerImageCache = speakerImageCache;
    }

    @GET
    @Produces("image/png")
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
