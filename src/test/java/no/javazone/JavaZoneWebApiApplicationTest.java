package no.javazone;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaZoneWebApiApplicationTest {

    @ClassRule
    public static final DropwizardAppRule<JavaZoneWebApiConfiguration> RULE =
            new DropwizardAppRule<>(JavaZoneWebApiApplication.class,
                    ResourceHelpers.resourceFilePath("test-configuration.yaml"),
                    ConfigOverride.config("server.applicationConnectors[0].port", "9090"));

    @Test
    public void server_svarer_200_ok() {
        WebTarget resource = ClientBuilder.newClient().target("http://localhost:" + RULE.getLocalPort() + "/hello-world");

        final Response response = resource.request().get();
        assertThat(response.getStatus()).isEqualTo(200);
    }

}