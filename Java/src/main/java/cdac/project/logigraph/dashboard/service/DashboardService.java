package cdac.project.logigraph.dashboard.service;

import cdac.project.logigraph.dashboard.dto.FleetStatusView;
import cdac.project.logigraph.dashboard.dto.LowStockView;
import cdac.project.logigraph.dashboard.dto.OrderSummaryView;
import cdac.project.logigraph.inventory.repository.WarehouseInventoryRepository;
import cdac.project.logigraph.order.enums.OrderStatus;
import cdac.project.logigraph.order.repository.OrderRepository;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import cdac.project.logigraph.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final OrderRepository orderRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseInventoryRepository inventoryRepository;

    public DashboardService(
            OrderRepository orderRepository,
            VehicleRepository vehicleRepository,
            WarehouseInventoryRepository inventoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /* =========================
       ORDERS OVERVIEW
       ========================= */

    public List<OrderSummaryView> recentOrders() {

        return orderRepository.findTop50ByOrderByCreatedAtDesc()
                .stream()
                .map(o -> new OrderSummaryView(
                        o.getId(),
                        o.getTrackingId(),
                        o.getStatus(),
                        o.getAssignedVehicleId(),
                        o.getFulfillmentWarehouseId(),
                        o.getCreatedAt()
                ))
                .toList();
    }

    public Map<OrderStatus, Long> ordersByStatus() {

        Map<OrderStatus, Long> result = new EnumMap<>(OrderStatus.class);

        for (OrderStatus status : OrderStatus.values()) {
            result.put(status, orderRepository.countByStatus(status));
        }
        return result;
    }

    /* =========================
       FLEET OVERVIEW
       ========================= */

    public List<FleetStatusView> fleetStatus() {

        List<FleetStatusView> result = new ArrayList<>();

        for (VehicleStatus status : VehicleStatus.values()) {
            result.add(
                    new FleetStatusView(
                            status,
                            vehicleRepository.countByStatus(status)
                    )
            );
        }
        return result;
    }

    /* =========================
       INVENTORY ALERTS
       ========================= */

    public List<LowStockView> lowStock(int threshold) {

        return inventoryRepository.findByQuantityLessThan(threshold)
                .stream()
                .map(inv ->
                        new LowStockView(
                                inv.getWarehouseId(),
                                inv.getProductId(),
                                inv.getQuantity()
                        )
                )
                .toList();
    }
}
