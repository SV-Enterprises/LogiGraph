package cdac.project.logigraph.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateVehicleRequest {

    @NotBlank(message = "Plate number is required")
    @Size(max = 20, message = "Plate number must not exceed 20 characters")
    private String plateNumber;

    @NotBlank(message = "Vehicle type is required")
    private String type;

    @NotNull(message = "Home warehouse ID is required")
    private Integer homeWarehouseId;

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getType() {
        return type;
    }

    public Integer getHomeWarehouseId() {
        return homeWarehouseId;
    }
}
