package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;

import java.util.List;

public interface UserRepository {

    void delete(Long id) throws NotFoundException;

    CreateUserResponse save(CreateUserRequest user);

    List<GetUserResponse> findAll();

    GetUserResponse findById(Long id) throws NotFoundException;
}
