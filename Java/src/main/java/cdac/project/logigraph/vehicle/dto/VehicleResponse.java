package cdac.project.logigraph.vehicle.dto;

import cdac.project.logigraph.vehicle.entity.Vehicle;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;

public class VehicleResponse {

    private Integer id;
    private String plateNumber;
    private String type;
    private VehicleStatus status;
    private Integer homeWarehouseId;
    private Integer currentWarehouseId;

    public static VehicleResponse fromEntity(Vehicle vehicle) {

        VehicleResponse dto = new VehicleResponse();
        dto.id = vehicle.getId();
        dto.plateNumber = vehicle.getPlateNumber();
        dto.type = vehicle.getType();
        dto.status = vehicle.getStatus();
        dto.homeWarehouseId = vehicle.getHomeWarehouseId();
        dto.currentWarehouseId = vehicle.getCurrentWarehouseId();

        return dto;
    }

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
}
