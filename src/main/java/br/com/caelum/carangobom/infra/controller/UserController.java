package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.entity.exception.PasswordMismatchException;
import br.com.caelum.carangobom.domain.service.UserService;
import br.com.caelum.carangobom.infra.config.security.TokenService;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.request.UpdatePasswordRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetDetailedUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;
import br.com.caelum.carangobom.infra.controller.response.UpdatePasswordResponse;
import org.aspectj.weaver.ast.Not;
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
    public UserController(UserService userService, TokenService tokenService) {
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

    @PutMapping("/updatePassword")
    @Transactional
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest request,
                                                                 @RequestHeader("Authorization") String authorization) {
        try {
            userService.updatePassword(request.toForm(), authorization);
        } catch (PasswordMismatchException e) {
            return ResponseEntity.badRequest().body(new UpdatePasswordResponse("The old password doesn't match"));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new UpdatePasswordResponse("Password Successfully updated"));
    }
}
