package br.com.caelum.carangobom.controller.form;

import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioForm {
    private String nome;
    private String email;
    private String senha;

    public Usuario converter() {
        return new Usuario(nome, email, senha);
    }

    public Usuario atualizar(Long id, UsuarioRepository usuarioRepository) {
        Usuario usuario = usuarioRepository.getOne(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
