package cdac.project.logigraph.order.service;

import cdac.project.logigraph.inventory.service.InventoryService;
import cdac.project.logigraph.order.dto.PlaceOrderRequest;
import cdac.project.logigraph.order.entity.Order;
import cdac.project.logigraph.order.entity.OrderItem;
import cdac.project.logigraph.order.entity.OrderStatusHistory;
import cdac.project.logigraph.order.enums.OrderStatus;
import cdac.project.logigraph.order.repository.OrderItemRepository;
import cdac.project.logigraph.order.repository.OrderRepository;
import cdac.project.logigraph.order.repository.OrderStatusHistoryRepository;
import cdac.project.logigraph.routing.service.RoutingService;
import cdac.project.logigraph.vehicle.service.VehicleAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderPlacementService {

    private final InventoryService inventoryService;
    private final RoutingService routingService;
    private final VehicleAssignmentService vehicleAssignmentService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository statusHistoryRepository;

    public OrderPlacementService(
            InventoryService inventoryService,
            RoutingService routingService,
            VehicleAssignmentService vehicleAssignmentService,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderStatusHistoryRepository statusHistoryRepository
    ) {
        this.inventoryService = inventoryService;
        this.routingService = routingService;
        this.vehicleAssignmentService = vehicleAssignmentService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    /**
     * Main orchestration method for placing an order.
     */
    @Transactional
    public Order placeOrder(
            Integer customerId,
            PlaceOrderRequest request
    ) {

        // 1️⃣ Find warehouses that can fulfill ALL items
        Set<Integer> eligibleWarehouses =
                inventoryService.findWarehousesThatCanFulfillAllItems(
                        request.getItems()
                );

        // 2️⃣ Select nearest warehouse using Dijkstra + last-mile distance
        Integer fulfillmentWarehouseId =
                routingService.selectNearestWarehouse(
                        eligibleWarehouses,
                        request.getDestLat(),
                        request.getDestLng()
                );

        // 3️⃣ Create Order
        Order order = new Order();
        order.setTrackingId(generateTrackingId());
        order.setCustomerId(customerId);
        order.setFulfillmentWarehouseId(fulfillmentWarehouseId);
        order.setStatus(OrderStatus.PLACED);
        order.setDestLat(request.getDestLat());
        order.setDestLng(request.getDestLng());

        order = orderRepository.save(order);

        // 4️⃣ Create Order Items
        for (Map.Entry<Integer, Integer> entry : request.getItems().entrySet()) {

            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(entry.getKey());
            item.setQuantity(entry.getValue());

            orderItemRepository.save(item);
        }

        // 5️⃣ Reserve Inventory (atomic, transactional)
        inventoryService.reserveInventory(
                fulfillmentWarehouseId,
                request.getItems()
        );

        // 6️⃣ Save Order Status History (timestamp auto-set by entity)
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setStatus(OrderStatus.PLACED);
        statusHistoryRepository.save(history);

        // 7️⃣ Assign vehicle AFTER transaction commits
        Integer orderId = order.getId();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        vehicleAssignmentService.assignVehicle(orderId);
                    }
                }
        );

        return order;
    }

    private String generateTrackingId() {
        return "LG-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}
