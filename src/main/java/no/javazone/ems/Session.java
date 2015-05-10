package no.javazone.ems;

import java.util.List;

public class Session {
    private String tittel;
    private List<Foredragsholder> foredragsholdere;

    public Session(String tittel, List<Foredragsholder> foredragsholdere) {
        this.tittel = tittel;
        this.foredragsholdere = foredragsholdere;
    }

    public String getTittel() {
        return tittel;
    }

    public List<Foredragsholder> getForedragsholdere() {
        return foredragsholdere;
    }
}
