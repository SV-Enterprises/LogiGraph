package cdac.project.logigraph.routing.graph;

import cdac.project.logigraph.routing.entity.Route;
import cdac.project.logigraph.routing.repository.RouteRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphBuilder {

    private final RouteRepository routeRepository;
    private volatile Map<Integer, GraphNode> cachedGraph;

    public GraphBuilder(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Map<Integer, GraphNode> buildGraph() {
        if (cachedGraph != null) return cachedGraph;

        synchronized (this) {
            if (cachedGraph == null) {
                cachedGraph = Collections.unmodifiableMap(buildInternal());
            }
        }
        return cachedGraph;
    }

    private Map<Integer, GraphNode> buildInternal() {
        Map<Integer, GraphNode> graph = new HashMap<>();
        for (Route route : routeRepository.findByActiveTrue()) {
            if (route.getBaseTimeMins() <= 0) continue;

            graph.putIfAbsent(
                    route.getSourceWarehouseId(),
                    new GraphNode(route.getSourceWarehouseId())
            );
            graph.putIfAbsent(
                    route.getDestinationWarehouseId(),
                    new GraphNode(route.getDestinationWarehouseId())
            );

            graph.get(route.getSourceWarehouseId())
                    .addEdge(new GraphEdge(
                            route.getDestinationWarehouseId(),
                            route.getBaseTimeMins()
                    ));
        }
        return graph;
    }
}
