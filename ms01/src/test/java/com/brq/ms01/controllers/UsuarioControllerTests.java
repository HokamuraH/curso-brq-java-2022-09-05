package com.brq.ms01.controllers;

import com.brq.ms01.dtos.UsuarioDTO;
import com.brq.ms01.services.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioControllerTests {

    @Autowired
    private UsuarioController controller;

    @MockBean
    private UsuarioService service;

    @Test
    void getAllUsuariosTest(){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setTelefone("telefone");
        usuarioDTO.setEmail("email");
        usuarioDTO.setNome("nome");

        List<UsuarioDTO> listDTO = Arrays.asList(usuarioDTO);

        // mockar retorno do service

        when(service.getAllUsuarios())
                .thenReturn(listDTO);

        // chamar o método a ser testado
        final var response
                = controller.getAllUsuarios();

        // verificar se o retorno está correto

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat( response.getBody() )
                .isEqualTo(listDTO);

        verify(service, times(1))
                .getAllUsuarios();
    }

    @Test
    void createWhenSuccess(){

        UsuarioDTO usuarioDTO = createValidUsuarioDTO();

        // mockando a service
        when(service.create(usuarioDTO))
                .thenReturn(usuarioDTO);

        // chamando o método a ser testado
        final var response = controller.create(usuarioDTO);

        // validando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuarioDTO);
    }

//    @Test
//    void createWhenFail(){
//
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setNome("a");
//        usuarioDTO.setEmail("email");
//        usuarioDTO.setTelefone("(11)982733817");
//
//        assertThrows(MethodArgumentNotValidException.class ,
//                () -> controller.create(usuarioDTO) );
//    }

    @Test
    void updateTest(){

        int id = 1;
        UsuarioDTO usuarioDTO = createValidUsuarioDTO();

        when(service.update(id, usuarioDTO))
                .thenReturn(usuarioDTO);

        // testar método de interesse
        final var response =
                controller.update(usuarioDTO, id);

        // verificar a resposta
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(usuarioDTO);
    }
    @Test
    void deleteTestWhenSuccess(){
        int id = 1;
        when(service.delete(id)).thenReturn("texto");
        final var response = controller.delete(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("texto");
    }
    @Test
    void deleteWhenFailTest(){
        // dado que
        int id = 1;

        // quando
        when(service.delete(id))
                .thenThrow(new RuntimeException("exception"));

        // então (teste do método)
        assertThrows( RuntimeException.class,
                () -> service.delete(id));
    }
    @Test
    void getOneWhenSucess(){
        //dado que
        int id = 1;

        final var usuarioDTO
                = createValidUsuarioDTO();

        //quando
        when(service.getOne(id))
                .thenReturn(usuarioDTO);

        // então
        final var response
                = controller.getOne(id);

        // verificar o resultado
        assertThat( response.getStatusCode() )
                .isEqualTo(HttpStatus.OK);

        assertThat( response.getBody() )
                .isEqualTo( usuarioDTO );
    }

    @Test
    void getOneWhenFail(){
        //dado que
        int id = 1;
        //quando
        when(service.getOne(id)).thenThrow(new RuntimeException("ex"));
        // então
        assertThrows( RuntimeException.class ,
                ()-> controller.getOne(id) ) ;
    }
    @Test
    void fetchUsuariosByNomeTest(){

        // dado que
        var nomeBusca = "nome";

        final var usuarioDTO = createValidUsuarioDTO();
        final var listUsuarios = Arrays.asList(usuarioDTO);
//      final var listUsuarios = Arrays.asList( createValidUsuarioDTO() );

        // quando
        when(service.fetchUsuariosByNome(nomeBusca))
                .thenReturn(listUsuarios);

        // então
        final var response
                = controller.fetchUsuariosByNome(nomeBusca);

        // validar a reposta

        assertThat( response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat( response.getBody())
                .isEqualTo( response.getBody() );

    }

    @Test
    void fetchUsuariosByNomeAndEmailTest(){

        // dado que
        final var nomeBusca = "nome";
        final var emailBusca = "email";

        final var listUsuarios
                = Arrays.asList( createValidUsuarioDTO() );

        // quando
        when(service.fetchUsuariosByNomeAndEmail(nomeBusca, emailBusca))
                .thenReturn(listUsuarios);

        // então
        final var response = controller.fetchUsuariosByNomeAndEmail(nomeBusca, emailBusca);

        // verificar a resposta
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(listUsuarios);
    }
    private UsuarioDTO createValidUsuarioDTO(){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("nome");
        usuarioDTO.setEmail("email");
        usuarioDTO.setTelefone("(11) 98273-3817");

        return usuarioDTO;
    }

}