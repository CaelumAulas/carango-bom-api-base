package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void delete(Long id) throws NotFoundException;

    User save(User user);

    User update(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
