package br.com.caelum.carangobom.infra.controller.response;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Data;

@Data
public class GetDetailedUserResponse {

    private Long id;
    private String username;

    public GetDetailedUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
