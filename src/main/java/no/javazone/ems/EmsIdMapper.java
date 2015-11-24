package no.javazone.ems;

import net.hamnaberg.json.Item;
import no.javazone.sessions.EmsIds;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmsIdMapper {

    public static EmsIds mapToEmsIds(Item item) {
        String uri = item.getHref().get().toString();

        Pattern pattern = Pattern.compile(".*events/(.{36})/sessions.(.{36}).*");
        Matcher matcher = pattern.matcher(uri);

        if (!matcher.matches()) {
            throw new IllegalStateException("Unable to extract ids from item with uri "  + uri);
        }
        return new EmsIds(
                UUID.fromString(matcher.group(1)),
                UUID.fromString(matcher.group(2))
        );
    }

}
