package br.com.caelum.carangobom.controller;


import br.com.caelum.carangobom.controller.dto.MarcaDto;
import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;
import br.com.caelum.carangobom.validacao.ErroDeParametroOutputDto;
import br.com.caelum.carangobom.validacao.ListaDeErrosOutputDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private MarcaRepository marcaRepository;
    
    @Autowired
    public MarcaController(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @GetMapping
    public List<MarcaDto> lista(String nome) {
    	if(nome == null) {
			List<Marca> marcas = marcaRepository.findAllByOrderByNome();
			return MarcaDto.toList(marcas);
		}else {
			List<Marca> marcas = marcaRepository.findByNome(nome);
			return MarcaDto.toList(marcas);
		}
    }

    @GetMapping("{id}")
    @Transactional
    public ResponseEntity<Marca> id(@PathVariable Long id) {
        Optional<Marca> marca = marcaRepository.findById(id);
        if (marca.isPresent()) {
            return ResponseEntity.ok(marca.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Marca> cadastra(@Valid @RequestBody Marca marcaForm, UriComponentsBuilder uriBuilder) {
        Marca marca = marcaRepository.save(marcaForm);
        URI  urlUri = uriBuilder.path("/marcas/{id}").buildAndExpand(marcaForm.getId()).toUri();
        return ResponseEntity.created(urlUri).body(marca);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Marca> altera(@PathVariable Long id, @Valid @RequestBody Marca marcaForm) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            marca.setNome(marcaForm.getNome());
            return ResponseEntity.ok(marca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Marca> deleta(@PathVariable Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            marcaRepository.delete(marca);
            return ResponseEntity.ok(marca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ListaDeErrosOutputDto validacao(MethodArgumentNotValidException excecao) {
        List<ErroDeParametroOutputDto> l = new ArrayList<>();
        excecao.getBindingResult().getFieldErrors().forEach(e -> {
            ErroDeParametroOutputDto d = new ErroDeParametroOutputDto();
            d.setParametro(e.getField());
            d.setMensagem(e.getDefaultMessage());
            l.add(d);
        });
        ListaDeErrosOutputDto l2 = new ListaDeErrosOutputDto();
        l2.setErros(l);
        return l2;
    }
}