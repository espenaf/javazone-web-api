package no.javazone.api.sessions;

public class ForedragsholderDTO {
    public final String navn;
    public final String bio;

    public ForedragsholderDTO(String navn, String bio) {
        this.navn = navn;
        this.bio = bio;
    }

    private ForedragsholderDTO() {
        this.navn = null;
        this.bio = null;
    }
}
