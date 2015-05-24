package no.javazone.api.sessions;

import java.util.List;

public class SessionDTO {
    public final String tittel;
    public final String format;
    public final List<ForedragsholderDTO> foredragsholdere;

    public SessionDTO(String tittel, String format, List<ForedragsholderDTO> foredragsholdere) {
        this.tittel = tittel;
        this.format = format;
        this.foredragsholdere = foredragsholdere;
    }
}
