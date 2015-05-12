package no.javazone.ems;

import net.hamnaberg.json.Collection;
import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import net.hamnaberg.json.parser.CollectionParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class EmsAdapter {
    private final WebTarget emsWebTarget;

    public EmsAdapter(String emsHost) {
        Client client = ClientBuilder.newClient();
        emsWebTarget = client.target("http://" + emsHost);
    }

    public List<Event> getEvents() {
        return getEventUris()
                .stream()
                .map(this::getEvent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<EventMinimal> getEventUris() {
        WebTarget eventWebTarget = emsWebTarget.path("/ems/server/events");

        String response = eventWebTarget.request().buildGet().invoke(String.class);

        try {
            Collection collection = new CollectionParser().parse(response);
            return mapToEventMinimals(collection);

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Optional<Event> getEvent(EventMinimal eventMinimal) {
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
        return new Session(
                mapItemProperty(item, "title"),
                getForedragsholdere(item.linkByRel("speaker collection")));
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
