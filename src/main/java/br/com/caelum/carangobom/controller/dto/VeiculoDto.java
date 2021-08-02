package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
public class VeiculoDto {

    private Long id;
    private String modelo;
    private Date ano;
    private BigDecimal valor;

    @ManyToOne
    public Marca marca;

    public VeiculoDto(Veiculo veiculo) {
        this(veiculo.getId(), veiculo.getModelo(), veiculo.getAno(), veiculo.getValor(), veiculo.getMarca());
    }

    public static Page<VeiculoDto> toList(Page<Veiculo> veiculos) {
        return veiculos.map(VeiculoDto::new);
    }
}
