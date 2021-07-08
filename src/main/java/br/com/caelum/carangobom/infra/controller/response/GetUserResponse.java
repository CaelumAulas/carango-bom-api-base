package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

public class GetUserResponse {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String username;

    public GetUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
