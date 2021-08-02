package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.VeiculoDto;
import br.com.caelum.carangobom.controller.form.VeiculoForm;
import br.com.caelum.carangobom.modelo.Veiculo;
import br.com.caelum.carangobom.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private VeiculoRepository veiculoRepository;

    @Autowired
    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @GetMapping
    @Cacheable(value = "listaDeVeiculos")
    public Page<VeiculoDto> listar(@PageableDefault() Pageable paginacao) {
        Page<Veiculo> veiculos = veiculoRepository.findAll(paginacao);
        return VeiculoDto.toList(veiculos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Veiculo> listarPorId(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        if (veiculo.isPresent()) {
            return ResponseEntity.ok(veiculo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @CacheEvict(value = "listaDeVeiculos", allEntries = true)
    public ResponseEntity<VeiculoDto> cadastrar(@Valid @RequestBody VeiculoForm veiculoForm, UriComponentsBuilder uriBuilder){
        Veiculo veiculo = veiculoForm.converter();
        veiculo = veiculoRepository.save(veiculo);
        URI urlUri = uriBuilder.path("/veiculos/{id}").buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(urlUri).body( new VeiculoDto(veiculo));
    }

    @PutMapping("{id}")
    @Transactional
    @CacheEvict(value = "listaDeVeiculos", allEntries = true)
    public ResponseEntity<VeiculoDto> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoForm veiculoForm) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if(veiculoOptional.isPresent()){
            Veiculo veiculo = veiculoForm.atualizar(id, veiculoRepository);
            return ResponseEntity.ok(new VeiculoDto(veiculo));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @CacheEvict(value = "listaDeVeiculos", allEntries = true)
    public ResponseEntity<VeiculoDto> deletar(@PathVariable Long id) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if(veiculoOptional.isPresent()){
            Veiculo veiculo = veiculoOptional.get();
            veiculoRepository.delete(veiculo);
            return ResponseEntity.ok(new VeiculoDto(veiculo));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
