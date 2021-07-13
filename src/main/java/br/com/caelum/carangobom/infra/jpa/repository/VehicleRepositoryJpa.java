package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.repository.VehicleRepository;
import br.com.caelum.carangobom.infra.jpa.entity.VehicleJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class VehicleRepositoryJpa implements VehicleRepository {

    private EntityManager entityManager;

    @Autowired
    VehicleRepositoryJpa(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    private VehicleJpa vehicleToVehicleJpa(Vehicle vehicle){
        return new VehicleJpa(vehicle);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleJpa vehicleJpa = vehicleToVehicleJpa(vehicle);
        this.entityManager.persist(vehicleJpa);
        return vehicleJpa;
    }

    @Override
    public Optional<Vehicle> findById(Long vehicleId) {
        VehicleJpa vehicleJpa = this.entityManager.find(VehicleJpa.class, vehicleId);
        return Optional.ofNullable(vehicleJpa);
    }
}
