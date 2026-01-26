package cdac.project.logigraph.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.inventory.entity.WarehouseInventory;
import cdac.project.logigraph.inventory.entity.WarehouseInventoryId;
import jakarta.transaction.Transactional;

@Repository
public interface WarehouseInventoryRepository
        extends JpaRepository<WarehouseInventory, WarehouseInventoryId> {

    Optional<WarehouseInventory> findByWarehouseIdAndProductId(
            Integer warehouseId,
            Integer productId
    );

    List<WarehouseInventory> findByProductIdAndQuantityGreaterThan(
            Integer productId,
            int quantity
    );

    @Modifying
    @Transactional
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity - :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
          AND wi.quantity >= :qty
    """)
    int reserveStock(Integer warehouseId, Integer productId, int qty);

    @Modifying
    @Transactional
    @Query("""
        UPDATE WarehouseInventory wi
        SET wi.quantity = wi.quantity + :qty
        WHERE wi.warehouseId = :warehouseId
          AND wi.productId = :productId
    """)
    int restoreStock(Integer warehouseId, Integer productId, int qty);
}
