package cdac.project.logigraph.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.order.entity.OrderStatusHistory;

@Repository
public interface OrderStatusHistoryRepository
        extends JpaRepository<OrderStatusHistory, Integer> {

    List<OrderStatusHistory> findByOrderIdOrderByChangedAtAsc(Integer orderId);
}
