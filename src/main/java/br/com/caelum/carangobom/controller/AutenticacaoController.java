package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.AutenticacaoDto;
import br.com.caelum.carangobom.controller.form.AutenticacaoForm;
import br.com.caelum.carangobom.service.UsuarioAutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Autowired
    public AutenticacaoController(UsuarioAutenticacaoService usuarioAutenticacaoService){
        this.usuarioAutenticacaoService = usuarioAutenticacaoService;
    }

    @PostMapping
    public ResponseEntity<AutenticacaoDto> login(@RequestBody @Valid AutenticacaoForm autenticacaoForm) {
        AutenticacaoDto autenticacaoDto = usuarioAutenticacaoService.autenticar(autenticacaoForm);
        return new ResponseEntity<AutenticacaoDto>(autenticacaoDto, HttpStatus.ACCEPTED);
    }
}
