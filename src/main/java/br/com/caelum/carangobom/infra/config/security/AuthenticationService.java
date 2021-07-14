package br.com.caelum.carangobom.infra.config.security;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        if(user.isPresent())
            return user.get();

        throw new UsernameNotFoundException("The user '" + s + "' doest not exist.");
    }
}