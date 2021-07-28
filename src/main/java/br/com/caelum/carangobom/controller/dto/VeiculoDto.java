package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Veiculo;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoDto {

    public Long id;
    public String modelo;
    public Date ano;
    public BigDecimal valor;

    @ManyToOne
    public Marca marca;

    public VeiculoDto(Long id, String modelo, Date ano, BigDecimal valor, Marca marca) {
        this.id = id;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public Date getAno() {
        return ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Marca getMarca() {
        return marca;
    }

    public static List<VeiculoDto> toList(List<Veiculo> veiculos) {
        return veiculos.stream().map(veiculo -> {
            return new VeiculoDto(veiculo.getId(), veiculo.getModelo(), veiculo.getAno(), veiculo.getValor(), veiculo.getMarca());
        }).collect(Collectors.toList());
    }
}
