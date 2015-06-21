package no.javazone.ems;

import net.hamnaberg.json.*;
import net.hamnaberg.json.parser.CollectionParser;
import no.javazone.sessions.*;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmsAdapter {

    private static final int CONNECT_TIMOUT_IN_MILLISECONDS = 30 * 1000;
    private static final int READ_TIMOUT_IN_MILLISECONDS = 30 * 1000;

    private final WebTarget emsWebTarget;
    private final Client client;

    public EmsAdapter(String emsHost) {
        client = ClientBuilder.newClient();

        client.property(ClientProperties.CONNECT_TIMEOUT, CONNECT_TIMOUT_IN_MILLISECONDS);
        client.property(ClientProperties.READ_TIMEOUT, READ_TIMOUT_IN_MILLISECONDS);

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
        WebTarget sessionWebTarget = client
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
                .map(this::mapItemTilForedrag)
                .collect(Collectors.toList());
        return new Event(sessions, slug);
    }

    private Session mapItemTilForedrag(Item item) {
        return new Session(
                SessionIdMapper.generateId(item),
                mapItemProperty(item, "title"),
                mapItemProperty(item, "format"),
                SlotMapper.mapToSlot(item),
                getForedragsholdere(item.linkByRel("speaker collection")),
                mapItemProperty(item, "lang"),
                mapItemProperty(item, "level"),
                mapItemProperty(item, "summary"),
                mapItemProperty(item, "body"),
                mapLink(item, "alternate video"),
                mapLinkPrompt(item, "room item"),
                mapItemTilNokkelord(item),
                mapItemProperty(item, "audience"));
    }

    private List<Foredragsholder> getForedragsholdere(Optional<Link> link) {
        if (link.isPresent()) {
            WebTarget webTarget = client
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
        String speakerId = SessionIdMapper.generateIdString(item);
        return new Foredragsholder(
                speakerId,
                mapItemProperty(item, "name"),
                mapItemProperty(item, "bio"),
                mapItemLink(item, "photo"),
                getGravatarUrl(item)
        );
    }


    public static Optional<URI> getGravatarUrl(Item item) {
        Optional<Link> link = item.findLink(x -> x.getRel().equals("thumbnail") && x.getHref().toString().contains("gravatar"));
        return link.map(x -> x.getHref().toString().replaceAll("\\?.*", ""))
                .map(URI::create);
    }

    private static Optional<URI> mapItemLink(Item item, String relName) {
        return item.linkByRel(relName)
                .map(Link::getHref);
    }

    private static String mapItemProperty(Item item, String propertyName) {
        return item.propertyByName(propertyName)
                .flatMap(Property::getValue)
                .map(Value::asString)
                .orElse(null);
    }

    private static String mapLinkPrompt(Item item, String rel) {
        return item.linkByRel(rel)
                .flatMap(Link::getPrompt)
                .orElse(null);
    }

    private static Optional<URI> mapLink(Item item, String rel) {
        return item.linkByRel(rel)
                .map(link -> Optional.of(link.getHref()))
                .orElse(Optional.empty());
    }

    private static List<String> mapItemTilNokkelord(Item item) {
        return item
                .propertyByName("keywords")
                .map(Property::getArray)
                .map(list -> list.stream()
                        .filter(Value::isString)
                        .map(Value::asString)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }
}
