package no.javazone.sessions;

public class Slot {
    private final String starter;
    private final String stopper;

    public Slot(String starter, String stopper) {
        this.starter = starter;
        this.stopper = stopper;
    }

    public String getStarter() {
        return starter;
    }

    public String getStopper() {
        return stopper;
    }

    public static Slot tom() {
        return new Slot(null, null);
    }
}
