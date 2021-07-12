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
				new MarcaDummy((long) 1, "Audi"),
				new MarcaDummy((long) 3, "Ferrari"),
				new MarcaDummy((long) 2, "Ford"),
				new MarcaDummy((long) 4, "Porsche")
			);
		this.marcaRepository.setMarcas(mockMarcasList);
		
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		List<Marca> marcaList = marcaService.findAllByOrderByNome();
		assertArrayEquals(mockMarcasList.toArray(),marcaList.toArray());
	}
	
	@Test
	void souldReturnASortedListWithAllUsers() {
		List<MarcaDummy> mockMarcasList = Arrays.asList(
				new MarcaDummy((long) 1, "Ferrari"),
				new MarcaDummy((long) 2, "Porsche"),
				new MarcaDummy((long) 3, "Audi"),
				new MarcaDummy((long) 4, "Ford")
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
		MarcaDummy marcaDummy = new MarcaDummy((long) 1, "Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Optional<Marca> marcaResult = marcaService.findById(marcaDummy.getId());
		assertTrue(marcaResult.isPresent());
		assertEquals(marcaResult.get().getId(), marcaDummy.getId());
		assertEquals(marcaResult.get().getNome(), marcaDummy.getNome());
	}
	
	@Test
	void shouldReturnFalseBecauseItDidNotFoundAMarca() {
		MarcaDummy marcaDummy = new MarcaDummy((long) 2, "Audi");
		List<MarcaDummy> mockMarcasList = Collections.singletonList(marcaDummy);
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Optional<Marca> marcaResult = marcaService.findById(1L);
		assertFalse(marcaResult.isPresent());
	}
	
	@Test
	void shouldCreateAMarca() {
		MarcaDummy marcaDummy = new MarcaDummy("Audi");
		ArrayList mockMarcasList = new ArrayList(Collections.singletonList(marcaDummy));
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Marca marcaResult = marcaService.create(marcaDummy);
		assertNotNull(marcaResult);
		assertEquals(marcaResult.getId(), 1);
		assertEquals(marcaResult.getNome(), marcaDummy.getNome());
	}

	@Test
	void shouldUpdateAMarca() throws NotFoundException {
		MarcaDummy marcaDummy = new MarcaDummy(1L,"Audi");
		List<MarcaDummy> mockMarcasList = new ArrayList(Arrays.asList(marcaDummy));
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		Marca marcaResult = marcaService.update(marcaDummy, 1L);
		assertNotNull(marcaResult);
		assertEquals(marcaResult.getId(), 1);
		assertEquals(marcaResult.getNome(), marcaDummy.getNome());
	}

	@Test
	void shouldThrowNotFoundOnUpdate() {
		String exceptionMessage = "Marca não encontrada";
		MarcaDummy marcaDummy = new MarcaDummy(1l,"Audi");
		ArrayList mockMarcasList = new ArrayList(Arrays.asList(marcaDummy));
		this.marcaRepository.setMarcas(mockMarcasList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		NotFoundException notfoundException = assertThrows(
				NotFoundException.class,
				()-> marcaService.update(marcaDummy, 2L)
		);
		assertEquals(notfoundException.getMessage(),exceptionMessage);
	}

	@Test void shouldDeleteAMarca() throws NotFoundException {
		MarcaDummy marcaDummy = new MarcaDummy(1L, "Audi");
		ArrayList mockMarcaDummyList = new ArrayList(Arrays.asList(marcaDummy));
		this.marcaRepository.setMarcas(mockMarcaDummyList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);
		marcaService.deleteById(marcaDummy.getId());
		assertEquals(0, this.marcaRepository.findAllByOrderByNome().size());
	}

	@Test void shoulThrowsNotFounOndDeleteMarca(){
		String exceptionMessage = "Marca não encontrada";
		MarcaDummy marcaDummy = new MarcaDummy(1L, "Audi");
		ArrayList mockMarcaDummyList = new ArrayList(Arrays.asList(marcaDummy));
		this.marcaRepository.setMarcas(mockMarcaDummyList);
		MarcaService marcaService = new MarcaService(this.marcaRepository);

		NotFoundException notfoundException = assertThrows(
				NotFoundException.class,
				()-> marcaService.deleteById(2L)
		);
		assertEquals(notfoundException.getMessage(),exceptionMessage);
	}

}