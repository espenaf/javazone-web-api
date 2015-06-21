package no.javazone.api.sessions.dto;

import no.javazone.sessions.Foredragsholder;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class SpeakerBildeUriCreator {
    public static URI createBildeUrl(Foredragsholder foredragsholder, URI contextRoot) {
        //  TODO: Harry kode, får rydde på sikt
        if (foredragsholder.getPhotoUri().isPresent()) {
            return getSpeakerImage(foredragsholder, contextRoot);
        } else if (foredragsholder.getGravatarUri().isPresent()) {
            return foredragsholder.getGravatarUri().get();
        } else {
            return null;
        }
    }

    private static URI getSpeakerImage(Foredragsholder foredragsholder, URI contextRoot) {
        UriBuilder absolutePathBuilder = UriBuilder.fromUri(contextRoot);
        return foredragsholder.getPhotoUri()
                .map(photo -> absolutePathBuilder
                        .path("speakers")
                        .path(foredragsholder.getSpeakerId())
                        .path("image")
                        .build())
                .orElse(null);
    }
}
