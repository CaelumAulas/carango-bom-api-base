package br.com.caelum.carangobom.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import br.com.caelum.carangobom.domain.entity.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import br.com.caelum.carangobom.domain.entity.Marca;
import br.com.caelum.carangobom.domain.entity.MarcaDummy;
import br.com.caelum.carangobom.domain.repository.MarcaRepositoryMock;

class MarcaServiceTest {
	
	private final MarcaRepositoryMock marcaRepository = new MarcaRepositoryMock();
	
	@Test 
	void shouldListAllUsersInOrderByNome() {
		List<MarcaDummy> mockMarcasList = Arrays.asList(
				new MarcaDummy(1L, "Audi"),
				new MarcaDummy(3L, "Ferrari"),
				new MarcaDummy(2L, "Ford"),
				new MarcaDummy(4L, "Porsche")
			);
		this.marcaRepository.setMarcas(mockMarcasList);
		
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		List<Marca> marcaList = marcaService.findAllByOrderByNome();
		assertArrayEquals(mockMarcasList.toArray(),marcaList.toArray());
	}
	
	@Test
	void souldReturnASortedListWithAllUsers() {
		List<MarcaDummy> mockMarcasList = Arrays.asList(
				new MarcaDummy(1L, "Ferrari"),
				new MarcaDummy(2L, "Porsche"),
				new MarcaDummy(3L, "Audi"),
				new MarcaDummy(4L, "Ford")
			);
		this.marcaRepository.setMarcas(mockMarcasList);

		MarcaService marcaService = new MarcaService(this.marcaRepository);
		List<Marca> marcaList = marcaService.findAllByOrderByNome();
		assertArrayEquals(mockMarcasList
				.stream()
				.sorted(Comparator.comparing(MarcaDummy::getNome))
				.toArray(),
				marcaList.toArray()
		);
	}
	
	@Test
	void shouldReturnAMarcaById() {
		MarcaDummy marcaDummy = new MarcaDummy(1L, "Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Optional<Marca> marcaResult = marcaService.findById(marcaDummy.getId());
		assertTrue(marcaResult.isPresent());
		assertEquals(marcaDummy.getId(),marcaResult.get().getId());
		assertEquals(marcaDummy.getNome(),marcaResult.get().getNome());
	}
	
	@Test
	void shouldReturnFalseBecauseItDidNotFoundAMarca() {
		MarcaDummy marcaDummy = new MarcaDummy(2L, "Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Optional<Marca> marcaResult = marcaService.findById(1L);
		assertFalse(marcaResult.isPresent());
	}
	
	@Test
	void shouldCreateAMarca() {
		MarcaDummy marcaDummy = new MarcaDummy("Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Marca marcaResult = marcaService.create(marcaDummy);
		assertNotNull(marcaResult);
		assertEquals(1, marcaResult.getId());
		assertEquals(marcaDummy.getNome(), marcaResult.getNome());
	}

	@Test
	void shouldUpdateAMarca() throws NotFoundException {
		MarcaDummy marcaDummy = new MarcaDummy(1L,"Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Marca marcaResult = marcaService.update(marcaDummy, 1L);
		assertNotNull(marcaResult);
		assertEquals(1,marcaResult.getId());
		assertEquals(marcaDummy.getNome(),marcaResult.getNome());
	}

	@Test
	void shouldThrowNotFoundOnUpdate() {
		String exceptionMessage = "Marca não encontrada";
		MarcaDummy marcaDummy = new MarcaDummy(1L,"Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		NotFoundException notfoundException = assertThrows(
				NotFoundException.class,
				()-> marcaService.update(marcaDummy, 2L)
		);
		assertEquals(exceptionMessage, notfoundException.getMessage());
	}

	@Test void shouldDeleteAMarca() throws NotFoundException {
		MarcaDummy marcaDummy = new MarcaDummy(1L, "Audi");
		List<MarcaDummy> mockMarcaDummyList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcaDummyList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		marcaService.deleteById(marcaDummy.getId());
		assertEquals(0, this.marcaRepository.findAllByOrderByNome().size());
	}

	@Test void shoulThrowsNotFounOndDeleteMarca(){
		String exceptionMessage = "Marca não encontrada";
		MarcaDummy marcaDummy = new MarcaDummy(1L, "Audi");
		List<MarcaDummy> mockMarcaDummyList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcaDummyList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);

		NotFoundException notfoundException = assertThrows(
				NotFoundException.class,
				()-> marcaService.deleteById(2L)
		);
		assertEquals(exceptionMessage, notfoundException.getMessage());
	}

}