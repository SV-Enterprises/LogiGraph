package cdac.project.logigraph.vehicle.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateVehicleWarehouseRequest {

    @NotNull(message = "Warehouse ID is required")
    private Integer warehouseId;

    public Integer getWarehouseId() {
        return warehouseId;
    }
}
