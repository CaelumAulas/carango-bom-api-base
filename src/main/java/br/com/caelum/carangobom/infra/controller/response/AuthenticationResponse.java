package br.com.caelum.carangobom.infra.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String type;

    public AuthenticationResponse(String generatedToken, String type) {
        this.token = generatedToken;
        this.type = type;
    }
}
