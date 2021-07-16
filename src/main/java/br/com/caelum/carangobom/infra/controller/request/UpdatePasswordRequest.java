package br.com.caelum.carangobom.infra.controller.request;

import br.com.caelum.carangobom.domain.entity.form.UpdatePasswordForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

    @Getter @Setter
    @NotBlank
    private String oldPassword;

    @Getter @Setter
    @NotBlank
    private String newPassword;

    public UpdatePasswordForm toForm() {
        return new UpdatePasswordForm(oldPassword, newPassword);
    }
}
