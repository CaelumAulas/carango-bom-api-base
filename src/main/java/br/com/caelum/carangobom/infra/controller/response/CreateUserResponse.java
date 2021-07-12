package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

public class CreateUserResponse {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String username;

    public CreateUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
