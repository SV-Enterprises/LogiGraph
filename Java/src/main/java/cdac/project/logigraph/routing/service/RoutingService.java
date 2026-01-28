package cdac.project.logigraph.routing.service;

import cdac.project.logigraph.routing.algorithm.DijkstraEngine;
import cdac.project.logigraph.routing.algorithm.DijkstraResult;
import cdac.project.logigraph.routing.graph.GraphBuilder;
import cdac.project.logigraph.routing.graph.GraphNode;
import cdac.project.logigraph.inventory.entity.Warehouse;
import cdac.project.logigraph.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RoutingService {

    private final GraphBuilder graphBuilder;
    private final DijkstraEngine dijkstraEngine;
    private final WarehouseRepository warehouseRepository;

    public RoutingService(
            GraphBuilder graphBuilder,
            DijkstraEngine dijkstraEngine,
            WarehouseRepository warehouseRepository
    ) {
        this.graphBuilder = graphBuilder;
        this.dijkstraEngine = dijkstraEngine;
        this.warehouseRepository = warehouseRepository;
    }

    public Integer selectNearestWarehouse(
            Set<Integer> eligibleWarehouses,
            double destLat,
            double destLng
    ) {

        Map<Integer, GraphNode> graph = graphBuilder.buildGraph();

        long bestTotalMinutes = Long.MAX_VALUE;
        Integer bestWarehouseId = null;

        for (Integer warehouseId : eligibleWarehouses) {

            if (!graph.containsKey(warehouseId)) {
                continue;
            }

            // 1️⃣ Middle-mile cost using Dijkstra
            DijkstraResult result =
                    dijkstraEngine.shortestPaths(graph, warehouseId);

            long middleMileMinutes =
                    result.getDistances().getOrDefault(
                            warehouseId, Long.MAX_VALUE
                    );

            // 2️⃣ Last-mile cost (warehouse → customer)
            Warehouse warehouse =
                    warehouseRepository.findById(warehouseId)
                            .orElseThrow(() ->
                                    new IllegalStateException(
                                            "Warehouse not found: " + warehouseId
                                    )
                            );

            double km = haversine(
                    warehouse.getLatitude(),
                    warehouse.getLongitude(),
                    destLat,
                    destLng
            );

            long lastMileMinutes =
                    Math.round((km / 40.0) * 60); // avg 40 km/h

            // 3️⃣ Total cost
            long totalMinutes =
                    middleMileMinutes + lastMileMinutes;

            if (totalMinutes < bestTotalMinutes) {
                bestTotalMinutes = totalMinutes;
                bestWarehouseId = warehouseId;
            }
        }

        if (bestWarehouseId == null) {
            throw new IllegalStateException(
                    "Unable to determine nearest warehouse"
            );
        }

        return bestWarehouseId;
    }

    private double haversine(
            double lat1, double lon1,
            double lat2, double lon2
    ) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);

        return EARTH_RADIUS_KM * 2 *
                Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
