package no.javazone.ems;

import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import net.hamnaberg.json.Property;
import net.hamnaberg.json.Value;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemMappers {
    private static final Logger LOG = LoggerFactory.getLogger(ItemMappers.class);

    public static String generateIdString(Item item) {
        Optional<URI> uriOptional = item.getHref();
        if (!uriOptional.isPresent()) {
            throw new IllegalStateException("manglet href");
        }
        URI href = uriOptional.get();
        return new String(Hex.encodeHex(getMda().digest(href.toString().getBytes())));
    }

    private static MessageDigest getMda() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Kunne ikke lage SHA-256", e);
            throw new IllegalStateException();
        }
    }

    public static List<String> mapPropertyToList(Item item, String propertyName) {
        return item
                .propertyByName(propertyName)
                .map(Property::getArray)
                .map(list -> list.stream()
                        .filter(Value::isString)
                        .map(Value::asString)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    public static Optional<URI> mapLink(Item item, String rel) {
        return item.linkByRel(rel)
                .map(link -> Optional.of(link.getHref()))
                .orElse(Optional.empty());
    }

    public static String mapLinkPrompt(Item item, String rel) {
        return item.linkByRel(rel)
                .flatMap(Link::getPrompt)
                .orElse(null);
    }

    public static String mapPropertyToString(Item item, String propertyName) {
        return item.propertyByName(propertyName)
                .flatMap(Property::getValue)
                .map(Value::asString)
                .orElse(null);
    }

    public static Optional<URI> mapItemLink(Item item, String relName) {
        return item.linkByRel(relName)
                .map(Link::getHref);
    }
}
