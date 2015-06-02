package no.javazone.http;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static no.javazone.http.SubdomeneSjekker.*;

public class AddCorsHeaderToResponse implements ContainerResponseFilter {

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext)
            throws IOException
    {
        MultivaluedMap<String, String> requestHeaders = requestContext.getHeaders();
        List<String> originValues = requestHeaders.getOrDefault("Origin", new ArrayList<>());

        if (originValues.size() > 0) {
            String origin = originValues.get(0);
            if (erEtJavaZoneSubdomene(origin)) {
                responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            }
        }
    }

}
