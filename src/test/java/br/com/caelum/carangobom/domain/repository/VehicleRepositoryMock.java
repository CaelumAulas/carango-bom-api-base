package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Page<Vehicle> getAll(Pageable pageable) {
        if(pageable.isPaged()){
            return new PageImpl(
                    this.vehicleList.subList(
                            (int)pageable.getOffset(),
                            (int)(pageable.getOffset()+pageable.getPageSize())
                    ),
                    pageable,
                    this.vehicleList.size()
            );
        }else{
            return new PageImpl(
                    this.vehicleList,
                    pageable,
                    this.vehicleList.size()
            );
        }
    }

    @Override
    public void deleteVehicle(Long id) throws NotFoundException {
        Optional<Vehicle> optionalVehicle = this.findById(id);
        if(optionalVehicle.isPresent()){
            this.vehicleList = this.vehicleList.stream().filter(vehicle -> vehicle.getId() != id).collect(Collectors.toList());
        }else{
            throw new NotFoundException("Vehicle not found");
        }

    }
}
