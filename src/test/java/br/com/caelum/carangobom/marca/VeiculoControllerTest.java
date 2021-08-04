package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.controller.dto.VeiculoDto;
import br.com.caelum.carangobom.controller.form.VeiculoForm;
import br.com.caelum.carangobom.modelo.Marca;
import br.com.caelum.carangobom.modelo.Veiculo;
import br.com.caelum.carangobom.repository.VeiculoRepository;
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
import br.com.caelum.carangobom.controller.VeiculoController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class VeiculoControllerTest {

    private VeiculoController veiculoController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private VeiculoRepository veiculoRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
        veiculoController = new VeiculoController(veiculoRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void deveRetornarListaDeVeiculos() {
        Marca marca = new Marca(1L, "FORD");
        List<Veiculo> veiculos = List.of(
                new Veiculo(1L, "FUSQUINHA AZUL", new Date(), new BigDecimal(2005), marca),
                new Veiculo(1L, "GOL BOLINHA", new Date(), new BigDecimal(2300), marca),
                new Veiculo(1L, "UNO COM ESCADA", new Date(), new BigDecimal(6700), marca),
                new Veiculo(1L, "CORSINHA", new Date(), new BigDecimal(4400), marca)
        );

        Page<Veiculo> veiculoPaged = new PageImpl(veiculos);

        Pageable paginacao = PageRequest.of(0, 3);

        when(veiculoRepository.findAll(paginacao)).thenReturn(veiculoPaged);

        Page<VeiculoDto> resultado = veiculoController.listar(paginacao);

        assertEquals(4, resultado.getTotalElements());
    }

    @Test
    void deveRetornarVeiculoPeloId() {
        Marca marca = new Marca(1L, "Audi");
        Veiculo veiculo = new Veiculo(1L, "Gol", new Date(), new BigDecimal(15000), marca);

        when(veiculoRepository.findById(1L))
                .thenReturn(Optional.of(veiculo));

        ResponseEntity<Veiculo> resposta = veiculoController.listarPorId(1L);
        assertEquals(veiculo, resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveRetornarNotFoundQuandoRecuperarVeiucloComIdInexistente() {
        when(veiculoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Veiculo> resposta = veiculoController.listarPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveResponderCreatedLocationQuandoCadastrarVeiculo() {
        Marca marca = new Marca(1L, "FIAT");

        VeiculoForm veiculoForm = new VeiculoForm();
        veiculoForm.setModelo("UNO MIL");
        veiculoForm.setAno(new Date());
        veiculoForm.setValor(new BigDecimal("3455.00"));
        veiculoForm.setMarca(marca);

        when(veiculoRepository.save(any(Veiculo.class))).then(veiculo -> {
           Veiculo veiculoSalva = veiculo.getArgument(0, Veiculo.class);
           veiculoSalva.setId(1L);
           return veiculoSalva;
        });

        ResponseEntity<VeiculoDto> resposta = veiculoController.cadastrar(veiculoForm, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/veiculos/1", Objects.requireNonNull(resposta.getHeaders().getLocation()).toString());
    }

    @Test
    void deveAlterarNomeQuandoVeiculoExistir() {
        Marca marca = new Marca(1L, "Audi");
        Veiculo veiculo = new Veiculo(1L, "Gol", new Date(), new BigDecimal(15000), marca);

        when(veiculoRepository.findById(1L))
                .thenReturn(Optional.of(veiculo));

        doReturn(veiculo).when(veiculoRepository).getOne(1L);

        VeiculoForm veiculoAlteradoForm = new VeiculoForm();
        veiculoAlteradoForm.setModelo("Nova Audi");

        ResponseEntity<VeiculoDto> resposta = veiculoController.atualizar(1L, veiculoAlteradoForm);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        VeiculoDto veiculoAlterado = resposta.getBody();
        assertEquals("Nova Audi", veiculoAlterado.getModelo());
    }

    @Test
    void naoDeveAlterarVeiculoInexistente() {
        when(veiculoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        VeiculoForm audiAlteradoForm = new VeiculoForm();
        audiAlteradoForm.setModelo("Ferrari");

        ResponseEntity<VeiculoDto> resposta = veiculoController.atualizar(1L, audiAlteradoForm);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarVeiculoExistente() {
        Marca marca = new Marca(1L, "Audi");
        Veiculo veiculo = new Veiculo(1L, "Gol", new Date(), new BigDecimal(15000), marca);

        when(veiculoRepository.findById(1L))
                .thenReturn(Optional.of(veiculo));

        ResponseEntity<VeiculoDto> resposta = veiculoController.deletar(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(veiculoRepository).delete(veiculo);
    }

    @Test
    void naoDeveDeletarVeiculoInexistente() {
        when(veiculoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<VeiculoDto> resposta = veiculoController.deletar(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

        verify(veiculoRepository, never()).delete(any());
    }
}
