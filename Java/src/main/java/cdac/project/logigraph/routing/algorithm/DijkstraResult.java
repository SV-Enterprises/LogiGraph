package cdac.project.logigraph.routing.algorithm;

import java.util.*;

public class DijkstraResult {

    private final Map<Integer, Long> distances;
    private final Map<Integer, Integer> previous;

    public DijkstraResult(
            Map<Integer, Long> distances,
            Map<Integer, Integer> previous
    ) {
        this.distances = Collections.unmodifiableMap(distances);
        this.previous = Collections.unmodifiableMap(previous);
    }

    public Map<Integer, Long> getDistances() {
        return distances;
    }

    public Map<Integer, Integer> getPrevious() {
        return previous;
    }
}
