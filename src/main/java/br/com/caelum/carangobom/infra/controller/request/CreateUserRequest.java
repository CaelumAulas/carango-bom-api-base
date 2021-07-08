package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.infra.jpa.entity.UserJpa;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequest {

    @NotNull @NotBlank
    private String username;
    @NotNull @NotBlank
    private String password;

    public User toUser() {
        UserJpa user = new UserJpa();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
