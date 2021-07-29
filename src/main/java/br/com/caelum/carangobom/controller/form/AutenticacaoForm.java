package br.com.caelum.carangobom.controller.form;

import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacaoForm {
    private String email;
    private String senha;
}
