package no.javazone.api.links;

import java.net.URI;
import java.util.Optional;

public class LinkDTOMapper {
    public static LinkDTO toLinkDTO(String rel, Optional<URI> uriOptional) {
        return uriOptional
                .map(videoUri -> new LinkDTO(rel, videoUri))
                .orElse(null);
    }
}
