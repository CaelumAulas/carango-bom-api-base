package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;

import java.util.Optional;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long vehicleId);
}
