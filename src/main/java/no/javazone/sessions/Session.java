package no.javazone.sessions;

import java.util.List;

public class Session {
    private String tittel;
    private String format;
    private List<Foredragsholder> foredragsholdere;

    public Session(String tittel, String format, List<Foredragsholder> foredragsholdere) {
        this.tittel = tittel;
        this.format = format;
        this.foredragsholdere = foredragsholdere;
    }

    public String getTittel() {
        return tittel;
    }

    public List<Foredragsholder> getForedragsholdere() {
        return foredragsholdere;
    }

    public String getFormat() {
        return format;
    }
}
