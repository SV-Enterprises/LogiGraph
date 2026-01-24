package cdac.project.logigraph.vehicle.entity;

import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import jakarta.persistence.*;

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

    @Column(name = "current_warehouse_id")
    private Integer currentWarehouseId;

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

    public Integer getCurrentWarehouseId() {
        return currentWarehouseId;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public void setCurrentWarehouseId(Integer currentWarehouseId) {
        this.currentWarehouseId = currentWarehouseId;
    }
}
