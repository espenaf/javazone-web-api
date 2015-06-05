package no.javazone.api.sessions;

public class ForedragsholderDetaljerDTO {
    public final String navn;
    public final String bio;

    public ForedragsholderDetaljerDTO(String navn, String bio) {
        this.navn = navn;
        this.bio = bio;
    }

    private ForedragsholderDetaljerDTO() {
        this.navn = null;
        this.bio = null;
    }
}
