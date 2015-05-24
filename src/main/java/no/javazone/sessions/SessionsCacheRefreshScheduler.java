package no.javazone.sessions;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SessionsCacheRefreshScheduler {
    private static final int PERIOD_BETWEEN_REFRESHES = 10;
    private static final int START_IMMEDIATELY = 0;

    private SessionRepository sessionRepository;

    public SessionsCacheRefreshScheduler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void schedule() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                sessionRepository::refresh,
                START_IMMEDIATELY,
                PERIOD_BETWEEN_REFRESHES,
                TimeUnit.MINUTES);
    }
}
