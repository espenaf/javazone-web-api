package no.javazone.ems;

import net.hamnaberg.json.Item;
import no.javazone.sessions.EmsIds;
import org.junit.Test;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EmsIdMapperTest {

    public static final String ITEM_URL = "http://test.javazone.no/ems/server/events/e213acd2-ad9e-4bba-a5aa-87d47fed5d08/sessions/717e34d7-f713-4edb-bb44-1b494d716b3b";

    @Test
    public void should_map_from_item_url_to_id() {
        Item item = Item.builder(URI.create(ITEM_URL)).build();
        EmsIds emsIds = EmsIdMapper.mapToEmsIds(item);

        assertThat(emsIds.getEventId(), is(UUID.fromString("e213acd2-ad9e-4bba-a5aa-87d47fed5d08")));
        assertThat(emsIds.getSessionId(), is(UUID.fromString("717e34d7-f713-4edb-bb44-1b494d716b3b")));
    }
}