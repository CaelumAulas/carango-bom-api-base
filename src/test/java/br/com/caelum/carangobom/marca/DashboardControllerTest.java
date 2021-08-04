package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.controller.DashboardController;
import br.com.caelum.carangobom.controller.dto.DashboardDto;
import br.com.caelum.carangobom.repository.MarcaRepository;
import br.com.caelum.carangobom.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DashboardControllerTest {
    private DashboardController dashboardController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
        dashboardController = new DashboardController(veiculoRepository, marcaRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void deveRetornarNumeroDeVeiculosEMarcas() {

        when(marcaRepository.count())
                .thenReturn(5L);
        when(veiculoRepository.count())
                .thenReturn(10L);

        ResponseEntity<DashboardDto> resultado = dashboardController.listar();
        assertEquals(10L, Objects.requireNonNull(resultado.getBody()).getNumeroVeiculos());
        assertEquals(5L, resultado.getBody().getNumeroMarcas());
    }
}

