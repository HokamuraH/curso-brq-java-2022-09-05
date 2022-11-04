package com.brq.ms03.services;

import com.brq.ms03.dtos.ProfessorDTO;
import com.brq.ms03.exceptions.DataCreateException;
import com.brq.ms03.models.ProfessorModel;
import com.brq.ms03.repositories.ProfessorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfessorServiceTests {
    @Autowired
    private ProfessorService professorService;
    @MockBean
    private ProfessorRepository professorRepository;
    @Test
    void getAllUsuariosTest(){
        List<ProfessorModel> listMock = new ArrayList<>();

        ProfessorModel professorModel = new ProfessorModel();
        professorModel.setId(1);
        professorModel.setNome("Teste");
        professorModel.setTelefone("Meu telefone");
        professorModel.setSalario(10.00);
        professorModel.setCpf("999.999.999-99");

        listMock.add(professorModel);

        when ( professorRepository.findAll() )
                .thenReturn( listMock );

        List<ProfessorDTO> resultadoAtual = professorService.getAllProfessores();

        assertThat(resultadoAtual.get(0).getNome() )
                .isEqualTo("Teste");
        assertThat(resultadoAtual.get(0).getTelefone())
                .isEqualTo("Meu telefone");
        assertThat(resultadoAtual.get(0).getId())
                .isEqualTo(1);
        assertThat(resultadoAtual.get(0).getCpf()).isEqualTo("999.999.999-99");
        assertThat(resultadoAtual.get(0).getSalario()).isEqualTo(10.00);

    }
    @Test
    void createWhenSuccess(){

        String nome = "nome";
        Double salario = 10.00;
        String cpf = "999.999.999-99";
        String telefone = "(11) 99999-9999";

        ProfessorDTO dto = new ProfessorDTO();

        dto.setNome(nome);
        dto.setTelefone(telefone);
        dto.setSalario(salario);
        dto.setCpf(cpf);

        ProfessorModel model = dto.toModel();
        model.setId(1);

        when(professorRepository.save( dto.toModel() ))
                .thenReturn(model);

        ProfessorDTO salvoDTO = professorService.create(dto);

        //verificar se está correto
        assertThat(salvoDTO.getNome()).isEqualTo(nome);
        assertThat(salvoDTO.getSalario()).isEqualTo(salario);
        assertThat(salvoDTO.getCpf()).isEqualTo(cpf);
        assertThat(salvoDTO.getTelefone()).isEqualTo(telefone);
        assertThat(salvoDTO.getId()).isGreaterThan(0);

    }

    @Test
    void createWhenFail(){
        when(professorRepository.save( null ))
                .thenThrow( new DataCreateException("Uma mensagem") );

        assertThrows( DataCreateException.class,
                () -> professorService.create(null)  );
    }
    @Test
    void updateWhenSucess(){

        int id = 1;

        ProfessorModel professorModelOriginal = new ProfessorModel();
        professorModelOriginal.setNome("nome");
        professorModelOriginal.setCpf("cpf");
        professorModelOriginal.setSalario(1.00);
        professorModelOriginal.setTelefone("(11) 99999-1111");

        Optional<ProfessorModel> optional = Optional.of(professorModelOriginal);

        ProfessorModel professorModelAlterado = new ProfessorModel();
        professorModelAlterado.setNome("nome-alterado");
        professorModelAlterado.setCpf("cpf-alterado");
        professorModelAlterado.setSalario(2.00);
        professorModelAlterado.setTelefone("(11) 99999-2222");

        when( professorRepository.findById(id) )
                .thenReturn(optional);

        when(professorRepository.save( professorModelAlterado ))
                .thenReturn(professorModelAlterado);

        var professorDTO = professorService.update(id,professorModelAlterado.toDTO());

        assertThat( professorDTO.getNome() )
                .isEqualTo( professorModelAlterado.getNome() );
        assertThat( professorDTO.getCpf() )
                .isEqualTo( professorModelAlterado.getCpf() );
  //      assertThat(professorDTO.getSalario()).isEqualTo(professorModelAlterado.getSalario());
        assertThat(professorDTO.getTelefone()).isEqualTo(professorModelAlterado.getTelefone());

    }

    @Test
    void updateWhenFail(){

        int id = 1;
        // vamos criar um Optional vazio para retornar no findByID

        Optional<ProfessorModel> optional = Optional.empty();

        ProfessorDTO body = new ProfessorDTO();
        body.setNome("nome");
        body.setCpf("cpf");
        body.setTelefone("telefone");
        body.setSalario(20.00);

        when( professorRepository.findById(id) )
                .thenReturn(optional);

        assertThrows( RuntimeException.class ,
                () -> professorService.update(id, body) );
    }
    @Test
    void deleteWhenSuccessTest(){

        int id = 1;

        ProfessorModel professorModelOriginal = new ProfessorModel();
        professorModelOriginal.setNome("nome");
        professorModelOriginal.setSalario(10.00);
        professorModelOriginal.setTelefone("telefone");

        Optional<ProfessorModel> optional = Optional.of(professorModelOriginal);

        when(professorRepository.findById(id))
                .thenReturn(optional);

        String response = professorService.delete(id);

        assertThat(response).isEqualTo("Professor delatado com sucesso!");

        verify( professorRepository, times(1) )
                .deleteById(id);
    }

    @Test
    void deleteWhenFailTest(){

        int id = 1;

        Optional<ProfessorModel> optional = Optional.empty();

        when(professorRepository.findById(id))
                .thenReturn(optional);

        assertThrows( RuntimeException.class ,
                () -> professorService.delete(id) );
    }
    @Test
    void getOneWhenSuccessTest(){

        int id = 1;

        ProfessorModel professorModelOriginal = new ProfessorModel();
        professorModelOriginal.setNome("nome");
        professorModelOriginal.setSalario(10.00);
        professorModelOriginal.setTelefone("telefone");

        Optional<ProfessorModel> optional = Optional.of(professorModelOriginal);

        when(professorRepository.findById(id))
                .thenReturn(optional);

        ProfessorDTO professorDTO = professorService.getOne(id);


        assertThat( professorDTO.getSalario() )
                .isEqualTo( professorModelOriginal.getSalario());
        assertThat( professorDTO.getNome() )
                .isEqualTo( professorModelOriginal.getNome());
        assertThat( professorDTO.getTelefone() )
                .isEqualTo( professorModelOriginal.getTelefone());
    }

    @Test
    void getOneWhenFailTest(){

        int id = 1;

        Optional<ProfessorModel> optional = Optional.empty();

        when(professorRepository.findById(id))
                .thenReturn(optional);

        assertThrows( RuntimeException.class ,
                () -> professorService.getOne(id) );
    }
}
