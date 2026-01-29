package cdac.project.logigraph.inventory.controller;

import cdac.project.logigraph.inventory.dto.AddInventoryRequest;
import cdac.project.logigraph.inventory.dto.AdjustInventoryRequest;
import cdac.project.logigraph.inventory.dto.WarehouseInventoryView;
import cdac.project.logigraph.inventory.service.InventoryManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/inventory")
public class InventoryController {

    private final InventoryManagementService inventoryService;

    public InventoryController(
            InventoryManagementService inventoryService
    ) {
        this.inventoryService = inventoryService;
    }

    /**
     * ADMIN / MANAGER
     * Add stock
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/add")
    public ResponseEntity<Void> addStock(
            @Valid @RequestBody AddInventoryRequest request
    ) {
        inventoryService.addStock(
                request.getWarehouseId(),
                request.getProductId(),
                request.getQuantity()
        );
        return ResponseEntity.status(201).build();
    }

    /**
     * ADMIN / MANAGER
     * Adjust stock
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/adjust")
    public ResponseEntity<Void> adjustStock(
            @Valid @RequestBody AdjustInventoryRequest request
    ) {
        inventoryService.adjustStock(
                request.getWarehouseId(),
                request.getProductId(),
                request.getDelta()
        );
        return ResponseEntity.noContent().build();
    }

    /**
     * ADMIN / MANAGER
     * View warehouse inventory
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<WarehouseInventoryView>> getInventory(
            @PathVariable Integer warehouseId
    ) {
        return ResponseEntity.ok(
                inventoryService.getInventoryForWarehouse(warehouseId)
        );
    }
}
