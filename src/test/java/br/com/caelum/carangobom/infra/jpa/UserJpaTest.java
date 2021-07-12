package br.com.caelum.carangobom.infra.jpa;

import br.com.caelum.carangobom.domain.entity.User;
import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import br.com.caelum.carangobom.domain.repository.UserRepository;
import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserJpaTest {

    @Autowired
    UserRepository userRepositoryJpa;

    @Test
    void testCreateSuccess() {
        setup();
        assertEquals(2, userRepositoryJpa.findAll().size());

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("standard2");
        request.setPassword("123456");

        User response = userRepositoryJpa.save(request);

        assertNotNull(response);
        assertEquals(request.getUsername(), response.getUsername());

        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("admin2");
        request2.setPassword("123456");

        User response2 = userRepositoryJpa.save(request2);

        assertNotNull(response2);
        assertEquals(request2.getUsername(), response2.getUsername());

        assertEquals(4, userRepositoryJpa.findAll().size());
    }

    @Test
    void testDeleteUserSuccess() {
        setup();

        assertDoesNotThrow(() -> {
            userRepositoryJpa.delete(userRepositoryJpa.findAll().get(0).getId());

            assertEquals(1, userRepositoryJpa.findAll().size());

            userRepositoryJpa.delete(userRepositoryJpa.findAll().get(0).getId());

            assertEquals(0, userRepositoryJpa.findAll().size());
        });
    }

    @Test
    void testDeleteUserFail() {
        assertThrows(NotFoundException.class, () -> {
            userRepositoryJpa.delete(1L);
        });
    }

    @Test
    void testGetDetailedUserSuccess() {
        setup();

        assertDoesNotThrow(() -> {
            User requestedUser = userRepositoryJpa.findAll().get(0);
            User userResponse = userRepositoryJpa.findById(
                    requestedUser.getId()
            );
            assertNotNull(userResponse);
            assertEquals(requestedUser.getId(), userResponse.getId());
            assertEquals(requestedUser.getUsername(), userResponse.getUsername());
        });

        assertDoesNotThrow(() -> {
            User requestedUser = userRepositoryJpa.findAll().get(1);
            User userResponse = userRepositoryJpa.findById(
                    requestedUser.getId()
            );
            assertNotNull(userResponse);
            assertEquals(requestedUser.getId(), userResponse.getId());
            assertEquals(requestedUser.getUsername(), userResponse.getUsername());

        });
    }

    @Test
    void testGetDetailedUserFail() {
        assertThrows(NotFoundException.class, () -> {
            userRepositoryJpa.findById(1L);
        });
    }

    private void setup() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("standard");
        request.setPassword("123456");

        userRepositoryJpa.save(request);

        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("admin");
        request2.setPassword("123456");

        userRepositoryJpa.save(request2);
    }
}
