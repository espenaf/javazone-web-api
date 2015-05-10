package no.javazone.ems;

import org.junit.Test;

public class EmsAdapterTest {
    @Test
    public void should_get_sessions() {
        EmsAdapter emsAdapter = new EmsAdapter("test.javazone.no");
        emsAdapter.getSessions("9f40063a-5f20-4d7b-b1e8-ed0c6cc18a5f");
    }
}