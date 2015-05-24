package no.javazone.api.sessions;

import java.util.List;

public class SessionDTO {
    public final String tittel;
    public final String format;
    public final String starter;
    public final String stopper;
    public final List<ForedragsholderDTO> foredragsholdere;

    public SessionDTO(
            String tittel,
            String format,
            String starter,
            String stopper,
            List<ForedragsholderDTO> foredragsholdere) {
        this.tittel = tittel;
        this.format = format;
        this.starter = starter;
        this.stopper = stopper;
        this.foredragsholdere = foredragsholdere;
    }
}
