package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import br.com.caelum.carangobom.infra.jpa.entity.VehicleJpa;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

@DataJpaTest
public class VehicleRepositoryJpaTest {

    @Autowired
    private EntityManager entityManager;

    VehicleRepositoryJpa setup(){
        return new VehicleRepositoryJpa(this.entityManager);
    }

    MarcaJpa createMarca(MarcaJpa marca){
        this.entityManager.persist(marca);
        return marca;
    }

    @Test
    void shouldSaveAnVehicle(){
        VehicleRepositoryJpa vehicleRepositoryJpa = setup();
        String model = "audi r8";
        int year = 2000;
        double price = 20000;
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa(null,"Audi"));
        VehicleJpa vehicleJpa = new VehicleJpa(null,model,year,price,marcaJpa);
        Vehicle savedVehicle = vehicleRepositoryJpa.save(vehicleJpa);
        assertNotNull(savedVehicle.getId());
        assertEquals(savedVehicle.getModel(), model);
        assertEquals(savedVehicle.getPrice(), price);
        assertEquals(savedVehicle.getYear(), year);
        assertEquals(savedVehicle.getMarca().getId(), marcaJpa.getId());
    }

    @Test
    void shouldThrowAnErrorWhenMarcaDontExistsOnSaveVehicle(){
        VehicleRepositoryJpa vehicleRepositoryJpa = setup();
        String model = "audi r8";
        int year = 2000;
        double price = 20000;
        MarcaJpa marcaJpa = new MarcaJpa(400L,"Audi");
        VehicleJpa vehicleJpa = new VehicleJpa(null,model,year,price,marcaJpa);
        assertThrows(PersistenceException.class,()->vehicleRepositoryJpa.save(vehicleJpa));
    }
}
