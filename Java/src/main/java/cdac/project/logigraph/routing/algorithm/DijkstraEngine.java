package cdac.project.logigraph.routing.algorithm;

import cdac.project.logigraph.routing.graph.*;

import java.util.*;

public class DijkstraEngine {

    public DijkstraResult shortestPaths(
            Map<Integer, GraphNode> graph,
            Integer source
    ) {
        Map<Integer, Long> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();
        PriorityQueue<long[]> pq =
                new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        pq.add(new long[]{source, 0});
        dist.put(source, 0L);

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int node = (int) cur[0];

            for (GraphEdge edge : graph.get(node).getEdges()) {
                long nd = dist.get(node) + edge.getWeight();
                if (nd < dist.getOrDefault(edge.getTargetWarehouseId(), Long.MAX_VALUE)) {
                    dist.put(edge.getTargetWarehouseId(), nd);
                    prev.put(edge.getTargetWarehouseId(), node);
                    pq.add(new long[]{edge.getTargetWarehouseId(), nd});
                }
            }
        }
        return new DijkstraResult(dist, prev);
    }
}
