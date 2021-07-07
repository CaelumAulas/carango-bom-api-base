package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.infra.jpa.entity.UserJpa;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;

    public User toUser() {
        UserJpa user = new UserJpa();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
