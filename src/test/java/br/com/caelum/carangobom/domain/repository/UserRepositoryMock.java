package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMock implements UserRepository {

    List<User> users = new ArrayList<>();

    @Override
    public void delete(Long id) throws NotFoundException {
        if(!this.users.stream().anyMatch(user -> user.getId().equals(id))) {
            throw new NotFoundException("Resource not found");
        }
        this.users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public User save(@Valid CreateUserRequest user) {
        User user_ = user.toUser();
        user_.setId(generateId());
        this.users.add(user_);

        return user_;
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if(!optionalUser.isPresent()) {
            throw new NotFoundException("Resource not found");
        }

        return optionalUser.get();
    }

    private Long generateId() {
        return this.users.stream().max(Comparator.comparingLong(User::getId)).map(User::getId).orElseGet(() -> 0L);
    }
}
