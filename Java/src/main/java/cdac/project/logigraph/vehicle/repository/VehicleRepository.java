package cdac.project.logigraph.vehicle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.vehicle.entity.Vehicle;
import cdac.project.logigraph.vehicle.enums.VehicleStatus;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByStatus(VehicleStatus status);

    List<Vehicle> findByStatusIn(List<VehicleStatus> statuses);
}
