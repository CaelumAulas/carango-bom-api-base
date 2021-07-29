package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutenticacaoDto {

	public Long id;
	public String nome;
	public String email;
	public String token;

	public AutenticacaoDto(Usuario usuario, String token) {
		this(usuario.getId(), usuario.getNome(), usuario.getEmail(), token);
	}
}
