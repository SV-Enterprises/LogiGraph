package cdac.project.logigraph.routing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cdac.project.logigraph.routing.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByActiveTrue();
}
