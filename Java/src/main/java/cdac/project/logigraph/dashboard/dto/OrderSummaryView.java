package cdac.project.logigraph.dashboard.dto;

import cdac.project.logigraph.order.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderSummaryView {

    private final Integer orderId;
    private final String trackingId;
    private final OrderStatus status;
    private final Integer assignedVehicleId;
    private final Integer fulfillmentWarehouseId;
    private final LocalDateTime createdAt;

    public OrderSummaryView(
            Integer orderId,
            String trackingId,
            OrderStatus status,
            Integer assignedVehicleId,
            Integer fulfillmentWarehouseId,
            LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.trackingId = trackingId;
        this.status = status;
        this.assignedVehicleId = assignedVehicleId;
        this.fulfillmentWarehouseId = fulfillmentWarehouseId;
        this.createdAt = createdAt;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public Integer getFulfillmentWarehouseId() {
        return fulfillmentWarehouseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
