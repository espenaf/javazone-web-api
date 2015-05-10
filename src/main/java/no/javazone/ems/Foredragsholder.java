package no.javazone.ems;

public class Foredragsholder {
    private final String navn;
    private final String gravatarUrl;

    public Foredragsholder(String navn, String gravatarUrl) {
        this.navn = navn;
        this.gravatarUrl = gravatarUrl;
    }

    public String getNavn() {
        return navn;
    }

    public String getGravatarUrl() {
        return gravatarUrl;
    }
}
