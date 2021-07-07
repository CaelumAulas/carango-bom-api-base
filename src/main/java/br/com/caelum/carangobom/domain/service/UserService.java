package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id);
    }

    public User create(CreateUserRequest request) {
        return userRepository.save(request);
    }

    public void delete(Long id) throws NotFoundException {
        userRepository.delete(id);
    }
}
