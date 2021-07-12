package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.MarcaDummy;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.VehicleDummy;
import br.com.caelum.carangobom.domain.repository.MarcaRepository;
import br.com.caelum.carangobom.domain.repository.MarcaRepositoryMock;
import br.com.caelum.carangobom.domain.repository.VehicleRepository;
import br.com.caelum.carangobom.domain.repository.VehicleRepositoryMock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleServiceTest {

    private MarcaRepository marcaRepository = new MarcaRepositoryMock();
    private VehicleRepository vehicleRepository= new VehicleRepositoryMock();

    VehicleService setup(){
        return new VehicleService(this.marcaRepository, this.vehicleRepository);
    }

    VehicleDummy createVehicle(String model, int year, double price){
        return new VehicleDummy(model, year, price);
    }

    Marca createMarca(Marca marca){
        return this.marcaRepository.save(marca);
    }

    @Test
    public void shouldCreateAVehicle(){
        String model = "";
        int year = 1997;
        double price = 2000;
        VehicleService vehicleService = setup();
        Marca marca =  createMarca(new MarcaDummy(1L,"Audi"));
        Vehicle vehicle = createVehicle(model, year, price);
        Vehicle savedVehicle = vehicleService.createVehicle(vehicle, marca.getId());
        assertEquals(savedVehicle.getId(), 1L);
        assertEquals(savedVehicle.getModel(), model);
        assertEquals(savedVehicle.getPrice(), price);
        assertEquals(savedVehicle.getYear(), year);
        assertEquals(savedVehicle.getMarca().getId(), marca.getId());
    }
}
