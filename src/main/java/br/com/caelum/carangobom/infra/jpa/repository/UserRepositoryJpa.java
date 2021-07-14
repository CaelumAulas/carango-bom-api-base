package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJpa implements UserRepository {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<User> optionalUser = locateUser(id);

        if(!optionalUser.isPresent()) {
            throw new NotFoundException("Resource '" + id + "' not found");
        }

        entityManager.remove(optionalUser.get());
    }

    @Override
    public User save(User userRequest) {
        userRequest.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        entityManager.persist(userRequest);
        return userRequest;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM user_entity u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return locateUser(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = entityManager.createQuery("SELECT u FROM user_entity u WHERE username=:username", User.class)
                .setParameter("username", username)
                .getResultList();

        if(users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    private Optional<User> locateUser(Long id) {
        List<User> users = entityManager.createQuery("SELECT u FROM user_entity u WHERE id=:id", User.class)
                .setParameter("id", id)
                .getResultList();

        if(users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }
}
