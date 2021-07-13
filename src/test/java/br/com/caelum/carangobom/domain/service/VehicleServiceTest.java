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

class VehicleServiceTest {

    private MarcaRepository marcaRepository = new MarcaRepositoryMock();
    private VehicleRepository vehicleRepository= new VehicleRepositoryMock();

    VehicleService setup(){
        return new VehicleService(this.marcaRepository, this.vehicleRepository);
    }

    VehicleForm createVehicle(String model, int year, double price, Long marcaId){
        return new VehicleForm(null,model,price,year,null, marcaId);
    }

    Marca createMarca(Marca marca){
        return this.marcaRepository.save(marca);
    }

    @Test
    void shouldCreateAVehicle() throws NotFoundException {
        String model = "Audi r8";
        int year = 1997;
        double price = 2000;
        VehicleService vehicleService = setup();
        Marca marca =  createMarca(new MarcaDummy(1L,"Audi"));
        VehicleForm vehicle = createVehicle(model, year, price, marca.getId());
        Vehicle savedVehicle = vehicleService.createVehicle(vehicle);
        assertEquals(1L, savedVehicle.getId());
        assertEquals(savedVehicle.getModel(), model);
        assertEquals(savedVehicle.getPrice(), price);
        assertEquals(savedVehicle.getYear(), year);
        assertEquals(savedVehicle.getMarca().getId(), marca.getId());
    }

    @Test
    void shouldReturnAErrorNotFoundAMarcaWhenCreatingVehicle(){
        String model = "Audi r8";
        int year = 1997;
        double price = 2000;
        Long marcaId = 404L;
        VehicleService vehicleService = setup();
        VehicleForm vehicle = createVehicle(model, year, price, marcaId);
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class,()->vehicleService.createVehicle(vehicle)
        );
        assertEquals("Marca not found", notFoundException.getMessage());
    }

    @Test
    void shouldReturnAnUpddateVehicle() throws NotFoundException {
        Marca marca =  createMarca(new MarcaDummy(1L,"Audi"));
        Vehicle savedVehicle = this.vehicleRepository.save(createVehicle("Audi",2010,1000, marca.getId()));
        String model = "Ford k";
        int year = 2016;
        double price = 3200;
        VehicleService vehicleService = setup();
        VehicleForm vehicle = createVehicle(model, year, price, marca.getId());
        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle, savedVehicle.getId());
        assertEquals(marca.getId(), updatedVehicle.getMarca().getId());
        assertEquals(year, updatedVehicle.getYear());
        assertEquals(price, updatedVehicle.getPrice());
        assertEquals(model, updatedVehicle.getModel());
    }
    @Test
    void shouldReturnAnErrorOnUpdateAVehicleWhenTheMarcaDoesntExists() throws NotFoundException {
        Long marcaId = 200L;
        Vehicle savedVehicle = this.vehicleRepository.save(createVehicle("Audi",2010,1000, marcaId));
        String model = "Ford k";
        int year = 2016;
        double price = 3200;
        VehicleService vehicleService = setup();
        VehicleForm vehicle = createVehicle(model, year, price, marcaId);
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class,
                ()->vehicleService.updateVehicle(vehicle,savedVehicle.getId())
        );
        assertEquals("Marca not found",notFoundException.getMessage());
    }
    @Test
    void shouldReturnAnErrorOnUpdateAVehicleWhenTheVehicleDoesntExists() throws NotFoundException {
        Marca marca =  createMarca(new MarcaDummy(1L,"Audi"));
        Long vehicleId = 200L;
        String model = "Ford k";
        int year = 2016;
        double price = 3200;
        VehicleService vehicleService = setup();
        VehicleForm vehicle = createVehicle(model, year, price, marca.getId());
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class,
                ()->vehicleService.updateVehicle(vehicle, vehicleId)
        );
        assertEquals("Vehicle not found",notFoundException.getMessage());
    }
}
