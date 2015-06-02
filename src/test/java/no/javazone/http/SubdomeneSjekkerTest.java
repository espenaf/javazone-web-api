package no.javazone.http;

import org.junit.Test;

import static no.javazone.http.SubdomeneSjekker.erEtJavaZoneSubdomene;
import static org.fest.assertions.Assertions.assertThat;

public class SubdomeneSjekkerTest {

    @Test
    public void subdomene_matcher() {
        assertThat(erEtJavaZoneSubdomene("http://2015.javazone.no")).isTrue();
    }

    @Test
    public void feil_domene() {
        assertThat(erEtJavaZoneSubdomene("http://ndc.no")).isFalse();
    }

    @Test
    public void dobbelt_subdomene() {
        assertThat(erEtJavaZoneSubdomene("http://test.2015.javazone.no")).isTrue();
    }

    @Test
    public void med_https() {
        assertThat(erEtJavaZoneSubdomene("https://dev.javazone.no")).isTrue();
    }
}