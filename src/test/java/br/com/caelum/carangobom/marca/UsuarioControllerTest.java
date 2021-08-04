package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.controller.UsuarioController;
import br.com.caelum.carangobom.controller.dto.UsuarioDto;
import br.com.caelum.carangobom.controller.form.UsuarioForm;
import br.com.caelum.carangobom.modelo.Usuario;
import br.com.caelum.carangobom.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

class UsuarioControllerTest {

    private UsuarioController usuarioController;
    private UriComponentsBuilder uriBuilder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void configuraMock() {
        openMocks(this);
        usuarioController = new UsuarioController(usuarioRepository);
        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
    }

    @Test
    void deveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = List.of(
          new Usuario(1L, "PEDRO SONAR", "pedro@email.com", "12344"),
          new Usuario(1L, "PAULO SOUZA", "paulo@email.com", "12344"),
          new Usuario(1L, "DANIEL DA SILVA", "daniel@email.com", "12344"),
          new Usuario(1L, "JOÃO TORRESMO", "joao@email.com", "12344")
        );

        Page<Usuario> usuarioPaged = new PageImpl(usuarios);
        Pageable paginacao = PageRequest.of(0, 3);
        when(usuarioRepository.findAll(paginacao)).thenReturn(usuarioPaged);
        Page<UsuarioDto> resultado = usuarioController.listar(paginacao);
        assertEquals(4, resultado.getTotalElements());
    }

    @Test
    void deveRetornarUsuarioPeloId() {

        Usuario usuario = new Usuario(1L, "PEDRO SONAR", "pedro@email.com", "12345");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> resposta = usuarioController.listarPorId(1L);
        assertEquals(usuario, resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveRetornarNotFoundQuandoRecuperarUsuarioComIdInexistente() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Usuario> resposta = usuarioController.listarPorId(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveResponderCreatedELocationQuandoCadastrarUsuario() {
        UsuarioForm usuarioForm = new UsuarioForm();
        usuarioForm.setNome("PEDRO PAULA");

        when(usuarioRepository.save(any(Usuario.class))).then(usuario -> {
            Usuario usuarioSalvo = usuario.getArgument(0, Usuario.class);
            usuarioSalvo.setId(1L);
            return usuarioSalvo;
        });

        ResponseEntity<UsuarioDto> resposta = usuarioController.cadastrar(usuarioForm, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/usuarios/1", Objects.requireNonNull(resposta.getHeaders().getLocation()).toString());
    }

    @Test
    void deveAlterarNomeQuandoUsuarioExistir() {
        Usuario usuario = new Usuario(1L, "PEDRO SONAR", "pedro@email.com", "12344");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        doReturn(usuario).when(usuarioRepository).getOne(1L);

        UsuarioForm usuarioAlteradoForm = new UsuarioForm();
        usuarioAlteradoForm.setNome("Novo Pedro");

        ResponseEntity<UsuarioDto> resposta = usuarioController.alterar(1L, usuarioAlteradoForm);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        UsuarioDto usuarioAlterado = resposta.getBody();
        assert usuarioAlterado != null;
        assertEquals("Novo Pedro", usuarioAlterado.getNome());
    }

    @Test
    void naoDeveAlterarUsuarioInexistente() {

        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        UsuarioForm usuarioForm = new UsuarioForm();
        usuarioForm.setNome("PEDRO DA SILVA");

        ResponseEntity<UsuarioDto> resposta = usuarioController.alterar(1L, usuarioForm);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarUsuarioExistente() {
        Usuario usuario = new Usuario(1L,"PEDRO SONAR", "pedro@email.com", "12344");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioDto> resposta = usuarioController.deletar(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void naoDeveDeletarUsuarioInexistente() {
    }

}
