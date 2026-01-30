package cdac.project.logigraph.vehicle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.vehicle.entity.Vehicle;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;
import jakarta.persistence.LockModeType;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    /* =========================
       READ OPERATIONS
       ========================= */

    List<Vehicle> findByStatus(VehicleStatus status);

    List<Vehicle> findByStatusIn(List<VehicleStatus> statuses);

    long countByStatus(VehicleStatus status); // ✅ REQUIRED FOR DASHBOARD

    boolean existsByPlateNumber(String plateNumber);

    /* =========================
       ASSIGNMENT SAFETY
       ========================= */

    /**
     * Fetch ONE IDLE vehicle with DB-level lock.
     * Prevents double assignment across concurrent orders.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT v
        FROM Vehicle v
        WHERE v.status = cdac.project.logigraph.vehicle.enums.VehicleStatus.IDLE
        ORDER BY v.id
    """)
    Optional<Vehicle> findFirstIdleVehicleForUpdate();
}
