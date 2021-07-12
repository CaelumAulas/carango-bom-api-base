package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.repository.VehicleRepository;
import br.com.caelum.carangobom.infra.jpa.entity.VehicleJpa;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class VehicleRepositoryJpa implements VehicleRepository {

    private EntityManager entityManager;

    @Autowired
    VehicleRepositoryJpa(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleJpa vehicleJpa = new VehicleJpa(vehicle);
        this.entityManager.persist(vehicleJpa);
        return vehicleJpa;
    }
}
