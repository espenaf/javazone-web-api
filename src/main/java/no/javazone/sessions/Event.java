package no.javazone.sessions;

import java.util.List;

public class Event {

    private final String slug;
    private final List<Session> sessions;

    public Event(List<Session> sessions, String slug) {
        this.sessions = sessions;
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
