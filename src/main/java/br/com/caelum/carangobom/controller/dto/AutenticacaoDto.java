package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Marca;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AutenticacaoDto {

	public Long id;
	public String nome;
	public String email;
	public String token;

	public AutenticacaoDto(long id, String nome, String email, String token) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.token = token;
	}
}
