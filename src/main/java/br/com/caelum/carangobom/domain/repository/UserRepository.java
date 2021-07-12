package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;

import java.util.List;

public interface UserRepository {

    void delete(Long id) throws NotFoundException;

    User save(User user);

    List<User> findAll();

    User findById(Long id) throws NotFoundException;
}
