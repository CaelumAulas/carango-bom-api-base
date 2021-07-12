package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepositoryMock implements VehicleRepository {

    private List<Vehicle> vehicleList = new ArrayList<Vehicle>();

    @Override
    public Vehicle save(Vehicle vehicle) {
        vehicle.setId(1L);
        vehicleList.add(vehicle);
        return vehicle;
    }
}
