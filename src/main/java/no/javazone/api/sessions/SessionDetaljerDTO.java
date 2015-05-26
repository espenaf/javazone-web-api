package no.javazone.api.sessions;

public class SessionDetaljerDTO {
    public final String tittel;
    public final String starter;
    public final String stopper;
    public final String niva;
    public final String oppsummering;
    public final String beskrivelse;

    public SessionDetaljerDTO(
            String tittel,
            String starter,
            String stopper,
            String niva,
            String oppsummering,
            String beskrivelse
    ) {
        this.tittel = tittel;
        this.starter = starter;
        this.stopper = stopper;
        this.niva = niva;
        this.oppsummering = oppsummering;
        this.beskrivelse = beskrivelse;
    }

    private SessionDetaljerDTO() {
        this.tittel = null;
        this.starter = null;
        this.stopper = null;
        this.niva = null;
        this.oppsummering = null;
        this.beskrivelse = null;
    }

}
