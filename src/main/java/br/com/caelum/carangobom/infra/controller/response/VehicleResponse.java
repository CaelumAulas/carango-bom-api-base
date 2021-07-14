package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.Vehicle;
import lombok.Getter;

@Getter
public class VehicleResponse {
    private MarcaResponse marca;
    private Long id;
    private double price;
    private int year;
    private String model;

    public VehicleResponse(Vehicle vehicle){
        this.marca = new MarcaResponse(vehicle.getMarca());
        this.id = vehicle.getId();
        this.price = vehicle.getPrice();
        this.year = vehicle.getYear();
        this.model = vehicle.getModel();
    }
}
