package cdac.project.logigraph.inventory.dto;

import jakarta.validation.constraints.NotNull;

public class AdjustInventoryRequest {

    @NotNull(message = "Warehouse ID is required")
    private Integer warehouseId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    /**
     * Can be positive (add) or negative (reduce).
     * Zero is not allowed (validated in service layer).
     */
    @NotNull(message = "Delta is required")
    private Integer delta;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getDelta() {
        return delta;
    }
}
