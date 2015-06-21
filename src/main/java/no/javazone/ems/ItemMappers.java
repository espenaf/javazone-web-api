package no.javazone.ems;

import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import net.hamnaberg.json.Property;
import net.hamnaberg.json.Value;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemMappers {
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
