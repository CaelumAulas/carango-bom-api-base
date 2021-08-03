package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.controller.dto.MarcaDto;
import br.com.caelum.carangobom.controller.form.MarcaForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controller.MarcaController;
import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MarcaControllerTest {

    private MarcaController marcaController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private MarcaRepository marcaRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
        marcaController = new MarcaController(marcaRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void deveRetornarListaQuandoHouverResultados() {
        List<Marca> marcas = List.of(
            new Marca(1L, "Audi"),
            new Marca(2L, "BMW"),
            new Marca(3L, "Fiat")
        );
        Page<Marca> marcasPaged = new PageImpl(marcas);

        Pageable paginacao = PageRequest.of(0, 3);

        when(marcaRepository.findAll(paginacao))
            .thenReturn(marcasPaged);

        Page<MarcaDto> resultado = marcaController.listar(paginacao);

        assertEquals(3, resultado.getTotalElements());
    }

    @Test
    void deveRetornarMarcaPeloId() {
        Marca audi = new Marca(1L, "Audi");

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Marca> resposta = marcaController.listarPorId(1L);
        assertEquals(audi, resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Marca> resposta = marcaController.listarPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveResponderCreatedELocationQuandoCadastrarMarca() {
        MarcaForm marcaForm = new MarcaForm();
        marcaForm.setNome("Ferrari");

        when(marcaRepository.save(any(Marca.class))).then(marca -> {
            Marca marcaSalva = marca.getArgument(0, Marca.class);
            marcaSalva.setId(1L);
            return marcaSalva;
        });

        ResponseEntity<MarcaDto> resposta = marcaController.cadastrar(marcaForm, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/marcas/1", Objects.requireNonNull(resposta.getHeaders().getLocation()).toString());
    }

    @Test
    void deveAlterarNomeQuandoMarcaExistir() {
        Marca audi = new Marca(1L, "Audi");

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        doReturn(audi).when(marcaRepository).getOne(1L);


        MarcaForm audiAlteradoForm = new MarcaForm();
        audiAlteradoForm.setNome("Nova Audi");

        ResponseEntity<MarcaDto> resposta = marcaController.alterar(1L, audiAlteradoForm);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        MarcaDto marcaAlterada = resposta.getBody();
        assertEquals("Nova Audi", marcaAlterada.getNome());
    }

    @Test
    void naoDeveAlterarMarcaInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        MarcaForm audiAlteradoForm = new MarcaForm();
        audiAlteradoForm.setNome("Ferrari");

        ResponseEntity<MarcaDto> resposta = marcaController.alterar(1L, audiAlteradoForm);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarMarcaExistente() {
        Marca audi = new Marca(1L, "Audi");

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<MarcaDto> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(marcaRepository).delete(audi);
    }

    @Test
    void naoDeveDeletarMarcaInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<MarcaDto> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

        verify(marcaRepository, never()).delete(any());
    }

}