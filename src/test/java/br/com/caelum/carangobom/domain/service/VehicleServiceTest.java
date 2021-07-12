package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.MarcaDummy;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.VehicleDummy;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.entity.form.VehicleForm;
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

    VehicleForm createVehicle(String model, int year, double price){
        return new VehicleForm(null,model,price,year,null);
    }

    Marca createMarca(Marca marca){
        return this.marcaRepository.save(marca);
    }

    @Test
    public void shouldCreateAVehicle() throws NotFoundException {
        String model = "Audi r8";
        int year = 1997;
        double price = 2000;
        VehicleService vehicleService = setup();
        Marca marca =  createMarca(new MarcaDummy(1L,"Audi"));
        VehicleForm vehicle = createVehicle(model, year, price);
        Vehicle savedVehicle = vehicleService.createVehicle(vehicle, marca.getId());
        assertEquals(savedVehicle.getId(), 1L);
        assertEquals(savedVehicle.getModel(), model);
        assertEquals(savedVehicle.getPrice(), price);
        assertEquals(savedVehicle.getYear(), year);
        assertEquals(savedVehicle.getMarca().getId(), marca.getId());
    }

    @Test
    public void shouldReturnAErrorNotFoundAMarcaWhenCreatingVehicle(){
        String model = "Audi r8";
        int year = 1997;
        double price = 2000;
        Long marcaId = 404L;
        VehicleService vehicleService = setup();
        VehicleForm vehicle = createVehicle(model, year, price);
        NotFoundException notFoundException = assertThrows(NotFoundException.class,()->vehicleService.createVehicle(vehicle, marcaId));
        assertEquals("Marca not found", notFoundException.getMessage());
    }
}
