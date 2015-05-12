package no.javazone.sessions;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Event;
import no.javazone.ems.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionRepository {
    private EmsAdapter emsAdapter;
    private Map<String, Event> eventCache;

    public SessionRepository(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
        eventCache = new HashMap<>();
    }

    public Event getSessions(String eventSlug) {
        return eventCache.get(eventSlug);
    }

    public void refresh() {
        emsAdapter
                .getEvents()
                .forEach(event -> eventCache.put(event.getSlug(), event));
    }
}
