package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${br.com.caelum.carangobom.jwt.secret}")
    private String key;

    @Value("${br.com.caelum.carangobom.jwt.expiration}")
    private String validadeToken;

    public String generateToken(Authentication authentication) {
    	Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(usuarioLogado.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(validadeToken)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
