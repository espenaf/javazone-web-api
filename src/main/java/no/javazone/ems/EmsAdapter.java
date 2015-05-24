package no.javazone.ems;

import net.hamnaberg.json.Collection;
import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import net.hamnaberg.json.parser.CollectionParser;
import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.Session;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmsAdapter {

    private final WebTarget emsWebTarget;

    public EmsAdapter(String emsHost) {
        Client client = ClientBuilder.newClient();
        emsWebTarget = client.target("http://" + emsHost);
    }

    public Stream<Event> getEvents() {
        return getEventUris()
                .parallelStream()
                .map(this::getEvent)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    public void check() {
        WebTarget target = emsWebTarget.path("/ems/server/app-info");
        target.request().buildGet().invoke(String.class);
    }

    private List<EventMinimal> getEventUris() {
        WebTarget eventWebTarget = emsWebTarget.path("/ems/server/events");

        String response = eventWebTarget.request().buildGet().invoke(String.class);

        try {
            Collection collection = new CollectionParser().parse(response);
            return mapToEventMinimals(collection);

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private Optional<Event> getEvent(EventMinimal eventMinimal) {
        WebTarget sessionWebTarget = ClientBuilder
                .newClient()
                .target(eventMinimal.getUri())
                .path("sessions");

        String response = sessionWebTarget.request().buildGet().invoke(String.class);

        try {
            Collection collection = new CollectionParser().parse(response);
            return Optional.of(mapToEvent(collection, eventMinimal.getSlug()));

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private List<EventMinimal> mapToEventMinimals(Collection collection) {
        return collection
                .getItems()
                .stream()
                .map(this::mapItemToEventMinimal)
                .collect(Collectors.toList());
    }

    private EventMinimal mapItemToEventMinimal(Item item) {
        return new EventMinimal(
                item.getHref().get(),
                mapItemProperty(item, "slug"));
    }

    private Event mapToEvent(Collection collection, String slug) throws IOException {
        List<Session> sessions = collection
                .getItems()
                .stream()
                .map(EmsAdapter::mapItemTilForedrag)
                .collect(Collectors.toList());
        return new Event(sessions, slug);
    }

    private static Session mapItemTilForedrag(Item item) {
        String startTid = null;
        String stopTid = null;
        String slotString = extractSlotString(item);
        if (slotString != null) {
            String[] strings = slotString.split("\\+");
            if (strings.length == 2) {
                startTid = strings[0];
                stopTid = strings[1];
            }
        }
        return new Session(
                mapItemProperty(item, "title"),
                mapItemProperty(item, "format"),
                startTid,
                stopTid,
                getForedragsholdere(item.linkByRel("speaker collection")));
    }

    private static String extractSlotString(final Item item) {
        Optional<Link> slotLink = item.linkByRel("slot item");
        if (!slotLink.isPresent()) {
            return null;
        }
        Optional<String> slotPrompt = slotLink.get().getPrompt();
        if (!slotPrompt.isPresent()) {
            return null;
        }
        return slotPrompt.get();
    }

    private static List<Foredragsholder> getForedragsholdere(Optional<Link> link) {
        if (link.isPresent()) {
            WebTarget webTarget = ClientBuilder.newClient()
                    .target(link.get().getHref());
            String response = webTarget.request().buildGet().invoke(String.class);

            try {
                Collection collection = new CollectionParser().parse(response);
                return collection
                        .getItems()
                        .stream()
                        .map(EmsAdapter::mapItemTilForedragsholder)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException("Finner ikke speakers");
            }
        } else {
            throw new RuntimeException("Speakerlink finnes ikke");
        }
    }

    private static Foredragsholder mapItemTilForedragsholder(Item item) {
        return new Foredragsholder(
                mapItemProperty(item, "name"),
                mapItemProperty(item, "bio"));
    }

    private static String mapItemProperty(Item item, String property) {
        return item.propertyByName(property).get().getValue().get().asString();
    }
}
