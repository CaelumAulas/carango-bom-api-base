package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.service.VehicleService;
import br.com.caelum.carangobom.infra.controller.request.CreateVehicleRequest;
import br.com.caelum.carangobom.infra.controller.response.VehicleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @PostMapping
    ResponseEntity<VehicleResponse> createVehicle(@RequestBody CreateVehicleRequest createVehicleRequest){
        try{
            Vehicle createdVehicle = vehicleService.createVehicle(
                    createVehicleRequest.toVehicleForm(),
                    createVehicleRequest.getMarcaId()
            );
            return ResponseEntity.ok(new VehicleResponse(createdVehicle));
        }catch (NotFoundException exception){
            return ResponseEntity.notFound().build();
        }

    }
}
