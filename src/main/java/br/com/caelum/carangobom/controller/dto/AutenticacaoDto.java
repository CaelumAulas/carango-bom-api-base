package br.com.caelum.carangobom.controller.dto;
import br.com.caelum.carangobom.modelo.Usuario;
import lombok.Getter;

@Getter
public class AutenticacaoDto {

	private Long id;
	private String nome;
	private String email;
	private String token;

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
