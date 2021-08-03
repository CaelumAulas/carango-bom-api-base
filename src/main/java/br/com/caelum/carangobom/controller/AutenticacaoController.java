package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.AutenticacaoDto;
import br.com.caelum.carangobom.controller.form.AutenticacaoForm;
import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
import br.com.caelum.carangobom.service.TokenService;
import br.com.caelum.carangobom.service.UsuarioAutenticacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	private UsuarioRepository usuarioRepository;
	private TokenService tokenService;
	
	@Autowired
	UsuarioAutenticacaoService usuarioAutenticacaoService;
	
	@Autowired
	AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<AutenticacaoDto> login(@RequestBody @Valid AutenticacaoForm autenticacaoForm) {
    	UsernamePasswordAuthenticationToken dadosLogin = autenticacaoForm.converter();
    	
    	try {
    		Optional<Usuario> usuario = usuarioRepository.findByEmail(autenticacaoForm.getEmail());
			Authentication authentication = authenticationManager.authenticate(dadosLogin);
			String token = "Bearer " + tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new AutenticacaoDto(usuario.get(), token));
    	}catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
    }
}
