package no.javazone.api.sessions;

public class SessionDetaljerDTO {
    public final String tittel;
    public final String starter;
    public final String stopper;
    public final String niva;

    public SessionDetaljerDTO(String tittel, String starter, String stopper, String niva) {
        this.tittel = tittel;
        this.starter = starter;
        this.stopper = stopper;
        this.niva = niva;
    }
}
