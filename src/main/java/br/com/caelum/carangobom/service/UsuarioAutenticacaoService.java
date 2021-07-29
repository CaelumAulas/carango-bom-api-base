package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.controller.dto.AutenticacaoDto;
import br.com.caelum.carangobom.controller.form.AutenticacaoForm;
import br.com.caelum.carangobom.exception.ExpiredTokenException;
import br.com.caelum.carangobom.exception.InvalidLoginException;
import br.com.caelum.carangobom.exception.InvalidTokenException;
import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

import java.util.Date;

@Service
public class UsuarioAutenticacaoService {

    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;

    @Autowired
    public UsuarioAutenticacaoService(UsuarioRepository usuarioRepository, TokenService tokenService){
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    public AutenticacaoDto autenticar(AutenticacaoForm autenticacaoForm){
        Usuario usuario = usuarioRepository.findByEmail(autenticacaoForm.getEmail());
        if(autenticacaoForm.getSenha().equals(usuario.getSenha())) {
            String token = "Bearer " + tokenService.generateToken(usuario);
            return new AutenticacaoDto(usuario, token);
        }
        else {
            throw new InvalidLoginException();
        }
    }

    private boolean validar(String token) {
        try {
            String tokenTratado = token.replace("Bearer ", "");
            Claims claims = tokenService.decodeToken(tokenTratado);
            System.out.println(claims.getIssuer());
            System.out.println(claims.getIssuedAt());

            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) throw new ExpiredTokenException();
            System.out.println(claims.getExpiration());
            return true;
        } catch (ExpiredTokenException et){
            et.printStackTrace();
            throw et;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTokenException();
        }
    }
}
