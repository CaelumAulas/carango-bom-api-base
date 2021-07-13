package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.infra.controller.request.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBeanValidationFail() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("admin");
        request.setPassword("");
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")));
    }

    @Test
    void testBeanValidationFail2() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("");
        request.setPassword("123456");
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("username")));
    }

    @Test
    void testBeanValidationFail3() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("");
        request.setPassword("");
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", hasItems("username", "password")));
    }
}
