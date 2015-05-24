package no.javazone.sessions;

import java.util.List;

public class Session {
    private final String tittel;
    private final String format;
    private final String starter;
    private final String stopper;
    private final List<Foredragsholder> foredragsholdere;

    public Session(
            String tittel,
            String format,
            String starter,
            String stopper,
            List<Foredragsholder> foredragsholdere) {
        this.tittel = tittel;
        this.format = format;
        this.starter = starter;
        this.stopper = stopper;
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

    public String getStarter() {
        return starter;
    }

    public String getStopper() {
        return stopper;
    }
}
