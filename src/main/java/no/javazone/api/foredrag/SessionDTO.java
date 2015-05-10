package no.javazone.api.foredrag;

import java.util.List;

public class SessionDTO {
    public final String tittel;
    public final List<ForedragsholderDTO> foredragsholdere;

    public SessionDTO(String tittel, List<ForedragsholderDTO> foredragsholdere) {
        this.tittel = tittel;
        this.foredragsholdere = foredragsholdere;
    }
}
