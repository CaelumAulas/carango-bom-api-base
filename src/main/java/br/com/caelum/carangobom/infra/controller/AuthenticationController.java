package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.infra.config.security.TokenService;
import br.com.caelum.carangobom.infra.controller.request.AuthenticationRequest;
import br.com.caelum.carangobom.infra.controller.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken token = authenticationRequest.parseUsernamePasswordAuthenticationToken();
        try {
            Authentication auth = authenticationManager.authenticate(token);
            String generatedToken = tokenService.generateToken(auth);
            return ResponseEntity.ok(new AuthenticationResponse(generatedToken, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
