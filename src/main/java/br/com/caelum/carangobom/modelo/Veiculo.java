package br.com.caelum.carangobom.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String modelo;

    @NotNull
    private Date ano;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=20, fraction=2)
    private BigDecimal valor;

    public Veiculo(){

    }

    public Veiculo(String modelo, Date ano, BigDecimal valor, Marca marca) {
        this(null, modelo, ano, valor, marca);
    }

    public Veiculo(Long id, String modelo, Date ano, BigDecimal valor, Marca marca) {
        this.id = id;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.marca = marca;
    }

    @ManyToOne
    private Marca marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}