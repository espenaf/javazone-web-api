package no.javazone.speaker;

import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.concurrent.ConcurrentHashMap;

public class SpeakerImageCache {

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    private final Map<String, SpeakerBilde> speakerImageCache = new ConcurrentHashMap<>();
    private final Client client;
    private static final Logger logger = LoggerFactory.getLogger(SessionRepository.class);

    public SpeakerImageCache() {
        client = ClientBuilder.newClient();
    }

    public void add(Foredragsholder f, NestedTimer timer) {
        try {
            Optional<URI> photoUri = f.getPhotoUri();
            if (photoUri.isPresent()) {
                Response response = timer.time("fetchImage", photoUri.get().toString(), () -> fetchImage(f));

                if (response.getStatus() != Response.Status.NOT_MODIFIED.getStatusCode()) {
                    byte[] imageBytes = response.readEntity(byte[].class);

                    System.out.println("speakerid=" + f.getSpeakerId() + " bytes=" + imageBytes.length + " photoUri=" + photoUri.get());

                    SpeakerBilde speakerBilde = timer.time("convertImage", photoUri.get().toString(), () -> new SpeakerBilde(imageBytes, response.getLastModified()));

                    speakerImageCache.put(f.getSpeakerId(), speakerBilde);
                }
            }
        } catch (RuntimeException e) {
            logger.warn("Failed to cache speaker image. speakerid=" + f.getSpeakerId(), e);
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

    public SpeakerBilde get(String speakerId) {
        return speakerImageCache.get(speakerId);
    }
}
