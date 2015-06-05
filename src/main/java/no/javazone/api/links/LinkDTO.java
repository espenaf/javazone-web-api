package no.javazone.api.links;

import java.net.URI;

public class LinkDTO {
    public final String rel;
    public final URI href;

    public LinkDTO(String rel, URI href) {
        this.rel = rel;
        this.href = href;
    }

    private LinkDTO() {
        this.rel = null;
        this.href = null;
    }
}
