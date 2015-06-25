package no.javazone.speaker;

import com.google.common.base.Throwables;
import no.javazone.sessions.Foredragsholder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SpeakerImageCache {

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    private final Map<String, SpeakerBilde> speakerImageCache = new ConcurrentHashMap<>();
    private final Client client;

    public SpeakerImageCache() {
        client = ClientBuilder.newClient();
    }

    public void add(Foredragsholder f) {
        Optional<URI> photoUri = f.getPhotoUri();
        if (photoUri.isPresent()) {
            Response response = time("fetch", () -> fetchImage(f));

            if (response.getStatus() != Response.Status.NOT_MODIFIED.getStatusCode()) {
                byte[] bbb = response.readEntity(byte[].class);

                System.out.println(f.getSpeakerId() + " " + bbb.length + " " + photoUri.get());

                SpeakerBilde speakerBilde = time("convert", () -> new SpeakerBilde(bbb, response.getLastModified()));

                speakerImageCache.put(f.getSpeakerId(), speakerBilde);
            }
        }
    }

    private Response fetchImage(Foredragsholder foredragsholder) {
        final Optional<URI> photoUri = foredragsholder.getPhotoUri();
        WebTarget target = client.target(photoUri.get());
        Invocation.Builder request = target.request();
        final SpeakerBilde speakerBilde = speakerImageCache.get(foredragsholder.getSpeakerId());
        if (speakerBilde != null && speakerBilde.getLastModified() != null) {
            request.header(HttpHeaders.IF_MODIFIED_SINCE, getDateAsString(speakerBilde.getLastModified()));
        }
        return request.get();
    }

    static String getDateAsString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }

    private static <T> T time(String name, Callable<T> callable) {
        long start = System.nanoTime();
        try {
            return callable.call();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        } finally {
            long endTime = System.nanoTime();
            System.out.println(name + " -> " + TimeUnit.NANOSECONDS.toMillis(endTime - start));
        }
    }

    public SpeakerBilde get(String speakerId) {
        return speakerImageCache.get(speakerId);
    }
}
