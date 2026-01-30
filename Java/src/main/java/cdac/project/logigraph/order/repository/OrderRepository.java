package cdac.project.logigraph.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.order.entity.Order;
import cdac.project.logigraph.order.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByTrackingId(String trackingId);

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findByStatus(OrderStatus status);

    // 🔴 ADD FOR DASHBOARD (READ-ONLY)

    /**
     * Latest orders for manager overview
     */
    List<Order> findTop50ByOrderByCreatedAtDesc();

    /**
     * Order count per status (dashboard metrics)
     */
    long countByStatus(OrderStatus status);
}
