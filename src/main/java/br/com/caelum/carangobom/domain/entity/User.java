package br.com.caelum.carangobom.domain.entity;

public interface User {
    void setId(Long id);
    Long getId();
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
}
