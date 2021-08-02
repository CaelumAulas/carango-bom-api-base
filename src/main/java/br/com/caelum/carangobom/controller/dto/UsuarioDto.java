package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    private String nome;
    private String email;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public static Page<UsuarioDto> toList(Page<Usuario> usuarios) {
        return usuarios.map(UsuarioDto::new);
    }

}
