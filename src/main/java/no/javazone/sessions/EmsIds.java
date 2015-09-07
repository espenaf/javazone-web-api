package no.javazone.sessions;

import java.util.UUID;

public class EmsIds {

    private UUID eventId;
    private UUID sessionId;

    public EmsIds(UUID eventId, UUID sessionId) {
        this.eventId = eventId;
        this.sessionId = sessionId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

}
