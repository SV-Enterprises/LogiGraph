package cdac.project.logigraph.vehicle.entity;

import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "plate_number", nullable = false, unique = true, length = 20)
    private String plateNumber;

    @Column(nullable = false, length = 50)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleStatus status;

    /**
     * Permanent base warehouse of the vehicle
     */
    @Column(name = "home_warehouse_id", nullable = false)
    private Integer homeWarehouseId;

    /**
     * Dynamic current warehouse (changes during routing)
     */
    @Column(name = "current_warehouse_id")
    private Integer currentWarehouseId;

    /**
     * Audit field (DB-managed)
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    /* =====================
       GETTERS
       ===================== */

    public Integer getId() {
        return id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getType() {
        return type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public Integer getHomeWarehouseId() {
        return homeWarehouseId;
    }

    public Integer getCurrentWarehouseId() {
        return currentWarehouseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /* =====================
       SETTERS
       ===================== */

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public void setHomeWarehouseId(Integer homeWarehouseId) {
        this.homeWarehouseId = homeWarehouseId;
    }

    public void setCurrentWarehouseId(Integer currentWarehouseId) {
        this.currentWarehouseId = currentWarehouseId;
    }
}
