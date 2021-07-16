package br.com.caelum.carangobom.domain.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMock implements UserRepository {

    List<User> users = new ArrayList<>();

    @Override
    public void delete(Long id) throws NotFoundException {
        if(this.users.stream().noneMatch(user -> user.getId().equals(id))) {
            throw new NotFoundException("Resource not found");
        }
        this.users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public User save(@Valid User user) {
        user.setId(generateId());
        this.users.add(user);

        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> optionalUser = this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return optionalUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    private Long generateId() {
        return this.users.stream().max(Comparator.comparingLong(User::getId)).map(User::getId).orElseGet(() -> 0L);
    }
}
