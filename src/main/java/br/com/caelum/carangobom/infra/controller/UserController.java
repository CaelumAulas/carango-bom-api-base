package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.domain.service.UserService;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;
import br.com.caelum.carangobom.infra.jpa.entity.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<GetUserResponse> getUsers() {
        return userService.findAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest,
                                                         UriComponentsBuilder uriComponentsBuilder) {

        CreateUserResponse response = userService.create(createUserRequest);
        URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
