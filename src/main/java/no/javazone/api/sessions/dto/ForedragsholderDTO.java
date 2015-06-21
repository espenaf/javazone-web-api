package no.javazone.api.sessions.dto;

import java.net.URI;

public class ForedragsholderDTO {
    public final String navn;
    public final URI bildeUri;

    public ForedragsholderDTO(String navn, URI bildeUri) {
        this.navn = navn;
        this.bildeUri = bildeUri;
    }

    private ForedragsholderDTO() {
        this.navn = null;
        this.bildeUri = null;
    }
}
