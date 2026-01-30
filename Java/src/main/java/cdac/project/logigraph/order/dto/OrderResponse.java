package cdac.project.logigraph.order.dto;

import cdac.project.logigraph.order.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponse {

    private Integer orderId;
    private String trackingId;
    private OrderStatus status;
    private Integer fulfillmentWarehouseId;
    private Integer assignedVehicleId;
    private LocalDateTime createdAt;

    public static OrderResponse fromEntity(
            cdac.project.logigraph.order.entity.Order order
    ) {
        OrderResponse r = new OrderResponse();
        r.orderId = order.getId();
        r.trackingId = order.getTrackingId();
        r.status = order.getStatus();
        r.fulfillmentWarehouseId = order.getFulfillmentWarehouseId();
        r.assignedVehicleId = order.getAssignedVehicleId();
        r.createdAt = order.getCreatedAt();
        return r;
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

    public Integer getFulfillmentWarehouseId() {
        return fulfillmentWarehouseId;
    }

    public Integer getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
