package cdac.project.logigraph.dashboard.controller;

import cdac.project.logigraph.dashboard.dto.FleetStatusView;
import cdac.project.logigraph.dashboard.dto.LowStockView;
import cdac.project.logigraph.dashboard.dto.OrderSummaryView;
import cdac.project.logigraph.dashboard.service.DashboardService;
import cdac.project.logigraph.order.enums.OrderStatus;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manager/dashboard")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class ManagerDashboardController {

    private final DashboardService dashboardService;

    public ManagerDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/orders/recent")
    public ResponseEntity<List<OrderSummaryView>> recentOrders() {
        return ResponseEntity.ok(
                dashboardService.recentOrders()
        );
    }

    @GetMapping("/orders/status")
    public ResponseEntity<Map<OrderStatus, Long>> ordersByStatus() {
        return ResponseEntity.ok(
                dashboardService.ordersByStatus()
        );
    }

    @GetMapping("/fleet/status")
    public ResponseEntity<List<FleetStatusView>> fleetStatus() {
        return ResponseEntity.ok(
                dashboardService.fleetStatus()
        );
    }

    @GetMapping("/inventory/low-stock")
    public ResponseEntity<List<LowStockView>> lowStock(
            @RequestParam(defaultValue = "10")
            @Min(0) int threshold
    ) {
        return ResponseEntity.ok(
                dashboardService.lowStock(threshold)
        );
    }
}
