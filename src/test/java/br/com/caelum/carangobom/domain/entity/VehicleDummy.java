package br.com.caelum.carangobom.domain.entity;

import lombok.Data;

@Data
public class VehicleDummy implements Vehicle{

    Long id;
    String model;
    int year;
    double price;
    Marca marca;

    public VehicleDummy(String model, int year, double price){
        this.model = model;
        this.year = year;
        this.price = price;
    }
}
