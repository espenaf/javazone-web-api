package no.javazone;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.javazone.ems.EmsAdapter;
import no.javazone.api.foredrag.ForedragResource;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            args = new String[]{"server", "configuration.yaml"};
        }
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(
            HelloWorldConfiguration configuration,
            Environment environment
    ) {
        final HelloWorldResource resource = new HelloWorldResource();
        environment.jersey().register(resource);

        final EmsAdapter emsAdapter = new EmsAdapter(configuration.getEmsHost());
        final ForedragResource foredragResource = new ForedragResource(emsAdapter);
        environment.jersey().register(foredragResource);
    }

}