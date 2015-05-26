package no.javazone.api.sessions;

public class ForedragsholderDTO {
    public final String navn;
    public final String gravatarUrl;

    public ForedragsholderDTO(String navn, String gravatarUrl) {
        this.navn = navn;
        this.gravatarUrl = gravatarUrl;
    }

    private ForedragsholderDTO() {
        this.navn = null;
        this.gravatarUrl = null;
    }
}
