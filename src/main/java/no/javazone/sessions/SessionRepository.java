package no.javazone.sessions;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SessionRepository.class);

    private EmsAdapter emsAdapter;
    private Map<String, Event> eventCache;

    public SessionRepository(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
        eventCache = new HashMap<>();
    }

    public Optional<Event> getSessions(String eventSlug) {
        if (eventCache.containsKey(eventSlug)) {
            return Optional.of(eventCache.get(eventSlug));
        } else {
            return Optional.empty();
        }
    }

    public void refresh() {
        LOG.info("Caching starta");

        emsAdapter
                .getEvents()
                .forEach(event -> {
                    System.out.println("Cached " + event.getSlug());
                    eventCache.put(event.getSlug(), event);
                });

        LOG.info("Caching ferdig");
    }
}
