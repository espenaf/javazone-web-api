package no.javazone.sessions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Event {

    private final String slug;
    private final Map<SessionId, Session> sessions;

    public Event(List<Session> sessions, String slug) {
        this.sessions = sessions
                .stream()
                .collect(Collectors.toMap(
                        Session::getId,
                        Function.identity()));

        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public Collection<Session> getSessions() {
        return sessions.values();
    }

    public Optional<Session> findSessionById(SessionId sessionId) {
        if (sessions.containsKey(sessionId)) {
            return Optional.of(sessions.get(sessionId));
        } else {
            return Optional.empty();
        }
    }
}
