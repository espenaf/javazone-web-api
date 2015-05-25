package no.javazone.sessions;

import java.util.List;

public class Session {
    private final SessionId id;
    private final String tittel;
    private final String format;
    private final Slot slot;
    private final List<Foredragsholder> foredragsholdere;
    private final String sprak;
    private final String niva;
    private final String oppsummering;
    private final String beskrivelse;

    public Session(
            SessionId id,
            String tittel,
            String format,
            Slot slot,
            List<Foredragsholder> foredragsholdere,
            String sprak,
            String niva,
            String oppsummering,
            String beskrivelse
    ) {
        this.id = id;
        this.tittel = tittel;
        this.format = format;
        this.slot = slot;
        this.foredragsholdere = foredragsholdere;
        this.sprak = sprak;
        this.niva = niva;
        this.oppsummering = oppsummering;
        this.beskrivelse = beskrivelse;
    }

    public SessionId getId() {
        return id;
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

    public String getSprak() {
        return sprak;
    }

    public String getNiva() {
        return niva;
    }

    public String getOppsummering() {
        return oppsummering;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }
}
