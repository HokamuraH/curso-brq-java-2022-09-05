package com.brq.ms01.services;

import com.brq.ms01.dtos.UsuarioDTO;
import com.brq.ms01.exceptions.DataCreateException;
import com.brq.ms01.models.UsuarioModel;
import com.brq.ms01.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/*
 * @SpringBootTest: fornece um jeito de iniciar o Spring Boot
 * para utilizar os testes unitários
 * */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioServiceTests {

    // primeiro temos que instanciar a classe de desejo do teste
    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void getAllUsuariosTest(){
        // o primeiro passo é simular (mockar) os objetos que preciso
        List<UsuarioModel> listMock = new ArrayList<>();

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(1);
        usuarioModel.setNome("Teste");
        usuarioModel.setTelefone("Meu telefone");

        listMock.add(usuarioModel);

        // quando o findAll da camada repository for acionado, retorno a lista acima
        when ( usuarioRepository.findAll() )
                .thenReturn( listMock );

        // executar o método de desejo de teste
        List<UsuarioDTO> resultadoAtual = usuarioService.getAllUsuarios();

        //List<UsuarioDTO> resultadoEsperado = new ArrayList<>();

        assertThat(resultadoAtual.get(0).getNome() )
                .isEqualTo("Teste");
        assertThat(resultadoAtual.get(0).getTelefone())
                .isEqualTo("Meu telefone");
        assertThat(resultadoAtual.get(0).getId())
                .isEqualTo(1);

    }
    @Test
    void getAllUsuarios2Test(){

        // o primeiro passo é simular (mockar) os objetos que preciso
        List<UsuarioModel> listMock = new ArrayList<>();

        String nome = "Teste";
        int id = 1;

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(id);
        usuarioModel.setNome(nome);
        usuarioModel.setTelefone("Meu telefone");

        listMock.add(usuarioModel);

        // quando o findAll da camada repository for acionado, retorno a lista acima
        when ( usuarioRepository.findAll() )
                .thenReturn( listMock );

        // executar o método de desejo de teste
        List<UsuarioDTO> resultadoAtual = usuarioService.getAllUsuarios2();

        assertThat(resultadoAtual.get(0).getNome() )
                .isEqualTo(nome + "JAVA");
        assertThat(resultadoAtual.get(0).getTelefone())
                .isEqualTo(usuarioModel.getTelefone());
        assertThat(resultadoAtual.get(0).getId())
                .isEqualTo(id * 2);

    }

    @Test
    void createWhenSuccess(){

        String email = "email";
        String nome = "nome";

        // usuário para mockar a repository
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(email);
        dto.setNome(nome);

        UsuarioModel model = dto.toModel();
        model.setId(1);

        when(usuarioRepository.save( dto.toModel() ))
                .thenReturn(model);

        // chamar o método a ser testado

        UsuarioDTO salvoDTO = usuarioService.create(dto);

        //verificar se está correto
        assertThat(salvoDTO.getNome()).isEqualTo(nome);
        assertThat(salvoDTO.getEmail()).isEqualTo(email);
        assertThat(salvoDTO.getId()).isGreaterThan(0);

    }

    /*Quando entrar dentro do catch*/
    @Test
    void createWhenFail(){
        // mockar o uso do save
        when(usuarioRepository.save( null ))
                .thenThrow( new DataCreateException("Uma mensagem") );

        // testar o método em questão
        assertThrows( DataCreateException.class,
                () -> usuarioService.create(null)  );
    }

    @Test
    void updateWhenSucess(){

        int id = 1;

        UsuarioModel usuarioModelOriginal = new UsuarioModel();
        usuarioModelOriginal.setNome("nome");
        usuarioModelOriginal.setEmail("email");
        usuarioModelOriginal.setTelefone("telefone");

        Optional<UsuarioModel> optional = Optional.of(usuarioModelOriginal);

        UsuarioModel usuarioModelAlterado = new UsuarioModel();
        usuarioModelAlterado.setNome("nome-alterado");
        usuarioModelAlterado.setEmail("email-alterado");
        usuarioModelAlterado.setTelefone("telefone-alterado");


        when( usuarioRepository.findById(id) )
                .thenReturn(optional);

        when(usuarioRepository.save( usuarioModelAlterado ))
                .thenReturn(usuarioModelAlterado);

        // testar o método em questão
        var usuarioDTO = usuarioService
                .update(id, usuarioModelAlterado.toDTO() );

        // verificando se o teste deu certo
        assertThat( usuarioDTO.getNome() )
                .isEqualTo( usuarioModelAlterado.getNome() );
        assertThat( usuarioDTO.getEmail() )
                .isEqualTo( usuarioModelAlterado.getEmail() );

    }

    @Test
    void updateWhenFail(){

        int id = 1;
        // vamos criar um Optional vazio para retornar no findByID

        Optional<UsuarioModel> optional = Optional.empty();

        UsuarioDTO body = new UsuarioDTO();
        body.setNome("nome");
        body.setEmail("email");
        body.setTelefone("telefone");

        /* quando o findById for chamado, retornaremos
         * um optional vazio*/
        when( usuarioRepository.findById(id) )
                .thenReturn(optional);

        // chamar método de teste
        // deve retornar uma exceção
        assertThrows( RuntimeException.class ,
                () -> usuarioService.update(id, body) );
    }

    @Test
    void deleteWhenSuccessTest(){

        int id = 1;

        UsuarioModel usuarioModelOriginal = new UsuarioModel();
        usuarioModelOriginal.setNome("nome");
        usuarioModelOriginal.setEmail("email");
        usuarioModelOriginal.setTelefone("telefone");

        Optional<UsuarioModel> optional = Optional.of(usuarioModelOriginal);

        // mockar
        when(usuarioRepository.findById(id))
                .thenReturn(optional);

        // chamar o método a ser testado
        String response = usuarioService.delete(id);

        // verificar se o resultado é o esperado
        assertThat(response).isEqualTo("Usuário delatado com sucesso!");

        // verificar se o método deleteById foi executado 1 única vez
        // esta verificação somente pode ser feita em objetos mockados (@MockBean)
        verify( usuarioRepository, times(1) )
                .deleteById(id);
    }

    @Test
    void deleteWhenFailTest(){

        int id = 1;

        Optional<UsuarioModel> optional = Optional.empty();

        when(usuarioRepository.findById(id))
                .thenReturn(optional);

        assertThrows( RuntimeException.class ,
                () -> usuarioService.delete(id) );
    }

    @Test
    void getOneWhenSuccessTest(){

        int id = 1;

        UsuarioModel usuarioModelOriginal = new UsuarioModel();
        usuarioModelOriginal.setNome("nome");
        usuarioModelOriginal.setEmail("email");
        usuarioModelOriginal.setTelefone("telefone");

        Optional<UsuarioModel> optional = Optional.of(usuarioModelOriginal);

        when(usuarioRepository.findById(id))
                .thenReturn(optional);

        // chamar o método a ser testado
        UsuarioDTO usuarioDTO = usuarioService.getOne(id);

        //verificar se o método deu certo

        assertThat( usuarioDTO.getEmail() )
                .isEqualTo( usuarioModelOriginal.getEmail());
        assertThat( usuarioDTO.getNome() )
                .isEqualTo( usuarioModelOriginal.getNome());
        assertThat( usuarioDTO.getTelefone() )
                .isEqualTo( usuarioModelOriginal.getTelefone());
    }

    @Test
    void getOneWhenFailTest(){

        int id = 1;

        Optional<UsuarioModel> optional = Optional.empty();

        when(usuarioRepository.findById(id))
                .thenReturn(optional);

        // verificar se vai estourar exceção
        assertThrows( RuntimeException.class ,
                () -> usuarioService.getOne(id) );
    }
    @Test
    void fetchUsuariosByNomeTest(){

        String nomeBusca = "aaa";

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("email");
        usuario.setTelefone("telefone");
        usuario.setNome("nome");

//        List<UsuarioModel> list = new ArrayList<>();
//        list.add(usuarioModel);
//        list.add(usuarioModel);

        List<UsuarioModel> listUsuariosMockados = Arrays.asList( usuario );

        // mockando a camada repository

        when(usuarioRepository.fetchByNomeLikeNativeQuery(nomeBusca))
                .thenReturn(listUsuariosMockados);

        // chamar o método a ser testado
        List<UsuarioDTO> dtos = usuarioService.fetchUsuariosByNome(nomeBusca);

        //verificar se o retorno é o esperado

        assertThat( dtos.get(0).getTelefone() )
                .isEqualTo(listUsuariosMockados.get(0).getTelefone());

        assertThat( dtos.get(0).getEmail() )
                .isEqualTo(listUsuariosMockados.get(0).getEmail());

        assertThat( dtos.get(0).getNome() )
                .isEqualTo(listUsuariosMockados.get(0).getNome());

        assertThat( dtos.isEmpty() ).isEqualTo(false);
    }
    @Test
    void fetchUsuariosByNomeAndEmailTest(){

        String nomeBusca = "nome-busca";
        String emailBusca = "email-busca";

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("email");
        usuario.setTelefone("telefone");
        usuario.setNome("nome");

        List<UsuarioModel> listUsuariosMockados = Arrays.asList( usuario );

        when(usuarioRepository.findByNomeContainsAndEmailContains(nomeBusca, emailBusca))
                .thenReturn(listUsuariosMockados);

        // chamar o método a ser testado
        List<UsuarioDTO> dtos = usuarioService.fetchUsuariosByNomeAndEmail(nomeBusca, emailBusca);

        // verificar se o método está correto
        assertThat( dtos.get(0).getTelefone() )
                .isEqualTo(listUsuariosMockados.get(0).getTelefone());

        assertThat( dtos.get(0).getEmail() )
                .isEqualTo(listUsuariosMockados.get(0).getEmail());

        assertThat( dtos.get(0).getNome() )
                .isEqualTo(listUsuariosMockados.get(0).getNome());

        assertThat( dtos.isEmpty() ).isEqualTo(false);
    }
}
