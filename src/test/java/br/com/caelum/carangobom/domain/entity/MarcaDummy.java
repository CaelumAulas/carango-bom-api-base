package br.com.caelum.carangobom.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarcaDummy implements Marca {
	private Long id;
	private String nome;
	
	public MarcaDummy(String nome) {
		this.id = null;
		this.nome = nome;
	}
}
