package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);
}
