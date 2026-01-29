package cdac.project.logigraph.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.inventory.entity.WarehouseInventory;
import cdac.project.logigraph.inventory.entity.WarehouseInventoryId;

@Repository
public interface WarehouseInventoryRepository
        extends JpaRepository<WarehouseInventory, WarehouseInventoryId> {

    Optional<WarehouseInventory> findByWarehouseIdAndProductId(
            Integer warehouseId,
            Integer productId
    );

    /**
     * Used ONLY for candidate warehouse discovery.
     * Do NOT use this for final stock reservation.
     */
    List<WarehouseInventory> findByProductIdAndQuantityGreaterThanEqual(
            Integer productId,
            int quantity
    );

    /**
     * Read inventory for a single warehouse (reporting only).
     */
    List<WarehouseInventory> findByWarehouseId(Integer warehouseId);

    /**
     * Atomic increment for stock addition.
     * Used by InventoryManagementService.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
    """)
    int incrementStock(
            Integer warehouseId,
            Integer productId,
            int qty
    );

    /**
     * Atomic adjustment (positive or negative).
     * Prevents negative stock.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :delta
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
          AND wi.quantity + :delta >= 0
    """)
    int adjustStock(
            Integer warehouseId,
            Integer productId,
            int delta
    );

    /**
     * Atomic stock reservation for orders.
     * Returns 1 if successful, 0 otherwise.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity - :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
          AND wi.quantity >= :qty
    """)
    int reserveStock(
            Integer warehouseId,
            Integer productId,
            int qty
    );

    /**
     * Compensation-only method.
     * Prefer transaction rollback instead.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
    """)
    int restoreStock(
            Integer warehouseId,
            Integer productId,
            int qty
    );
}
