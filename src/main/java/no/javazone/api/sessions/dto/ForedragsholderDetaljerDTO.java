package no.javazone.api.sessions.dto;

import java.net.URI;

public class ForedragsholderDetaljerDTO {
    public final String navn;
    public final String bio;
    public final URI bildeUri;

    public ForedragsholderDetaljerDTO(String navn, String bio, URI bildeUri) {
        this.navn = navn;
        this.bio = bio;
        this.bildeUri = bildeUri;
    }

    private ForedragsholderDetaljerDTO() {
        this.navn = null;
        this.bio = null;
        this.bildeUri = null;
    }
}
