package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleRepositoryMock implements VehicleRepository {

    private List<Vehicle> vehicleList = new ArrayList<Vehicle>();

    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicle.setId(1L);
        vehicleList.add(vehicle);
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(Long vehicleId) {
        return this.vehicleList
                .stream()
                .filter(vehicle -> vehicle.getId().equals(vehicleId))
                .findFirst();
    }
}
