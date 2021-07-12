package br.com.caelum.carangobom.infra.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CreateMarcaRequest{

	@NotBlank 
	@Size(min = 2, message = "Deve ter {min} ou mais caracteres.") 
	@Getter
	private String nome;

	public MarcaJpa toMarcaJpa(){
		return new MarcaJpa(nome);
	}
}
