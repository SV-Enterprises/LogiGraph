package cdac.project.logigraph.routing.graph;

import java.util.*;

public class GraphNode {

    private final Integer warehouseId;
    private final List<GraphEdge> edges = new ArrayList<>();

    public GraphNode(Integer warehouseId) {
        if (warehouseId == null) {
            throw new IllegalArgumentException("warehouseId cannot be null");
        }
        this.warehouseId = warehouseId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public List<GraphEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    void addEdge(GraphEdge edge) {
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GraphNode &&
               warehouseId.equals(((GraphNode) o).warehouseId);
    }

    @Override
    public int hashCode() {
        return warehouseId.hashCode();
    }
}
