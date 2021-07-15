package br.com.caelum.carangobom.domain.entity;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails {
    void setId(Long id);
    Long getId();
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
}
