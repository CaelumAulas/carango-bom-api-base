package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Data;

@Data
public class CreateUserResponse {

    private Long id;
    private String username;
    private String password;

    public CreateUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
