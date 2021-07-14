package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.form.VehicleForm;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CreateVehicleRequest {
    @NotBlank
    String model;
    @NotNull @Positive @Min(message = "O valor minimo de um veículo é 100", value = 100)
    double price;
    @NotNull @Positive @Min(message = "O ano do veículo precisa ser maior que 1900", value = 1900)
    int year;
    @NotNull @Positive
    Long marcaId;

    public VehicleForm toVehicleForm() {
        return new VehicleForm(null,model,price,year,null, marcaId);
    }
}
