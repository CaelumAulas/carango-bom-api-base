package br.com.caelum.carangobom.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.repository.MarcaRepository;

public class MarcaService {
	private MarcaRepository marcaRepository;
	
	@Autowired
	public MarcaService(MarcaRepository marcaRepository) {
	}
	
	public Marca cadastra(Marca m1) {
        return this.marcaRepository.save(m1);
    }
	
}
