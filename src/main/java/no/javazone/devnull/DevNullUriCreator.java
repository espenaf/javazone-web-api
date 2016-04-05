package no.javazone.devnull;

import no.javazone.sessions.EmsIds;

import java.net.URI;

public class DevNullUriCreator {

    private String baseUrl;

    public DevNullUriCreator(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public URI mapToUri(EmsIds emsIds) {
        return URI.create(String.format(
                "%s/%s/sessions/%s/feedbacks",
                baseUrl,
                emsIds.getEventId(),
                emsIds.getSessionId()));
    }
}
