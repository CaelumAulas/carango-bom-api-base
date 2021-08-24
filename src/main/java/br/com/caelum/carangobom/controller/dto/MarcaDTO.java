package br.com.caelum.carangobom.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.carangobom.modelo.Marca;

public class MarcaDTO {

    private Long id;
    private String nome;

    public MarcaDTO(Marca pMarca) {
        this.id = pMarca.getId();
        this.nome = pMarca.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static List<MarcaDTO> converter(List<Marca> pMarca){
        return pMarca.stream().map(MarcaDTO::new).collect(Collectors.toList());
    }
}