package br.com.caelum.carangobom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.modelo.Marca;


@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>  {
    
    List<Marca> findAllByOrderByNome();
    
    Marca findByNome(String nome);

}
