package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class VeiculoDto {

    public Long id;
    public String modelo;
    public Date ano;
    public BigDecimal valor;

    @ManyToOne
    public Marca marca;

    public VeiculoDto(Veiculo veiculo) {
        this(veiculo.getId(), veiculo.getModelo(), veiculo.getAno(), veiculo.getValor(), veiculo.getMarca());
    }

    public static List<VeiculoDto> toList(List<Veiculo> veiculos) {
        return veiculos.stream().map(VeiculoDto::new).collect(Collectors.toList());
    }
}
