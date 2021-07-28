package br.com.caelum.carangobom.controller;

import br.com.caelum.carangobom.controller.dto.VeiculoDto;
import br.com.caelum.carangobom.modelo.Veiculo;
import br.com.caelum.carangobom.repository.MarcaRepository;
import br.com.caelum.carangobom.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private VeiculoRepository veiculoRepository;

    @Autowired
    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }


    @GetMapping
    public List<VeiculoDto> listar() {
        List<Veiculo> veiculos = veiculoRepository.findAllByOrderByModelo();
        return VeiculoDto.toList(veiculos);
    }
}
