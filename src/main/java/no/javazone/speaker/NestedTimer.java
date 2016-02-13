package no.javazone.speaker;

import com.google.common.base.Throwables;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class NestedTimer {

    public <T> T time(String name, Object detail, Callable<T> callable) {
        long start = System.nanoTime();
        try {
            return callable.call();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        } finally {
            long endTime = System.nanoTime();
            timers.computeIfAbsent(name, (key) -> new TimerNode()).addDuration(endTime - start);
        }
    }

    private Map<String, TimerNode> timers = new LinkedHashMap<>();

    public void printAggregate() {
        timers.forEach((k, v) -> {
            long millis = TimeUnit.NANOSECONDS.toMillis(v.totalDuration());
            System.out.println("operation=" + k + " - total_time=" + millis + "ms. items=" + v.size() + ". avg_time=" + millis/v.size() +"ms");
        });
    }

    private static class TimerNode {
        private final List<Long> durations = new ArrayList<>();

        public void addDuration(long durationNanos) {
            durations.add(durationNanos);
        }

        public long size() {
            return durations.size();
        }

        public long totalDuration() {
            long sum = 0;
            for (Long duration : durations) {
                if (duration != null) {
                    sum += duration;
                }
            }
            return sum;
        }
    }
}
