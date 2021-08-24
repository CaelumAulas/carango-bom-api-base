package br.com.caelum.carangobom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import br.com.caelum.carangobom.modelo.Marca;

public interface MarcaRepository extends JpaRepository<Marca,Long>{

    public List<Marca> findAllByOrderByNome();

}
