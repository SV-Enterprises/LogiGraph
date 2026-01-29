package cdac.project.logigraph.inventory.dto;

import cdac.project.logigraph.inventory.entity.Warehouse;

public class WarehouseResponse {

    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private Boolean active;

    public static WarehouseResponse fromEntity(Warehouse warehouse) {

        WarehouseResponse response = new WarehouseResponse();
        response.id = warehouse.getId();
        response.name = warehouse.getName();
        response.latitude = warehouse.getLatitude();
        response.longitude = warehouse.getLongitude();
        response.address = warehouse.getAddress();
        response.active = warehouse.getActive();

        return response;
    }

    public Integer getId() {
        return id;
    }

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
