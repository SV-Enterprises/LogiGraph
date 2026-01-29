package cdac.project.logigraph.inventory.controller;

import cdac.project.logigraph.inventory.dto.CreateWarehouseRequest;
import cdac.project.logigraph.inventory.dto.UpdateWarehouseRequest;
import cdac.project.logigraph.inventory.dto.WarehouseResponse;
import cdac.project.logigraph.inventory.entity.Warehouse;
import cdac.project.logigraph.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * ADMIN: Create warehouse
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<WarehouseResponse> createWarehouse(
            @Valid @RequestBody CreateWarehouseRequest request
    ) {
        Warehouse warehouse = warehouseService.createWarehouse(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WarehouseResponse.fromEntity(warehouse));
    }

    /**
     * ADMIN: Update warehouse
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateWarehouseRequest request
    ) {
        Warehouse warehouse =
                warehouseService.updateWarehouse(id, request);

        return ResponseEntity.ok(
                WarehouseResponse.fromEntity(warehouse)
        );
    }

    /**
     * MANAGER / ADMIN: View all warehouses
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/manager")
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {

        List<WarehouseResponse> response =
                warehouseService.getAllWarehouses()
                        .stream()
                        .map(WarehouseResponse::fromEntity)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * MANAGER / ADMIN: View single warehouse
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/manager/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouse(
            @PathVariable Integer id
    ) {
        Warehouse warehouse =
                warehouseService.getWarehouse(id);

        return ResponseEntity.ok(
                WarehouseResponse.fromEntity(warehouse)
        );
    }
}
