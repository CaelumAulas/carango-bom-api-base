package br.com.caelum.carangobom.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.repository.MarcaRepository;

@Service
public class MarcaService {
	private MarcaRepository marcaRepository;
	
	@Autowired
	public MarcaService(MarcaRepository marcaRepository) {
		this.marcaRepository = marcaRepository;
	}
	
	public Marca create(Marca marca) {
        return this.marcaRepository.save(marca);
    }

	public List<Marca> findAllByOrderByNome() {
		return this.marcaRepository.findAllByOrderByNome();
	}

	public Optional<Marca> findById(Long id) {
		return this.marcaRepository.findById(id);
	}

	public void deleteById(Long id) throws NotFoundException {
		System.out.println(id);
		Optional<Marca> marca = this.marcaRepository.findById(id);
		System.out.println(marca.isPresent());
		if(marca.isPresent()) {
			this.marcaRepository.delete(marca.get());			
		}
		else{
			throw new NotFoundException("Marca não encontrada");
		}
	}
	
	public Marca update(Marca marca, Long id) throws NotFoundException {
		Optional<Marca> optionalMarcaBase = this.findById(id);
		if(optionalMarcaBase.isPresent()) {
			Marca marcaBase = optionalMarcaBase.get();
			marcaBase.setNome(marca.getNome());
			this.marcaRepository.save(marcaBase);
			return marcaBase;
		}else {
			throw new NotFoundException("Marca não encontrada");
		}
	}
}
