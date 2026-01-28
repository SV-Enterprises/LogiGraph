package cdac.project.logigraph.order.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collections;
import java.util.Map;

public class PlaceOrderRequest {

    /**
     * Customer delivery latitude
     */
    @NotNull(message = "Destination latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double destLat;

    /**
     * Customer delivery longitude
     */
    @NotNull(message = "Destination longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double destLng;

    /**
     * productId -> quantity
     */
    @NotEmpty(message = "Order must contain at least one product")
    private Map<
            @Positive(message = "Product ID must be positive") Integer,
            @Positive(message = "Quantity must be positive")
            @Max(value = 1000, message = "Quantity cannot exceed 1000")
            Integer
    > items;

    public Double getDestLat() {
        return destLat;
    }

    public Double getDestLng() {
        return destLng;
    }

    /**
     * Returns an unmodifiable map to prevent request mutation
     */
    public Map<Integer, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }
}
