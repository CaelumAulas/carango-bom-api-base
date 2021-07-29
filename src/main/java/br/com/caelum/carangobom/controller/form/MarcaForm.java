package br.com.caelum.carangobom.controller.form;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaForm {
    private String nome;

    public Marca converter() {
        return new Marca(nome);
    }
    
    public Marca atualizar(Long id, MarcaRepository marcaRepository) {
    	Marca marca = marcaRepository.getOne(id);
    	marca.setNome(nome);
    	return marca;
    }
    
}
