package br.com.caelum.carangobom.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.caelum.carangobom.domain.entity.Marca;

public interface MarcaRepository {
	public void delete(Marca marca);

    public Marca save(Marca marca);

    public Optional<Marca> findById(Long id);

    public List<Marca> findAllByOrderByNome();
}
