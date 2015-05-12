package no.javazone.ems;

import java.net.URI;

public class EventMinimal {
    private URI uri;
    private String slug;

    public EventMinimal(URI uri, String slug) {
        this.uri = uri;
        this.slug = slug;
    }

    public URI getUri() {
        return uri;
    }

    public String getSlug() {
        return slug;
    }
}
