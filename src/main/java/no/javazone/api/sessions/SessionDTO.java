package no.javazone.api.sessions;

import java.util.List;

public class SessionDTO {
    public final String tittel;
    public final List<ForedragsholderDTO> foredragsholdere;

    public SessionDTO(String tittel, List<ForedragsholderDTO> foredragsholdere) {
        this.tittel = tittel;
        this.foredragsholdere = foredragsholdere;
    }
}
