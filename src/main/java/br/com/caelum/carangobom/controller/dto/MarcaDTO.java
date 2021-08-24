package br.com.caelum.carangobom.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.carangobom.modelo.Marca;

public class MarcaDTO {

    private Long id;
    private String Nome;

    public MarcaDTO(Marca pMarca) {
        this.id = pMarca.getId();
        this.Nome = pMarca.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return Nome;
    }

    public static List<MarcaDTO> converter(List<Marca> pMarca){
        return pMarca.stream().map(MarcaDTO::new).collect(Collectors.toList());
    }
}