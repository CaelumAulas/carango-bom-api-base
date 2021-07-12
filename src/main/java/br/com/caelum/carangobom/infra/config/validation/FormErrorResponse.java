package br.com.caelum.carangobom.infra.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FormErrorResponse {

    @Getter
    private String field;
    @Getter
    private String message;
}
