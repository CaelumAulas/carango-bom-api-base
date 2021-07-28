package br.com.caelum.carangobom.controller.dto;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDto {

    public Long id;
    public String nome;
    public String email;

    public UsuarioDto(long id, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }

    public static List<UsuarioDto> toList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
    }

}
