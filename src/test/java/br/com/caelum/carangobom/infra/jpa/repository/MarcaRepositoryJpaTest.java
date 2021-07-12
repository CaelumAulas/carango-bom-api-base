package br.com.caelum.carangobom.infra.jpa.repository;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@DataJpaTest
@ActiveProfiles("test")
public class MarcaRepositoryJpaTest {

    @Autowired
    private EntityManager em;

    MarcaRepositoryJpa createMarcaJpaRepository(){
        return new MarcaRepositoryJpa(this.em);
    }

    @Test
    void shouldCreateAMarca(){
        MarcaRepositoryJpa marcaRepositoryJpa = createMarcaJpaRepository();
        MarcaJpa marcaJpa = new MarcaJpa("Audi");
        marcaRepositoryJpa.save(marcaJpa);
        assertEquals(1L, marcaJpa.getId());
        assertEquals("Audi", marcaJpa.getNome());
    }


    @Nested
    class createMarcaBeforeTest{

        private MarcaJpa savedMarca;

        @BeforeEach()
        void createMarca(){
            MarcaJpa marcaJpa = new MarcaJpa("Audi");
            em.persist(marcaJpa);
            savedMarca = marcaJpa;
        }

        @Test
        void shouldUpdateAMarca(){
            MarcaRepositoryJpa marcaJpaRepository = createMarcaJpaRepository();
            String newName = "Ford";
            MarcaJpa marcaFound = em.find(MarcaJpa.class, savedMarca.getId());
            marcaFound.setNome(newName);
            Marca marca = marcaJpaRepository.save(marcaFound);
            assertEquals(marcaFound.getId(), marca.getId());
            assertEquals(newName, marca.getNome());

        }

        @Test
        void shouldFindAMarcaById(){
            Long id = savedMarca.getId();
            MarcaRepositoryJpa marcaRepositoryJpa = createMarcaJpaRepository();
            Optional<Marca> marca = marcaRepositoryJpa.findById(id);
            assertTrue(marca.isPresent());
            assertEquals(id, marca.get().getId());
        }

        @Test
        void shouldDeleteTheMarca(){
            MarcaRepositoryJpa marcaRepositoryJpa = createMarcaJpaRepository();
            assertDoesNotThrow(()->marcaRepositoryJpa.delete(this.savedMarca));
        }
    }

    @Test
    void shouldThrowsBecauseTheMarcaNotExistsOnFindById(){
        Long id = 10000L;
        MarcaRepositoryJpa marcaRepositoryJpa = createMarcaJpaRepository();
        Optional<Marca> marca = marcaRepositoryJpa.findById(id);
        assertFalse(marca.isPresent());
    }

    @Test
    void sholdReturnAllMarcasOrderedByName(){
        MarcaRepositoryJpa marcaRepositoryJpa = createMarcaJpaRepository();
        ArrayList<Marca> marcas = new ArrayList<>(Arrays.asList(
                new MarcaJpa("Audi"),
                new MarcaJpa("Ferrari"),
                new MarcaJpa("Ford"),
                new MarcaJpa("Porsche")
        ));
        marcas.forEach((Marca marca)->{
            this.em.persist(marca);
        });
        List<Marca> marcasOrdered = marcaRepositoryJpa.findAllByOrderByNome();
        assertArrayEquals(
                marcas.stream()
                        .sorted(Comparator.comparing((Function<Marca, String>) Marca::getNome))
                        .collect(Collectors.toList())
                        .toArray(),
                marcasOrdered.toArray()
        );
    }
}
