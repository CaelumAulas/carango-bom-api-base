package br.com.caelum.carangobom.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource gMessageSource;
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroForm> handle(MethodArgumentNotValidException exception){
        List<ErroForm> lErrosList = new ArrayList<>();

        List<FieldError> lFieldErros = exception.getBindingResult().getFieldErrors();

        lFieldErros.forEach(x -> {
            String message = gMessageSource.getMessage(x, LocaleContextHolder.getLocale());
            ErroForm lErro = new ErroForm(x.getField(), message);
            lErrosList.add(lErro);
        });

        return lErrosList;
    }
}
