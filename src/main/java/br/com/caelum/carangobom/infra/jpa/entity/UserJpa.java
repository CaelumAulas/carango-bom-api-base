package br.com.caelum.carangobom.infra.jpa.entity;

import br.com.caelum.carangobom.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user")
public class UserJpa implements User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    @Column(unique = true)
    private String username;
    @Getter @Setter
    private String password;
}
