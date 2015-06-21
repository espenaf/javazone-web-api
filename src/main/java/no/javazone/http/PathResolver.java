package no.javazone.http;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PathResolver {
    private URI contextRoot;

    public PathResolver(URI contextRoot) {
        this.contextRoot = contextRoot;
    }

    public URI path(UriInfo uriInfo) {
        return UriBuilder.fromUri(contextRoot).path(uriInfo.getPath()).build();
    }

    public URI getContextRoot() {
        return contextRoot;
    }
}
