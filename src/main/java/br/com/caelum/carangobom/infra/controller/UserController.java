package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.service.UserService;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetDetailedUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<GetUserResponse> getUsers() {
        return userService.findAll().stream().map(GetUserResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDetailedUserResponse> getDetailedUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new GetDetailedUserResponse(userService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest,
                                                         UriComponentsBuilder uriComponentsBuilder) {

        CreateUserResponse response = new CreateUserResponse(userService.create(createUserRequest.toUser()));
        URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
