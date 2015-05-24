package no.javazone.sessions;

import java.util.List;

public class Session {
    private final String tittel;
    private final String format;
    private final Slot slot;
    private final List<Foredragsholder> foredragsholdere;
    private final String lang;

    public Session(
            String tittel,
            String format,
            Slot slot,
            List<Foredragsholder> foredragsholdere,
            String lang
    ) {
        this.tittel = tittel;
        this.format = format;
        this.slot = slot;
        this.foredragsholdere = foredragsholdere;
        this.lang = lang;
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

    public Slot getSlot() {
        return slot;
    }

    public String getLang() {
        return lang;
    }
}
