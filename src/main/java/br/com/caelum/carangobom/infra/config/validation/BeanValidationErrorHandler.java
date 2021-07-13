package br.com.caelum.carangobom.infra.config.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BeanValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorResponse> handle(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream().map(
                fieldError -> new FormErrorResponse(fieldError.getField(), messageSource.getMessage(fieldError, getLocale()))
        ).collect(Collectors.toList());
    }
}
