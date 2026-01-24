package cdac.project.logigraph.routing.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "routes",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"source_warehouse_id", "destination_warehouse_id"}
    )
)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "source_warehouse_id", nullable = false)
    private Integer sourceWarehouseId;

    @Column(name = "destination_warehouse_id", nullable = false)
    private Integer destinationWarehouseId;

    @Column(name = "distance_km", nullable = false)
    private double distanceKm;

    @Column(name = "base_time_mins", nullable = false)
    private int baseTimeMins;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_bidirectional")
    private boolean bidirectional;

    public Integer getId() {
        return id;
    }

    public Integer getSourceWarehouseId() {
        return sourceWarehouseId;
    }

    public Integer getDestinationWarehouseId() {
        return destinationWarehouseId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public int getBaseTimeMins() {
        return baseTimeMins;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setSourceWarehouseId(Integer sourceWarehouseId) {
        this.sourceWarehouseId = sourceWarehouseId;
    }

    public void setDestinationWarehouseId(Integer destinationWarehouseId) {
        this.destinationWarehouseId = destinationWarehouseId;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setBaseTimeMins(int baseTimeMins) {
        this.baseTimeMins = baseTimeMins;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }
}
