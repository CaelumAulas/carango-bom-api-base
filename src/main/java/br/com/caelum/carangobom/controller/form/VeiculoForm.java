package br.com.caelum.carangobom.controller.form;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Veiculo;
import br.com.caelum.carangobom.repository.VeiculoRepository;

import java.math.BigDecimal;
import java.util.Date;

public class VeiculoForm {
    private String modelo;
    private Date ano;
    private BigDecimal valor;
    private Marca marca;

    public String getModelo() {
        return modelo;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Veiculo converter() {
        return new Veiculo(modelo, ano, valor, marca);
    }

    public Veiculo atualizar(Long id,VeiculoRepository veiculoRepository) {
        Veiculo veiculo = veiculoRepository.getOne(id);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setValor(valor);
        veiculo.setMarca(marca);
        return veiculo;
    }
}
