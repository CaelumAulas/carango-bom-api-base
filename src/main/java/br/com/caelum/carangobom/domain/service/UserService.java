package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.repository.UserRepository;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import br.com.caelum.carangobom.infra.controller.response.CreateUserResponse;
import br.com.caelum.carangobom.infra.controller.response.GetUserResponse;
import br.com.caelum.carangobom.infra.jpa.entity.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GetUserResponse> findAll() {
        return userRepository.findAll();
    }

    public CreateUserResponse create(CreateUserRequest request) {
        return userRepository.save(request);
    }

    public void delete(Long id) throws NotFoundException {
        userRepository.delete(id);
    }
}
