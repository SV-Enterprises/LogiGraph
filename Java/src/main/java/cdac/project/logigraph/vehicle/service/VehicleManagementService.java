package cdac.project.logigraph.vehicle.service;

import cdac.project.logigraph.inventory.repository.WarehouseRepository;
import cdac.project.logigraph.vehicle.dto.CreateVehicleRequest;
import cdac.project.logigraph.vehicle.dto.UpdateVehicleStatusRequest;
import cdac.project.logigraph.vehicle.dto.UpdateVehicleWarehouseRequest;
import cdac.project.logigraph.vehicle.dto.VehicleResponse;
import cdac.project.logigraph.vehicle.entity.Vehicle;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import cdac.project.logigraph.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleManagementService {

    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;

    public VehicleManagementService(
            VehicleRepository vehicleRepository,
            WarehouseRepository warehouseRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /* =========================
       VEHICLE REGISTRATION
       ========================= */

    @Transactional
    public VehicleResponse registerVehicle(CreateVehicleRequest request) {

        if (!warehouseRepository.existsById(request.getHomeWarehouseId())) {
            throw new IllegalArgumentException(
                    "Invalid warehouse ID: " + request.getHomeWarehouseId()
            );
        }

        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new IllegalArgumentException(
                    "Vehicle with this plate number already exists"
            );
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setType(request.getType());
        vehicle.setHomeWarehouseId(request.getHomeWarehouseId());
        vehicle.setCurrentWarehouseId(request.getHomeWarehouseId());

        // 🔴 ISSUE 1 FIX → Explicit initial status
        vehicle.setStatus(VehicleStatus.IDLE);

        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(saved);
    }

    /* =========================
       STATUS MANAGEMENT
       ========================= */

    @Transactional
    public VehicleResponse updateVehicleStatus(
            Integer vehicleId,
            UpdateVehicleStatusRequest request
    ) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vehicle not found: " + vehicleId
                        )
                );

        validateStatusTransition(
                vehicle.getStatus(),
                request.getStatus()
        );

        vehicle.setStatus(request.getStatus());

        // 🔴 ISSUE 2 FIX → Explicit save
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(saved);
    }

    /* =========================
       WAREHOUSE ASSIGNMENT
       ========================= */

    @Transactional
    public VehicleResponse updateVehicleWarehouse(
            Integer vehicleId,
            UpdateVehicleWarehouseRequest request
    ) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vehicle not found: " + vehicleId
                        )
                );

        if (vehicle.getStatus() == VehicleStatus.IN_TRANSIT ||
            vehicle.getStatus() == VehicleStatus.LOADING) {
            throw new IllegalStateException(
                    "Cannot change warehouse while vehicle is active"
            );
        }

        if (!warehouseRepository.existsById(request.getWarehouseId())) {
            throw new IllegalArgumentException(
                    "Invalid warehouse ID: " + request.getWarehouseId()
            );
        }

        vehicle.setCurrentWarehouseId(request.getWarehouseId());

        // 🔴 ISSUE 2 FIX → Explicit save
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(saved);
    }

    /* =========================
       READ OPERATIONS
       ========================= */

    @Transactional(readOnly = true)
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> getVehiclesByStatus(VehicleStatus status) {
        return vehicleRepository.findByStatus(status)
                .stream()
                .map(VehicleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /* =========================
       STATUS TRANSITION RULES
       ========================= */

    private void validateStatusTransition(
            VehicleStatus current,
            VehicleStatus next
    ) {
        if (current == next) {
            return;
        }

        switch (current) {
            case IDLE:
                if (next != VehicleStatus.ASSIGNED &&
                    next != VehicleStatus.MAINTENANCE) {
                    invalid(current, next);
                }
                break;

            case ASSIGNED:
                if (next != VehicleStatus.LOADING) {
                    invalid(current, next);
                }
                break;

            case LOADING:
                if (next != VehicleStatus.IN_TRANSIT) {
                    invalid(current, next);
                }
                break;

            case IN_TRANSIT:
                if (next != VehicleStatus.DELIVERED) {
                    invalid(current, next);
                }
                break;

            case DELIVERED:
            case MAINTENANCE:
                if (next != VehicleStatus.IDLE) {
                    invalid(current, next);
                }
                break;

            default:
                invalid(current, next);
        }
    }

    private void invalid(VehicleStatus from, VehicleStatus to) {
        throw new IllegalStateException(
                "Invalid vehicle status transition: " + from + " → " + to
        );
    }
}
