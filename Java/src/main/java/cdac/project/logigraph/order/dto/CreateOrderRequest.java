package cdac.project.logigraph.order.dto;

import jakarta.validation.constraints.*;
import java.util.Map;

public class CreateOrderRequest {

    /**
     * productId -> quantity
     */
    @NotEmpty(message = "Order must contain at least one product")
    private Map<
            @NotNull Integer,
            @NotNull @Positive Integer
    > items;

    @NotNull(message = "Destination latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double destLat;

    @NotNull(message = "Destination longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double destLng;

    public Map<Integer, Integer> getItems() {
        return Map.copyOf(items);
    }

    public Double getDestLat() {
        return destLat;
    }

    public Double getDestLng() {
        return destLng;
    }
}
