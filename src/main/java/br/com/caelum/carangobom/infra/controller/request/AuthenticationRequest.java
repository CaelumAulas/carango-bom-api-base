package br.com.caelum.carangobom.infra.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull @NotEmpty
    private String username;
    @NotNull @NotEmpty
    private String password;

    public UsernamePasswordAuthenticationToken parseUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
