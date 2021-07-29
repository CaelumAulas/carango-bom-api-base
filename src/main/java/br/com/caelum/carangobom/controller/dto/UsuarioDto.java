package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UsuarioDto {

    public Long id;
    public String nome;
    public String email;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public static List<UsuarioDto> toList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
    }

}
