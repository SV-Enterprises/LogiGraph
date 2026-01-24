package cdac.project.logigraph.inventory.entity;

import java.io.Serializable;
import java.util.Objects;

public class WarehouseInventoryId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer warehouseId;
    private Integer productId;

    public WarehouseInventoryId() {}

    public WarehouseInventoryId(Integer warehouseId, Integer productId) {
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseInventoryId)) return false;
        WarehouseInventoryId that = (WarehouseInventoryId) o;
        return Objects.equals(warehouseId, that.warehouseId)
            && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, productId);
    }
}
