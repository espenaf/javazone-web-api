package no.javazone.sessions;

import no.javazone.ems.EmsAdapter;
import no.javazone.ems.Event;
import no.javazone.ems.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionRepository {
    private EmsAdapter emsAdapter;
    private Map<String, List<Session>> eventCache;

    public SessionRepository(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
        eventCache = new HashMap<>();
    }

    public List<Session> getSessions(String eventId) {
        return eventCache.get(eventId);
    }

    public void refresh() {
        List<Event> events = emsAdapter.getEvents();

        System.out.println(events.size());
//        for (String cachedEvent : cachedEvents) {
//            List<Session> sessions = emsAdapter.getEvent(cachedEvent);
//            eventCache.put(cachedEvent, sessions);
//        }
    }
}
