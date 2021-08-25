package br.com.caelum.carangobom.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import br.com.caelum.carangobom.modelo.Marca;

public class MarcaFormUpdate {

    @NotNull @NotEmpty @NotBlank @Length(min = 2)
    private String nome;

    public MarcaFormUpdate() {
    }

    public MarcaFormUpdate(Marca pMarca) {
        this.nome = pMarca.getNome();
    }

    public String getNome() {
        return nome;
    }

    public Marca Update(Marca pMarca) {
        pMarca.setNome(this.nome);

        return pMarca;
    }
}
