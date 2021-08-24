package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.MarcaDTO;
import br.com.caelum.carangobom.controller.form.MarcaForm;
import br.com.caelum.carangobom.controller.form.MarcaFormUpdate;
import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;
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
@RequestMapping("/marcas")
public class MarcaController {
    
    @Autowired
    private MarcaRepository gMarcaRepository;

    @GetMapping
    public List<MarcaDTO> listar() {
        List<Marca> lMarcas = gMarcaRepository.findAllByOrderByNome();
        return MarcaDTO.converter(lMarcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> id(@PathVariable Long id) {
        Optional<Marca> lOpcional = gMarcaRepository.findById(id);

        if (lOpcional.isPresent()) {
            Marca lMarca = lOpcional.get();

            return ResponseEntity.ok(new MarcaDTO(lMarca));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MarcaDTO> cadastrar(@RequestBody @Valid MarcaForm pForm, UriComponentsBuilder uriBuilder) {
        Marca lMarca = pForm.converter();
        gMarcaRepository.save(lMarca);

        URI lUri = uriBuilder.path("/marcas/{id}").buildAndExpand(lMarca.getId()).toUri();
        return ResponseEntity.created(lUri).body(new MarcaDTO(lMarca));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MarcaDTO> alterar(@PathVariable Long id, @Valid @RequestBody MarcaFormUpdate form) {
        Optional<Marca> lOpcional = gMarcaRepository.findById(id);

        if (lOpcional.isPresent()) {
            Marca lMarca = lOpcional.get();
            lMarca = form.Update(lMarca);

            return ResponseEntity.ok(new MarcaDTO(lMarca));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Marca> lOpcional = gMarcaRepository.findById(id);

        if (lOpcional.isPresent()) {
            gMarcaRepository.deleteById(id);
            
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}