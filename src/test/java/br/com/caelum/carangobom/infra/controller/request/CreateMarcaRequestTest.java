package br.com.caelum.carangobom.infra.controller.request;


import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateMarcaRequestTest {

    private Validator validator;

    private boolean searchMessage(List<String> messages, String lookingFor){
        return messages
                .stream()
                .anyMatch(message->lookingFor.equals(message));
    }

    @BeforeAll
    void setupValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldValidateTheCreateMarcaRequestWithSuccess(){
        String nome = "Audi";
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(nome);
        Set<ConstraintViolation<CreateMarcaRequest>> constraintViolations = this.validator.validate(createMarcaRequest);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void shouldFailBecauseTheNameIsEmpty(){
        String nome = "";
        List<String> expectedMessages = Arrays.asList("Deve ter 2 ou mais caracteres.","Deve ser preenchido.");
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(nome);
        Set<ConstraintViolation<CreateMarcaRequest>> constraintViolations = this.validator.validate(createMarcaRequest);
        List<String> errorMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        for (String expectedMessage: expectedMessages) {
            assertTrue(searchMessage(errorMessages, expectedMessage));
        }
    }

    @Test
    void shouldFailBecahseTheNameIsTooShort(){
        String nome = "E";
        String expectedMessage = "Deve ter 2 ou mais caracteres.";
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(nome);
        Set<ConstraintViolation<CreateMarcaRequest>> constraintViolations = this.validator.validate(createMarcaRequest);
        List<String> errorMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertTrue(searchMessage(errorMessages, expectedMessage));
    }

    @Test
    void shouldReturnAMarcaJpaInstace(){
        String nome = "Audi";
        MarcaJpa marcaJpa = new CreateMarcaRequest(nome).toMarcaJpa();
        assertNotNull(marcaJpa);
        assertEquals(nome, marcaJpa.getNome());
    }

    @Test
    void shouldGetAllTheFieldsUsingTGetters(){
        String nome = "Audi";
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(nome);
        assertEquals(nome, createMarcaRequest.getNome());
    }

}
