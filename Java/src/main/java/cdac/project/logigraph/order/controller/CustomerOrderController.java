package cdac.project.logigraph.order.controller;

import cdac.project.logigraph.customer.service.CustomerService;
import cdac.project.logigraph.order.dto.CreateOrderRequest;
import cdac.project.logigraph.order.dto.OrderResponse;
import cdac.project.logigraph.order.entity.Order;
import cdac.project.logigraph.order.service.OrderPlacementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/orders")
public class CustomerOrderController {

    private final OrderPlacementService orderPlacementService;
    private final CustomerService customerService;

    public CustomerOrderController(
            OrderPlacementService orderPlacementService,
            CustomerService customerService
    ) {
        this.orderPlacementService = orderPlacementService;
        this.customerService = customerService;
    }

    /**
     * CUSTOMER
     * Place a new order.
     *
     * Flow:
     * 1. Authenticated username → customerId
     * 2. OrderPlacementService orchestrates:
     *    - warehouse selection
     *    - inventory reservation
     *    - order creation
     *    - vehicle assignment (after commit)
     *
     * HTTP 201 CREATED
     */
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody CreateOrderRequest request,
            Authentication authentication
    ) {
        // Identity from Spring Security (JWT → username)
        String username = authentication.getName();

        // Resolve domain customerId
        Integer customerId =
                customerService.getCustomerIdByUsername(username);

        // Delegate to domain service
        Order order =
                orderPlacementService.placeOrder(
                        customerId,
                        request
                );

        // DTO response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.fromEntity(order));
    }
}
