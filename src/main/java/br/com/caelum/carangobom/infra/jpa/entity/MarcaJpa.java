package br.com.caelum.carangobom.infra.jpa.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import br.com.caelum.carangobom.domain.entity.Marca;
import lombok.Data;

@Data
@Entity(name = "marca")
public class MarcaJpa implements Marca {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;

    public MarcaJpa() {

    }

    public MarcaJpa(String nome) {
        this(null, nome);
    }

    public MarcaJpa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
