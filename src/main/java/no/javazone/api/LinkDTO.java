package no.javazone.api;

import java.net.URI;

public class LinkDTO {
    public final String rel;
    public final URI href;

    public LinkDTO(String rel, URI href) {
        this.rel = rel;
        this.href = href;
    }
}
