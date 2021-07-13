package br.com.caelum.carangobom.domain.entity;

import lombok.Data;

@Data
public class VehicleDummy implements Vehicle{

    Long id;
    String model;
    Integer year;
    Double price;
    Marca marca;

    public VehicleDummy(String model, Integer year, Double price){
        this.model = model;
        this.year = year;
        this.price = price;
    }
}
