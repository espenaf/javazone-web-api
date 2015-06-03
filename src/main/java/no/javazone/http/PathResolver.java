package no.javazone.http;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PathResolver {
    private URI contextPath;

    public PathResolver(URI contextPath) {
        this.contextPath = contextPath;
    }

    public URI path(UriInfo uriInfo) {
        return UriBuilder.fromUri(contextPath).path(uriInfo.getPath()).build();
    }
}
