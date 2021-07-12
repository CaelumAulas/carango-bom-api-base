package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.domain.entity.form.VehicleForm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateVehicleRequest {
    String model;
    double price;
    int year;
    Long marcaId;

    public VehicleForm toVehicleForm() {
        return new VehicleForm(null,model,price,year,null);
    }
}
