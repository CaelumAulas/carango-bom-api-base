package br.com.caelum.carangobom.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDummy implements User {
    private Long id;
    @NotNull @NotBlank
    private String username;
    @NotNull @NotBlank
    private String password;
}
