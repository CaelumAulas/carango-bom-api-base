package br.com.caelum.carangobom.domain.entity.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UpdatePasswordForm {

    @Getter @Setter
    private String oldPassword;

    @Getter @Setter
    private String newPassword;
}
