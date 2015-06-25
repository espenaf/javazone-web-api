package no.javazone.speaker;

import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static org.junit.Assert.assertEquals;

public class SpeakerImageCacheTest {

    @Test
    public void formater_dato_til_http_date_korrekt() throws Exception {
        String date = "Mon, 28 Apr 2014 21:34:20 GMT";
        String pattern = "EEE, dd MMM yyyy HH:mm:ss z";

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        Date javaDate = format.parse(date);

        assertEquals(SpeakerImageCache.getDateAsString(javaDate), date);
    }
}