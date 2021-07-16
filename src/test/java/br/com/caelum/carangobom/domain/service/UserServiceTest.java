package br.com.caelum.carangobom.domain.service;


import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.UserDummy;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.repository.UserRepositoryMock;
import static org.junit.jupiter.api.Assertions.*;

import br.com.caelum.carangobom.infra.config.security.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class UserServiceTest {

    private final UserRepositoryMock userRepository = new UserRepositoryMock();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserService userService = new UserService(userRepository, new TokenService(), passwordEncoder);

    @Test
    void testBeanValidationFail() {
        UserDummy request = new UserDummy(1L, "", "123456");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserDummy>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUsername("usuario");
        request.setPassword("");

        violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUsername(null);
        request.setPassword("123456");

        violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUsername("usuario");
        request.setPassword(null);

        violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBeanValidationSuccess() {
        UserDummy request = new UserDummy(1L, "usuario", "123456");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserDummy>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testFindAllSuccess() {
        assertEquals(0, userService.findAll().size());

        UserDummy request = new UserDummy(1L, "standard user", "123456");

        userService.create(request);

        assertEquals(1, userService.findAll().size());

        UserDummy request2 = new UserDummy(2L, "premium user", "123456");

        userService.create(request2);

        assertEquals(2, userService.findAll().size());
    }

    @Test
    void testFindByIdSuccess() {
        String password = "123456";
        UserDummy request = new UserDummy(1L, "standard user", password);
        String encoded = passwordEncoder.encode(password);
        userService.create(request);

        assertDoesNotThrow(() -> {
            User user = userService.findById(0L);
            assertNotNull(user);
            assertEquals(0L, user.getId());
            assertEquals("standard user", user.getUsername());
            assertNotEquals(user.getPassword(), password);
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
        });
    }

    @Test
    void testFindByIdFail() {
        UserDummy request = new UserDummy(1L, "standard user", "123456");

        userService.create(request);

        assertThrows(NotFoundException.class, () -> {
            userService.findById(1L);
        });
    }

    @Test
    void testDeleteSuccess() {
        assertEquals(0, userService.findAll().size());

        UserDummy request = new UserDummy(1L, "standard user", "standard user");

        userService.create(request);

        assertEquals(1, userService.findAll().size());

        assertDoesNotThrow(() -> {
            userService.delete(0L);
            assertEquals(0, userService.findAll().size());
        });
    }

    @Test
    void testDeleteFail() {
        assertEquals(0, userService.findAll().size());

        assertThrows(NotFoundException.class, () -> userService.delete(0L));

        UserDummy request = new UserDummy(1L, "standard user", "123456");

        userService.create(request);

        assertEquals(1, userService.findAll().size());
        assertDoesNotThrow(() -> {
            userService.delete(0L);
            assertEquals(0, userService.findAll().size());
        });
        assertEquals(0, userService.findAll().size());
        assertThrows(NotFoundException.class, () -> userService.delete(0L));
    }

    @Test
    void testUpdate() {
        UserDummy request = new UserDummy(1L, "standard user", "123456");

        userService.create(request);
        assertDoesNotThrow(() -> {
            User user = userService.findById(0L);
            user.setUsername("different");

            userService.update(user);

            user = userService.findById(0L);
            assertEquals("different", user.getUsername());
        });

    }
}
