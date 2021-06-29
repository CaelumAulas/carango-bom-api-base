package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.exception.ConflictException;
import br.com.caelum.carangobom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe reponsável pela lógica de negócios de marcas
 */
@Service
public class MarcaService {

    private MarcaRepository marcaRepository;

    private MarcaDtoMapper marcaDtoMapper;

    /**
     * Construtor de marca service
     *
     * @param marcaRepository o repositório de marcas
     * @param marcaDtoMapper o conversor de objeto de transferência para entidade
     */
    @Autowired
    public MarcaService(MarcaRepository marcaRepository, MarcaDtoMapper marcaDtoMapper) {
        this.marcaRepository = marcaRepository;
        this.marcaDtoMapper = marcaDtoMapper;
    }

    @Transactional
    public List<MarcaDto> listarMarcas() {
        return marcaRepository.findAllByOrderByNome().stream().map(marcaDtoMapper::map).collect(Collectors.toList());
    }

    @Transactional
    public MarcaDto obterMarcaPorId(Long id) {
        Optional<Marca> marca = marcaRepository.findById(id);
        return marcaDtoMapper.map(marca.orElseThrow(() -> new NotFoundException("Marca não encontrada")));
    }

    @Transactional
    public MarcaDto cadastrarMarca(MarcaDto marcaDto){
        validarMarcaExistente(marcaDto.getNome());

        var novaMarca = marcaDtoMapper.map(marcaDto);
        return marcaDtoMapper.map(marcaRepository.save(novaMarca));
    }

    @Transactional
    public MarcaDto alterarMarca(Long id, MarcaDto marcaDto) {
        validarMarcaExistente(marcaDto.getNome());

        var marcaEncontrada = obterMarcaPorId(id);
        marcaEncontrada.setNome(marcaDto.getNome());
        return marcaEncontrada;
    }

    @Transactional
    public MarcaDto deletarMarca(Long id) {
        var marcaEncontrada = obterMarcaPorId(id);
        marcaRepository.deleteById(marcaEncontrada.getId());
        return marcaEncontrada;
    }

    private void validarMarcaExistente(String nomeMarca) {
        Optional<Marca> marcaEncontrada = marcaRepository.findByNome(nomeMarca);
        marcaEncontrada.ifPresent((m) ->{ throw new ConflictException("Marca " + m.getNome()+ " já existente");});
    }
}