package br.com.caelum.carangobom.domain.service;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.entity.exception.PasswordMismatchException;
import br.com.caelum.carangobom.domain.entity.form.UpdatePasswordForm;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import br.com.caelum.carangobom.infra.config.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new NotFoundException("Resource with id '" + id + "' not found"));
    }

    public User create(User request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(request);
    }

    public User update(User request) {
        return userRepository.update(request);
    }

    public void updatePassword(UpdatePasswordForm form, String authorization) throws PasswordMismatchException {
        String token = authorization.substring(7);
        Long id = tokenService.getUserId(token);

        Optional<User> optionalUser = userRepository.findById(id);

        User user = optionalUser.get();

        if(!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userRepository.update(user);
    }

    public void delete(Long id) throws NotFoundException {
        userRepository.delete(id);
    }
}
