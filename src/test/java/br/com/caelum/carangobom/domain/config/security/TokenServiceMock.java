package br.com.caelum.carangobom.domain.config.security;

import br.com.caelum.carangobom.infra.config.security.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class TokenServiceMock extends TokenService {

    private final Long expiration = 3600L;
    private final String secret = "t35tS5Cr5T";

    @Override
    public String generateToken(Authentication auth) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("Carango Bom")
                .setSubject((String) auth.getPrincipal())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Long getUserId(String token) {
        return 0L;
    }
}
