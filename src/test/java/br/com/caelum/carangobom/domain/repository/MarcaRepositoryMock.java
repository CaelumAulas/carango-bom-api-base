package br.com.caelum.carangobom.domain.repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.MarcaDummy;
import lombok.Setter;

public class MarcaRepositoryMock implements MarcaRepository {

	@Setter
	private List<MarcaDummy> marcas = new ArrayList<>();

	private Long generateId(){
		List<Long> ids = marcas
				.stream()
				.map(MarcaDummy::getId)
				.collect(Collectors.toList());
		Long maxId = Collections.max(ids);
		if(maxId==null) {
			maxId = 0L;
		}
		return maxId+1;
	}
	
	@Override
	public void delete(Marca marca) {
		this.setMarcas(
				marcas.stream()
					.filter((Marca m1)-> m1.getId() != marca.getId())
					.collect(Collectors.toList())
		);
	}

	@Override
	public Marca save(Marca marca) {
		if(marca.getId() == null) {
			marca.setId(this.generateId());
		}
		marcas.add((MarcaDummy)marca);
		return marca;
	}

	@Override
	public Optional<Marca> findById(Long id) {
		Optional<MarcaDummy> marca = this.marcas.stream().filter((MarcaDummy m1) -> m1.getId().equals(id)).findFirst();
		if(marca.isPresent()) {
			return Optional.of(marca.get());
		}else {
			return Optional.empty();
		}
	}

	@Override
	public List<Marca> findAllByOrderByNome() {
		return this.marcas
				.stream()
				.sorted(Comparator.comparing((Function<Marca, String>) Marca::getNome))
				.collect(Collectors.toList());
	}

}
