package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Usuario;
import lombok.Getter;

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

	public AutenticacaoDto(Usuario usuario, String token) {
		this(usuario.getId(), usuario.getNome(), usuario.getEmail(), token);
	}
}
