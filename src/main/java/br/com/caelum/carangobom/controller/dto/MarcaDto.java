package br.com.caelum.carangobom.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.carangobom.modelo.Marca;

public class MarcaDto {
	
	public Long id;
	public String nome;

	public MarcaDto(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public MarcaDto(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
	}

	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}

	public static List<MarcaDto> toList(List<Marca> marcas) {
		return marcas.stream().map(MarcaDto::new).collect(Collectors.toList());
	}

}
