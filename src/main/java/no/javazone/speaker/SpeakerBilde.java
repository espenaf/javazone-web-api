package no.javazone.speaker;

import org.joda.time.DateTime;

import java.util.Date;

public class SpeakerBilde {
    private static final int CACHELEVETID_MINUTTER = 60;

    public static final int SMALL = 48;
    public static final int LARGE = 240;

    private final byte[] smallPhoto;
    private final byte[] largePhoto;

    private final DateTime hentet;
    private final Date lastModified;

    public SpeakerBilde(final byte[] uskalertBilde, Date lastModified) {
        this.lastModified = lastModified;
        byte[] firkantBilde = BildeSkalerer.cropTilFirkant(uskalertBilde);

        smallPhoto = BildeSkalerer.skalerPngBilde(firkantBilde, SMALL, SMALL);
        largePhoto = BildeSkalerer.skalerPngBilde(firkantBilde, LARGE, LARGE);

        hentet = DateTime.now();
    }

    public byte[] getLiteBilde() {
        return smallPhoto;
    }

    public byte[] getStortBilde() {
        return largePhoto;
    }

    public boolean erGammelt() {
        return hentet.isBefore(DateTime.now().minusMinutes(CACHELEVETID_MINUTTER));
    }

}
