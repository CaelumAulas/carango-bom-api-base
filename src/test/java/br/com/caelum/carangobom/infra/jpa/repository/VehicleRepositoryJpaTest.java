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
import java.util.Optional;

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

    VehicleJpa createVehicle(VehicleJpa vehicleJpa){
        this.entityManager.persist(vehicleJpa);
        return vehicleJpa;
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

    @Test
    void shouldReturnAVehicleWhenFindById(){
        VehicleRepositoryJpa vehicleRepositoryJpa = setup();
        String model = "audi r8";
        int year = 2000;
        double price = 20000;
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa(null,"Audi"));
        VehicleJpa vehicleJpa = createVehicle(new VehicleJpa(null, model, year, price, marcaJpa));
        Optional<Vehicle> optionalVehicle = vehicleRepositoryJpa.findById(vehicleJpa.getId());
        assertTrue(optionalVehicle.isPresent());
        assertNotNull(optionalVehicle.get().getId());
        assertEquals(model, optionalVehicle.get().getModel());
        assertEquals(price, optionalVehicle.get().getPrice());
        assertEquals(year, optionalVehicle.get().getYear());
        assertEquals(marcaJpa.getId(), optionalVehicle.get().getMarca().getId());
    }

    @Test
    void shouldReturnOptionalNullWhenNotFindById(){
        VehicleRepositoryJpa vehicleRepositoryJpa = setup();
        Long vehicleId = 100L;
        Optional<Vehicle> optionalVehicle = vehicleRepositoryJpa.findById(vehicleId);
        assertFalse(optionalVehicle.isPresent());
    }
}
