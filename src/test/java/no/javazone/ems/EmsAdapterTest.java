package no.javazone.ems;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class EmsAdapterTest {
    @Test
    public void should_get_sessions() {
        EmsAdapter emsAdapter = new EmsAdapter("test.javazone.no");
        List<Event> events = emsAdapter.getEvents().collect(Collectors.toList());

        assertThat(events.size()).isGreaterThan(0);
    }
}