package cdac.project.logigraph.vehicle.dto;

import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateVehicleStatusRequest {

    @NotNull(message = "Vehicle status is required")
    private VehicleStatus status;

    public VehicleStatus getStatus() {
        return status;
    }
}
