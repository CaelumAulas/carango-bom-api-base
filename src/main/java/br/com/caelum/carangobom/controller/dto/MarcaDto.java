package br.com.caelum.carangobom.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Topico;
import br.com.caelum.carangobom.modelo.Marca;

public class MarcaDto {
	
	public Long id;
	public String nome;
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	
	public MarcaDto(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
	}
	
	public static List<MarcaDto> toList(List<Marca> marcas) {
		return marcas.stream().map(MarcaDto::new).collect(Collectors.toList());
	}

}
