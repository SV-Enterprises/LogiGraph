package cdac.project.logigraph.vehicle.controller;

import cdac.project.logigraph.vehicle.dto.CreateVehicleRequest;
import cdac.project.logigraph.vehicle.dto.UpdateVehicleStatusRequest;
import cdac.project.logigraph.vehicle.dto.UpdateVehicleWarehouseRequest;
import cdac.project.logigraph.vehicle.dto.VehicleResponse;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import cdac.project.logigraph.vehicle.service.VehicleManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/vehicles")
public class VehicleController {

    private final VehicleManagementService vehicleService;

    public VehicleController(VehicleManagementService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * ADMIN: Register vehicle
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VehicleResponse> registerVehicle(
            @Valid @RequestBody CreateVehicleRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleService.registerVehicle(request));
    }

    /**
     * MANAGER / ADMIN: Update vehicle status
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{vehicleId}/status")
    public ResponseEntity<VehicleResponse> updateStatus(
            @PathVariable Integer vehicleId,
            @Valid @RequestBody UpdateVehicleStatusRequest request
    ) {
        return ResponseEntity.ok(
                vehicleService.updateVehicleStatus(vehicleId, request)
        );
    }

    /**
     * MANAGER / ADMIN: Change vehicle warehouse
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{vehicleId}/warehouse")
    public ResponseEntity<VehicleResponse> updateWarehouse(
            @PathVariable Integer vehicleId,
            @Valid @RequestBody UpdateVehicleWarehouseRequest request
    ) {
        return ResponseEntity.ok(
                vehicleService.updateVehicleWarehouse(vehicleId, request)
        );
    }

    /**
     * MANAGER / ADMIN: Fleet view
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(
            @RequestParam(required = false) VehicleStatus status
    ) {
        if (status != null) {
            return ResponseEntity.ok(
                    vehicleService.getVehiclesByStatus(status)
            );
        }
        return ResponseEntity.ok(
                vehicleService.getAllVehicles()
        );
    }
}
