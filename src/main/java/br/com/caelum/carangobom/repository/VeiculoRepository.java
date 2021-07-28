package br.com.caelum.carangobom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.modelo.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>   {
    List<Veiculo> findAllByOrderByModelo();
    Veiculo findByModelo(String modelo);
}
