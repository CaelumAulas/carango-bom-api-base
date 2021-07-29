package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.UsuarioDto;
import br.com.caelum.carangobom.controller.form.UsuarioForm;
import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<UsuarioDto> listar() {
        List<Usuario> usuarios = usuarioRepository.findAllByOrderByNome();
        return UsuarioDto.toList(usuarios);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> listarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrar(@Valid @RequestBody UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioForm.converter();
        usuario = usuarioRepository.save(usuario);
        URI urlUri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(urlUri).body(new UsuarioDto(usuario));
    }


    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<UsuarioDto> alterar(@PathVariable Long id, @Valid @RequestBody UsuarioForm usuarioForm){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioForm.atualizar(id, usuarioRepository);
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<UsuarioDto> deletar(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuarioRepository.delete(usuario);
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
