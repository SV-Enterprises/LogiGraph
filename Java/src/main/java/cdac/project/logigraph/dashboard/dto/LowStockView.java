package cdac.project.logigraph.dashboard.dto;

public class LowStockView {

    private final Integer warehouseId;
    private final Integer productId;
    private final int quantity;

    public LowStockView(
            Integer warehouseId,
            Integer productId,
            int quantity
    ) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
