package no.javazone.ems;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class Event {

    private final List<Session> sessions;
    private String slug;

    public Event(List<Session> sessions, String slug) {
        this.sessions = sessions;
        this.slug = slug;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public String getSlug() {
        return slug;
    }
}
