package cdac.project.logigraph.inventory.service;

import cdac.project.logigraph.inventory.dto.CreateWarehouseRequest;
import cdac.project.logigraph.inventory.dto.UpdateWarehouseRequest;
import cdac.project.logigraph.inventory.entity.Warehouse;
import cdac.project.logigraph.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public Warehouse createWarehouse(CreateWarehouseRequest request) {

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setLatitude(request.getLatitude());
        warehouse.setLongitude(request.getLongitude());
        warehouse.setAddress(request.getAddress());
        warehouse.setActive(true);

        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public Warehouse updateWarehouse(
            Integer warehouseId,
            UpdateWarehouseRequest request
    ) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Warehouse not found: " + warehouseId
                        )
                );

        if (request.getName() != null) {
            warehouse.setName(request.getName());
        }
        if (request.getLatitude() != null) {
            warehouse.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            warehouse.setLongitude(request.getLongitude());
        }
        if (request.getAddress() != null) {
            warehouse.setAddress(request.getAddress());
        }
        if (request.getActive() != null) {
            warehouse.setActive(request.getActive());
        }

        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(Integer warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Warehouse not found: " + warehouseId
                        )
                );
    }
}
