package br.com.caelum.carangobom.marca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controller.MarcaController;
import br.com.caelum.carangobom.controller.dto.MarcaDTO;
import br.com.caelum.carangobom.controller.form.MarcaForm;
import br.com.caelum.carangobom.controller.form.MarcaFormUpdate;
import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.repository.MarcaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MarcaControllerTest {

 /*    private MarcaController marcaController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private MarcaRepository marcaRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);

        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void deveRetornarListaQuandoHouverResultados() {
        List<Marca> marcas = List.of(
            new Marca(1L, "Audi"),
            new Marca(2L, "BMW"),
            new Marca(3L, "Fiat")
        );
        List<MarcaDTO> lMarcasDto = MarcaDTO.converter(marcas);

        when(marcaRepository.findAllByOrderByNome())
            .thenReturn(marcas);

        List<MarcaDTO> resultado = marcaController.listar();
        assertEquals(lMarcasDto, resultado);
    }

    @Test
    void deveRetornarMarcaPeloId() {
        Marca audi = new Marca(1L, "Audi");

        MarcaDTO lAudiDto = new MarcaDTO(audi);

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<MarcaDTO> resposta = marcaController.id(1L);
        assertEquals(lAudiDto, resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<MarcaDTO> resposta = marcaController.id(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveResponderCreatedELocationQuandoCadastrarMarca() {
        Marca nova = new Marca("Ferrari");

        when(marcaRepository.save(nova))
            .then(invocation -> {
                Marca marcaSalva = invocation.getArgument(0, Marca.class);
                marcaSalva.setId(1L);

                return marcaSalva;
            });

        MarcaForm lMarcaForm = new MarcaForm(nova);

        ResponseEntity<MarcaDTO> resposta = marcaController.cadastrar(lMarcaForm, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/marcas/1", resposta.getHeaders().getLocation().toString());
    }

    @Test
    void deveAlterarNomeQuandoMarcaExistir() {
        Marca audi = new Marca(1L, "Audi");

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        MarcaFormUpdate lMarcaFormUpdate = new MarcaFormUpdate(audi);

        ResponseEntity<MarcaDTO> resposta = marcaController.alterar(1L, lMarcaFormUpdate);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        MarcaDTO marcaAlterada = resposta.getBody();
        assertEquals("NOVA Audi", marcaAlterada.getNome());
    }

    @Test
    void naoDeveAlterarMarcaInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        MarcaFormUpdate lMarcaFormUpdate = new MarcaFormUpdate(new Marca(1L, "NOVA Audi"));

        ResponseEntity<MarcaDTO> resposta = marcaController.alterar(1L, lMarcaFormUpdate);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarMarcaExistente() {
        Marca audi = new Marca(1l, "Audi");

        when(marcaRepository.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<?> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(marcaRepository).delete(audi);
    }

    @Test
    void naoDeveDeletarMarcaInexistente() {
        when(marcaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> resposta = marcaController.deletar(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

        verify(marcaRepository, never()).delete(any());
    } */

}