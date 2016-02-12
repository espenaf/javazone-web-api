package no.javazone.sessions;

import no.javazone.ems.EmsAdapter;
import no.javazone.speaker.NestedTimer;
import no.javazone.speaker.SpeakerImageCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SessionRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SessionRepository.class);

    private EmsAdapter emsAdapter;
    private Map<String, Event> eventCache;
    private SpeakerImageCache speakerCache;

    public SessionRepository(EmsAdapter emsAdapter, SpeakerImageCache speakerCache) {
        this.emsAdapter = emsAdapter;
        this.speakerCache = speakerCache;
        this.eventCache = new ConcurrentHashMap<>();
    }

    public Optional<Event> getSessions(String eventSlug) {
        return Optional.ofNullable(eventCache.get(eventSlug));
    }

    public Optional<Session> getSession(String eventSlug, SessionId sessionId) {
        return getSessions(eventSlug)
                .flatMap(x -> x.findSessionById(sessionId));
    }

    public void refresh() {
        NestedTimer timer = new NestedTimer();
        try {
            LOG.info("Caching starta");

            List<Event> events = emsAdapter.getEvents(timer)
                    .collect(Collectors.toList());

            events.forEach(event -> {
                LOG.info("Cached " + event.getSlug());
                eventCache.put(event.getSlug(), event);
            });

            events.parallelStream()
                    .flatMap(e -> e.getSessions().stream()
                            .flatMap(s -> s.getForedragsholdere().stream()
                                    .map(f -> f)))
                    .forEach(f -> speakerCache.add(f, timer));

            timer.printAggregate();

            LOG.info("Caching ferdig");
        } catch (Exception e) {
            LOG.error("Exception under lasting av cache", e);
            timer.printAggregate();
        } catch (Throwable e) {
            LOG.error("Throwable under lasting av cache", e);
            throw e; // Rethrowing cancels the scheduler.
        }
    }
}
