package br.com.caelum.carangobom.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacaoForm {
    private String email;
    private String senha;
    
    
    public UsernamePasswordAuthenticationToken converter() {
    	return new UsernamePasswordAuthenticationToken(senha, email);
    }


	public String getEmail() {
		return email;
	}


	public String getSenha() {
		return senha;
	}

    
}
