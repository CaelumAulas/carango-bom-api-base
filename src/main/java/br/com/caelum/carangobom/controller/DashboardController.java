package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.DashboardDto;
import br.com.caelum.carangobom.repository.MarcaRepository;
import br.com.caelum.carangobom.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private VeiculoRepository veiculoRepository;
    private MarcaRepository marcaRepository;

    @Autowired
    public DashboardController(VeiculoRepository veiculoRepository, MarcaRepository marcaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.marcaRepository = marcaRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardDto> listar() {
        Long numeroVeiculos = veiculoRepository.count();
        Long numeroMarcas = marcaRepository.count();
        return ResponseEntity.ok(new DashboardDto(numeroVeiculos, numeroMarcas));
    }
}
