package no.javazone.ems;

import net.hamnaberg.json.Collection;
import net.hamnaberg.json.Item;
import net.hamnaberg.json.parser.CollectionParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class EmsAdapter {
    private final WebTarget emsWebTarget;

    public EmsAdapter(String emsHost) {
        Client client = ClientBuilder.newClient();
        emsWebTarget = client.target("http://" + emsHost);
    }

    public List<Session> getSessions(String eventId) {

        WebTarget webTarget = emsWebTarget
                .path("/ems/server/events/" + eventId + "/sessions");

        String response = webTarget.request().buildGet().invoke(String.class);

        try {
            Collection collection = new CollectionParser().parse(response);
            return mapToForedragsliste(collection);

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private List<Session> mapToForedragsliste(Collection collection) throws IOException {
        ArrayList<Session> sessions = new ArrayList<>();

        for (Item item : collection.getItems()) {
            Session session = new Session(
                    mapItemProperty(item, "title"),
                    new ArrayList<>());
                    //getForedragsholdere(item.linkByName("Speakers").get().getHref()));
            sessions.add(session);
        }

        return sessions;
    }

    private List<Foredragsholder> getForedragsholdere(URI speakersUri) {
        return null;
    }

    private String mapItemProperty(Item item, String property) {
        return item.propertyByName(property).get().getValue().get().asString();
    }
}
