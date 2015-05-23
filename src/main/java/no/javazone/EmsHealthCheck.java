package no.javazone;

import com.codahale.metrics.health.HealthCheck;
import no.javazone.ems.EmsAdapter;

public class EmsHealthCheck extends HealthCheck {
    private EmsAdapter emsAdapter;

    public EmsHealthCheck(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
