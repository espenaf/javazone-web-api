package no.javazone.ems;

import net.hamnaberg.json.Collection;
import net.hamnaberg.json.Item;
import net.hamnaberg.json.parser.CollectionParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EmsAdapter {
    private String emsHost;

    public EmsAdapter(String emsHost) {
        this.emsHost = emsHost;
    }

    public Optional<Session> getSession(String sessionId) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://" + emsHost)
                .path("/ems/server/events/" + sessionId + "/sessions");

        String response = webTarget.request().buildGet().invoke(String.class);

        try {
            Session session = mapToSession(response);
            return Optional.of(session);

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Session mapToSession(String response) throws IOException {
        Collection collection = new CollectionParser().parse(response);
        List<Item> items = collection.getItems();

        for (Item item : items) {
            System.out.println(item.getData().toString());
        }

        return new Session();
    }
}
