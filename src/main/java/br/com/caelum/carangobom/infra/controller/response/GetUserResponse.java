package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Data;

@Data
public class GetUserResponse {

    private Long id;
    private String username;

    public GetUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
