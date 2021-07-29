package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.VeiculoDto;
import br.com.caelum.carangobom.controller.form.VeiculoForm;
import br.com.caelum.carangobom.modelo.Veiculo;
import br.com.caelum.carangobom.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
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
    public List<VeiculoDto> listar() {
        List<Veiculo> veiculos = veiculoRepository.findAllByOrderByModelo();
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
    public ResponseEntity<VeiculoDto> cadastrar(@Valid @RequestBody VeiculoForm veiculoForm, UriComponentsBuilder uriBuilder){
        Veiculo veiculo = veiculoForm.converter();
        veiculo = veiculoRepository.save(veiculo);
        URI urlUri = uriBuilder.path("/veiculos/{id}").buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(urlUri).body( new VeiculoDto(veiculo));
    }

    @PutMapping("{id}")
    @Transactional
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
