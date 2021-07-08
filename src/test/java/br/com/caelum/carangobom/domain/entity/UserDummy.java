package br.com.caelum.carangobom.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDummy implements User {
    private Long id;
    private String username;
    private String password;
}
