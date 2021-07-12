package br.com.caelum.carangobom.infra.jpa.entity;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "vehicle")
public class VehicleJpa implements Vehicle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private int year;
    private double price;
    @ManyToOne
    private MarcaJpa marca;

    public VehicleJpa(){}

    public VehicleJpa(Long id, String model, int year, double price, MarcaJpa marca){
        this.id = id;
        this.marca = marca;
        this.year = year;
        this.price = price;
        this.model = model;
    }

    public VehicleJpa(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.price = vehicle.getPrice();
        this.year = vehicle.getYear();
        this.model = vehicle.getModel();
        setMarca(vehicle.getMarca());
    }

    @Override
    public void setMarca(Marca marca) {
        this.marca = new MarcaJpa(marca.getId(), marca.getNome());
    }
}
