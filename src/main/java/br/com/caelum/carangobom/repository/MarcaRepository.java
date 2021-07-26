package br.com.caelum.carangobom.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.modelo.Marca;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MarcaRepository {

    private EntityManager entityManager;

    @Autowired
    public MarcaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void delete(Marca marca) {
        entityManager.remove(marca);
    }

    public Marca save(Marca marca) {
        entityManager.persist(marca);
        return marca;
    }

    public Optional<Marca> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Marca.class, id));
    }

    public List<Marca> findAllByOrderByNome() {
        return entityManager.createQuery("select m from Marca m order by m.nome", Marca.class)
                .getResultList();
    }

}
