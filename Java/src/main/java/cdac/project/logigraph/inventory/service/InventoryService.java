package cdac.project.logigraph.inventory.service;

import cdac.project.logigraph.inventory.entity.WarehouseInventory;
import cdac.project.logigraph.inventory.repository.WarehouseInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InventoryService {

    private final WarehouseInventoryRepository inventoryRepository;

    public InventoryService(WarehouseInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Set<Integer> findWarehousesThatCanFulfillAllItems(
            Map<Integer, Integer> items
    ) {
        Map<Integer, Integer> warehouseMatchCount = new HashMap<>();
        int totalItems = items.size();

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer productId = entry.getKey();
            int qty = entry.getValue();

            List<WarehouseInventory> inventories =
                    inventoryRepository
                            .findByProductIdAndQuantityGreaterThanEqual(
                                    productId, qty
                            );

            for (WarehouseInventory inv : inventories) {
                warehouseMatchCount.merge(
                        inv.getWarehouseId(), 1, Integer::sum
                );
            }
        }

        Set<Integer> eligible = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : warehouseMatchCount.entrySet()) {
            if (entry.getValue() == totalItems) {
                eligible.add(entry.getKey());
            }
        }

        if (eligible.isEmpty()) {
            throw new IllegalStateException(
                    "No warehouse can fulfill all requested items"
            );
        }

        return eligible;
    }

    @Transactional
    public void reserveInventory(
            Integer warehouseId,
            Map<Integer, Integer> items
    ) {
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            int updated =
                    inventoryRepository.reserveStock(
                            warehouseId,
                            entry.getKey(),
                            entry.getValue()
                    );

            if (updated != 1) {
                throw new IllegalStateException(
                        "Insufficient stock for product " + entry.getKey()
                );
            }
        }
    }
}
