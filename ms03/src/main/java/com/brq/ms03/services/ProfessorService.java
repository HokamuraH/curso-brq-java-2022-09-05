package com.brq.ms03.services;

import com.brq.ms03.dtos.ProfessorDTO;
import com.brq.ms03.exceptions.DataCreateException;
import com.brq.ms03.exceptions.ObjNotFoundException;
import com.brq.ms03.models.ProfessorModel;
import com.brq.ms03.repositories.ProfessorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ProfessorService {

@Autowired
    private ProfessorRepository profRepository;
    public void mostrarMensagemService(){
        System.out.println("Mensagem do serviço");
    }
    public ProfessorDTO create(ProfessorDTO professor){
        ProfessorModel professorSaved = null;
        try {
            professorSaved = profRepository.save(professor.toModel());
            log.info(professorSaved.toString());
            return professorSaved.toDTO();
        }
        catch (Exception exception){
        log.error("Erro ao salvar o professor: " + exception.getMessage());
        throw new DataCreateException("Erro ao salvar professor");
         }
    }
    public ProfessorDTO update(int id, ProfessorDTO professorBody) {
        ProfessorModel professor = profRepository.findById(id)
                .orElseThrow(() -> new ObjNotFoundException("Professor não localizado"));
        professor.setCpf(professorBody.getCpf());
        professor.setSalario(professorBody.getSalario());
        professor.setNome(professorBody.getNome());
        professor.setTelefone(professorBody.getTelefone());
        return profRepository.save(professor).toDTO();
    }
    public String delete(int id){
        profRepository.findById(id)
                .orElseThrow( () -> new ObjNotFoundException("Professor não localizado") );
        profRepository.deleteById(id);
        return "Professor delatado com sucesso!";
    }
    public ProfessorDTO getOne(int id) {
        ProfessorModel professor = profRepository.findById(id)
                .orElseThrow(() -> new ObjNotFoundException("Professor não localizado"));
        return professor.toDTO();
    }
    public List<ProfessorDTO> getAllProfessores(){
        List<ProfessorModel> list = profRepository.findAll();
        List<ProfessorDTO> listDTO = new ArrayList<>();
        for (ProfessorModel balde : list) {
            listDTO.add(balde.toDTO());
        }
        return listDTO;
    }
}