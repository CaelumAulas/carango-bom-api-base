package br.com.caelum.carangobom.repository;

import br.com.caelum.carangobom.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByOrderByNome();

    Usuario findByEmail(String email);
}
