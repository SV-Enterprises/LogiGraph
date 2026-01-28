package cdac.project.logigraph.routing.graph;

public class GraphEdge {

    private final Integer targetWarehouseId;
    private final int weight;

    public GraphEdge(Integer targetWarehouseId, int weight) {
        if (targetWarehouseId == null || weight <= 0) {
            throw new IllegalArgumentException("Invalid edge");
        }
        this.targetWarehouseId = targetWarehouseId;
        this.weight = weight;
    }

    public Integer getTargetWarehouseId() {
        return targetWarehouseId;
    }

    public int getWeight() {
        return weight;
    }
}
