package br.com.caelum.carangobom.infra.controller;

import static org.junit.jupiter.api.Assertions.*;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.infra.controller.request.CreateMarcaRequest;
import br.com.caelum.carangobom.infra.controller.response.MarcaResponse;
import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MarcaControllerTest {

    @Autowired
    MarcaController marcaController;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldCreateAMarca(){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        String nome = "Audi";
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(nome);
        ResponseEntity<MarcaResponse> responseEntity = marcaController.cadastra(createMarcaRequest, uriComponentsBuilder);
        assertEquals("/marcas/1", responseEntity.getHeaders().getLocation().toString());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getNome());
        assertEquals(nome, responseEntity.getBody().getNome());
    }

    @Test
    void shouldFindMarcaById(){
        String nome = "Audi";
        MarcaJpa marcaJpa = this.saveMarca(nome);
        ResponseEntity<MarcaResponse> responseEntity = marcaController.getMarcaById(marcaJpa.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getNome());
        assertEquals(marcaJpa.getId(), responseEntity.getBody().getId());
        assertEquals(marcaJpa.getNome(), responseEntity.getBody().getNome());
    }

    @Test
    void shouldNotFoundAMarcaOnFindById(){
        Long id = 100L;
        ResponseEntity<MarcaResponse> responseEntity = marcaController.getMarcaById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldUpdateAMarca(){
        String nome = "Audi";
        String newName = "Ford";
        MarcaJpa marcaJpa = saveMarca(nome);
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(newName);
        ResponseEntity<MarcaResponse> responseEntity = marcaController.altera(
                marcaJpa.getId(),
                createMarcaRequest
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getNome());
        assertEquals(newName, responseEntity.getBody().getNome());
        assertEquals(marcaJpa.getId(), responseEntity.getBody().getId());
    }

    @Test
    void shouldReturnNotFounOnUpdate(){
        Long id = 100L;
        String newName = "Ford";
        CreateMarcaRequest createMarcaRequest = new CreateMarcaRequest(newName);
        ResponseEntity<MarcaResponse> responseEntity = marcaController.altera(
                id,
                createMarcaRequest
        );
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldDeleteAMarca(){
        MarcaJpa marcaJpa = saveMarca("Audi");
        ResponseEntity responseEntity = this.marcaController.deleta(marcaJpa.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotFoundOnDeleteAMarca(){
        Long id = 1L;
        ResponseEntity responseEntity = this.marcaController.deleta(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnAllMarcasOrderedByName(){
        ArrayList<MarcaJpa> marcas = new ArrayList<>(Arrays.asList(
           saveMarca("Audi"),
           saveMarca("Ford"),
           saveMarca("Porsche"),
           saveMarca("Ferrari")
        ));
        List<MarcaResponse> marcaResponses = this.marcaController.lista();
        assertArrayEquals(
                marcas
                        .stream()
                        .map(MarcaJpa::getNome)
                        .sorted()
                        .collect(Collectors.toList()).toArray(),
                marcaResponses
                        .stream()
                        .map(MarcaResponse::getNome)
                        .collect(Collectors.toList()).toArray()
        );
    }

    private MarcaJpa saveMarca(String nome){
        MarcaJpa marcaJpa = new MarcaJpa(nome);
        this.entityManager.persist(marcaJpa);
        return marcaJpa;
    }
}
