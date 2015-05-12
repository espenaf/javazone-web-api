package no.javazone.ems;

import org.junit.Test;

public class EmsAdapterTest {
    @Test
    public void should_get_sessions() {
        EmsAdapter emsAdapter = new EmsAdapter("test.javazone.no");
        emsAdapter.getEvents();
    }
}