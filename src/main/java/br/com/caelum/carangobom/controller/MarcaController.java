package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.MarcaDto;
import br.com.caelum.carangobom.controller.form.MarcaForm;
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
    public List<MarcaDto> listar() {
        List<Marca> marcas = marcaRepository.findAllByOrderByNome();
        return MarcaDto.toList(marcas);
    }

    @GetMapping("{id}")
    public ResponseEntity<Marca> listarPorId(@PathVariable Long id) {
        Optional<Marca> marca = marcaRepository.findById(id);
        if (marca.isPresent()) {
            return ResponseEntity.ok(marca.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MarcaDto> cadastrar(@Valid @RequestBody MarcaForm marcaForm, UriComponentsBuilder uriBuilder){
        Marca marca = marcaForm.converter();
        marca = marcaRepository.save(marca);
        URI urlUri = uriBuilder.path("/marcas/{id}").buildAndExpand(marca.getId()).toUri();
        return ResponseEntity.created(urlUri).body( new MarcaDto(marca));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<MarcaDto> alterar(@PathVariable Long id, @Valid @RequestBody MarcaForm marcaForm) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
        	Marca marca = marcaForm.atualizar(id, marcaRepository);
            return ResponseEntity.ok(new MarcaDto(marca));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<MarcaDto> deletar(@PathVariable Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            marcaRepository.delete(marca);
            return ResponseEntity.ok(new MarcaDto(marca));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ListaDeErrosOutputDto validacao(MethodArgumentNotValidException excecoes) {
        List<ErroDeParametroOutputDto> listaDeErrosDto = new ArrayList<>();
        excecoes.getBindingResult().getFieldErrors().forEach(excecao -> {
            ErroDeParametroOutputDto erroDeParametro = new ErroDeParametroOutputDto();
            erroDeParametro.setParametro(excecao.getField());
            erroDeParametro.setMensagem(excecao.getDefaultMessage());
            listaDeErrosDto.add(erroDeParametro);
        });
        ListaDeErrosOutputDto listaDeErros = new ListaDeErrosOutputDto();
        listaDeErros.setErros(listaDeErrosDto);
        return listaDeErros;
    }
}