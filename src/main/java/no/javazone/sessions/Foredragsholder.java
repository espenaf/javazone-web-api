package no.javazone.sessions;

public class Foredragsholder {
    private final String navn;
    private final String bio;

    public Foredragsholder(String navn, String bio) {
        this.navn = navn;
        this.bio = bio;
    }

    public String getNavn() {
        return navn;
    }

    public String getBio() {
        return bio;
    }
}
