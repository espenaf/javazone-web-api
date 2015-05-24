package no.javazone;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.javazone.api.sessions.SessionResource;
import no.javazone.ems.EmsAdapter;
import no.javazone.helsesjekk.EmsHealthCheck;
import no.javazone.sessions.SessionRepository;
import no.javazone.sessions.SessionsCacheRefreshScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JavaZoneWebApiApplication extends Application<JavaZoneWebApiConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            args = new String[]{"server", "configuration.yaml"};
        }
        new JavaZoneWebApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<JavaZoneWebApiConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(
            JavaZoneWebApiConfiguration configuration,
            Environment environment
    ) {
        final EmsAdapter emsAdapter = new EmsAdapter(configuration.getEmsHost());

        final SessionRepository sessionRepository = new SessionRepository(emsAdapter);

        new SessionsCacheRefreshScheduler(sessionRepository).schedule();

        environment.jersey().register(new SessionResource(sessionRepository));

        environment.healthChecks().register("ems", new EmsHealthCheck(emsAdapter));
    }

}