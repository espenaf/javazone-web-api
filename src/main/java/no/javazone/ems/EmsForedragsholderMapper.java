package no.javazone.ems;

import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import no.javazone.sessions.Foredragsholder;

import java.net.URI;
import java.util.Optional;

import static no.javazone.ems.ItemMappers.generateIdString;
import static no.javazone.ems.ItemMappers.mapItemLink;
import static no.javazone.ems.ItemMappers.mapPropertyToString;

public class EmsForedragsholderMapper {
    public static Foredragsholder mapItemTilForedragsholder(Item item) {
        String speakerId = generateIdString(item);
        return new Foredragsholder(
                speakerId,
                mapPropertyToString(item, "name"),
                mapPropertyToString(item, "bio"),
                mapItemLink(item, "photo"),
                getGravatarUrl(item)
        );
    }

    private static Optional<URI> getGravatarUrl(Item item) {
        Optional<Link> link = item.findLink(x -> x.getRel().equals("thumbnail") && x.getHref().toString().contains("gravatar"));
        return link.map(x -> x.getHref().toString().replaceAll("\\?.*", ""))
                .map(URI::create);
    }
}
