package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.infra.jpa.entity.UserJpa;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @Getter @Setter
    @NotNull @NotBlank
    private String username;

    @Getter @Setter
    @NotNull @NotBlank
    private String password;

    public User toUser() {
        UserJpa user = new UserJpa();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
