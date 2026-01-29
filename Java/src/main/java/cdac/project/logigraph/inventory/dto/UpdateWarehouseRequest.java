package cdac.project.logigraph.inventory.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class UpdateWarehouseRequest {

    @Size(max = 100, message = "Warehouse name must not exceed 100 characters")
    private String name;

    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double longitude;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    /**
     * Controls whether warehouse can be used for routing & fulfillment.
     */
    private Boolean active;

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getActive() {
        return active;
    }
}
