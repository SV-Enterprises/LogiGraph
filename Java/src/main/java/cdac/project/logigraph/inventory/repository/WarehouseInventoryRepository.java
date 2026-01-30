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

    List<WarehouseInventory> findByProductIdAndQuantityGreaterThanEqual(
            Integer productId,
            int quantity
    );

    List<WarehouseInventory> findByWarehouseId(Integer warehouseId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
    """)
    int incrementStock(Integer warehouseId, Integer productId, int qty);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :delta
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
          AND wi.quantity + :delta >= 0
    """)
    int adjustStock(Integer warehouseId, Integer productId, int delta);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity - :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
          AND wi.quantity >= :qty
    """)
    int reserveStock(Integer warehouseId, Integer productId, int qty);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
    """)
    int restoreStock(Integer warehouseId, Integer productId, int qty);

    // 🔹 Dashboard (global)
    List<WarehouseInventory> findByQuantityLessThan(int quantity);

    // 🔹 Dashboard (warehouse-specific)
    List<WarehouseInventory> findByWarehouseIdAndQuantityLessThan(
            Integer warehouseId,
            int threshold
    );
}

