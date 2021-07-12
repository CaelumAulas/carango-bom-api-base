package br.com.caelum.carangobom.infra.jpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.repository.MarcaRepository;
import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;

@Repository
public class MarcaRepositoryJpa implements MarcaRepository {

	private EntityManager em;

	@Autowired
    public MarcaRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    public void delete(Marca marca) {
        em.remove(marca);
    }

    public Marca save(Marca marca) {
    	MarcaJpa marcaJpa = (MarcaJpa) marca;
        em.persist(marcaJpa);
        return marca;
    }

    public Optional<Marca> findById(Long id) {
        return Optional.ofNullable(em.find(MarcaJpa.class, id));
    }

    public List<Marca> findAllByOrderByNome() {
        return em.createQuery("select m from marca m order by m.nome", Marca.class)
                .getResultList();
    }
}
