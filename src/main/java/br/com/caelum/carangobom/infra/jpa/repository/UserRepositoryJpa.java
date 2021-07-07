package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;
import br.com.caelum.carangobom.infra.jpa.entity.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryJpa implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<User> optionalUser = locateUser(id);

        if(!optionalUser.isPresent()) {
            throw new NotFoundException();
        }

        entityManager.remove(optionalUser.get());
    }

    @Override
    public CreateUserResponse save(CreateUserRequest userRequest) {
        User user = userRequest.toUser();
        entityManager.persist(user);
        return new CreateUserResponse(user);
    }

    @Override
    public List<GetUserResponse> findAll() {
        return entityManager.createQuery("SELECT u FROM user u", User.class)
                .getResultList().stream().map(GetUserResponse::new).collect(Collectors.toList());
    }

    @Override
    public GetUserResponse findById(Long id) throws NotFoundException {
        Optional<User> optionalUser = locateUser(id);

        if(!optionalUser.isPresent()) {
            throw new NotFoundException();
        }

        return new GetUserResponse(optionalUser.get());
    }

    private Optional<User> locateUser(Long id) {
        List<User> users = entityManager.createQuery("SELECT u FROM user u WHERE id=:id", User.class)
                .setParameter("id", id)
                .getResultList();

        if(users.size() == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(users.get(0));
    }
}
