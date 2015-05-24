package no.javazone.helsesjekk;

import com.codahale.metrics.health.HealthCheck;
import no.javazone.ems.EmsAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ServerErrorException;

public class EmsHealthCheck extends HealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(EmsHealthCheck.class);

    private EmsAdapter emsAdapter;

    public EmsHealthCheck(EmsAdapter emsAdapter) {
        this.emsAdapter = emsAdapter;
    }

    @Override
    protected Result check() throws Exception {
        try {
            emsAdapter.check();

        } catch (ServerErrorException e) {
            LOG.error("Helsesjekk av EMS feila", e);
            return Result.unhealthy(e);
        }
        return Result.healthy();
    }
}
