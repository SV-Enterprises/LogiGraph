package cdac.project.logigraph.order.entity;

import cdac.project.logigraph.order.enums.OrderStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tracking_id", nullable = false, unique = true, length = 50)
    private String trackingId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "fulfillment_warehouse_id", nullable = false)
    private Integer fulfillmentWarehouseId;

    @Column(name = "assigned_vehicle_id")
    private Integer assignedVehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "dest_lat", nullable = false)
    private double destLat;

    @Column(name = "dest_lng", nullable = false)
    private double destLng;

    public Integer getId() {
        return id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getFulfillmentWarehouseId() {
        return fulfillmentWarehouseId;
    }

    public Integer getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getDestLat() {
        return destLat;
    }

    public double getDestLng() {
        return destLng;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setFulfillmentWarehouseId(Integer fulfillmentWarehouseId) {
        this.fulfillmentWarehouseId = fulfillmentWarehouseId;
    }

    public void setAssignedVehicleId(Integer assignedVehicleId) {
        this.assignedVehicleId = assignedVehicleId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public void setDestLng(double destLng) {
        this.destLng = destLng;
    }
}
