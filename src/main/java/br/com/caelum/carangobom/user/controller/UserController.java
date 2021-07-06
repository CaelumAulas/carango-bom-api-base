package br.com.caelum.carangobom.user.controller;

import br.com.caelum.carangobom.user.dto.UserDTO;
import br.com.caelum.carangobom.user.form.UserForm;
import br.com.caelum.carangobom.user.model.User;
import br.com.caelum.carangobom.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/users")
    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder){
        User user = userForm.convert();

        userRepository.save(user);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }
}
