package br.com.caelum.carangobom.infra.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AuthenticationRequest {

    @NotNull @NotEmpty
    private String username;
    @NotNull @NotEmpty
    private String password;

    public UsernamePasswordAuthenticationToken parseUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
