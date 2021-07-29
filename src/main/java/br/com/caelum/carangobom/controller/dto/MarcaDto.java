package br.com.caelum.carangobom.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.carangobom.modelo.Marca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MarcaDto {
	
	public Long id;
	public String nome;

	public MarcaDto(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
	}

	public static List<MarcaDto> toList(List<Marca> marcas) {
		return marcas.stream().map(MarcaDto::new).collect(Collectors.toList());
	}

}
