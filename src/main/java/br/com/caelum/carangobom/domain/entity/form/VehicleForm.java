package br.com.caelum.carangobom.domain.entity.form;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleForm implements Vehicle {
    private Long id;
    private String model;
    private double price;
    private int year;
    private Marca marca;
}
