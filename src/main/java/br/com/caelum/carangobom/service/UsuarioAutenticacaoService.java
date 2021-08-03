package br.com.caelum.carangobom.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.carangobom.exception.ExpiredTokenException;
import br.com.caelum.carangobom.exception.InvalidTokenException;
import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioAutenticacaoService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;

    @Autowired
    public UsuarioAutenticacaoService(UsuarioRepository usuarioRepository, TokenService tokenService){
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @SuppressWarnings("unused")
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
		if(usuario.isPresent()) {
			return usuario.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos");
	}
}
