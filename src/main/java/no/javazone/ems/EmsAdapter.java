package no.javazone.ems;

import net.hamnaberg.json.Collection;
import net.hamnaberg.json.Item;
import net.hamnaberg.json.parser.CollectionParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EmsAdapter {
    private String emsHost;

    public EmsAdapter(String emsHost) {
        this.emsHost = emsHost;
    }

    public List<Foredrag> getForedragsliste(String eventId) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client
                .target("http://" + emsHost)
                .path("/ems/server/events")
                .path(eventId)
                .path("sessions");

        String response = webTarget.request().buildGet().invoke(String.class);

        try {
            Collection collection = new CollectionParser().parse(response);
            return mapToForedragsliste(collection);

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private List<Foredrag> mapToForedragsliste(Collection collection) throws IOException {
        return collection
                .getItems()
                .stream()
                .map(EmsAdapter::mapItemTilForedrag)
                .collect(Collectors.toList());
    }

    private static Foredrag mapItemTilForedrag(Item item) {
        return new Foredrag(mapItemProperty(item, "title"));
    }

    private static String mapItemProperty(Item item, String property) {
        return item.propertyByName(property).get().getValue().get().asString();
    }
}
