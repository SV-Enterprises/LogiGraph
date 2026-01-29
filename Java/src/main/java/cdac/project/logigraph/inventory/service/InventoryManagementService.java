package cdac.project.logigraph.inventory.service;

import cdac.project.logigraph.inventory.dto.WarehouseInventoryView;
import cdac.project.logigraph.inventory.entity.WarehouseInventory;
import cdac.project.logigraph.inventory.repository.WarehouseInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryManagementService {

    private final WarehouseInventoryRepository inventoryRepository;

    public InventoryManagementService(
            WarehouseInventoryRepository inventoryRepository
    ) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Add stock to a warehouse-product pair.
     * If record exists → atomic increment.
     * If not → create new record.
     */
    @Transactional
    public void addStock(
            Integer warehouseId,
            Integer productId,
            int quantity
    ) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        int updated =
                inventoryRepository.incrementStock(
                        warehouseId,
                        productId,
                        quantity
                );

        // If no row was updated, create new inventory record
        if (updated == 0) {
            WarehouseInventory inventory = new WarehouseInventory();
            inventory.setWarehouseId(warehouseId);
            inventory.setProductId(productId);
            inventory.setQuantity(quantity);
            inventoryRepository.save(inventory);
        }
    }

    /**
     * Adjust stock positively or negatively.
     * Adjustment is atomic and cannot result in negative stock.
     */
    @Transactional
    public void adjustStock(
            Integer warehouseId,
            Integer productId,
            int delta
    ) {
        if (delta == 0) {
            throw new IllegalArgumentException(
                    "Delta must not be zero"
            );
        }

        int updated =
                inventoryRepository.adjustStock(
                        warehouseId,
                        productId,
                        delta
                );

        if (updated == 0) {
            throw new IllegalStateException(
                    "Inventory adjustment failed or insufficient stock"
            );
        }
    }

    /**
     * Read-only inventory view for a specific warehouse.
     */
    @Transactional(readOnly = true)
    public List<WarehouseInventoryView> getInventoryForWarehouse(
            Integer warehouseId
    ) {
        return inventoryRepository
                .findByWarehouseId(warehouseId)
                .stream()
                .map(inv ->
                        new WarehouseInventoryView(
                                inv.getWarehouseId(),
                                inv.getProductId(),
                                inv.getQuantity()
                        )
                )
                .collect(Collectors.toList());
    }
}
