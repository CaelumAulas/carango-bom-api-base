package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.entity.form.VehicleForm;
import br.com.caelum.carangobom.domain.repository.MarcaRepository;
import br.com.caelum.carangobom.domain.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    private MarcaRepository marcaRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(MarcaRepository marcaRepository, VehicleRepository vehiceRepository){
        this.marcaRepository = marcaRepository;
        this.vehicleRepository = vehiceRepository;
    }

    public Vehicle createVehicle(VehicleForm vehicle, Long marcaId) throws NotFoundException {
        Optional<Marca> marca = marcaRepository.findById(marcaId);
        if(!marca.isPresent()){
            throw new NotFoundException("Marca not found");
        }
        vehicle.setMarca(marca.get());
        return vehicleRepository.save(vehicle);
    }
}
