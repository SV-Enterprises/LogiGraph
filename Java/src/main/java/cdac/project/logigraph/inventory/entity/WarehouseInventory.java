package cdac.project.logigraph.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_inventory")
@IdClass(WarehouseInventoryId.class)
public class WarehouseInventory {

    @Id
    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private int quantity;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
