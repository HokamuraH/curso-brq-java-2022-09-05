package com.brq.ms03.controllers;

import com.brq.ms03.dtos.ProfessorDTO;
import com.brq.ms03.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProfessorController {
    @Autowired
    private ProfessorService profService;

    @PostMapping("professores")
    public ProfessorDTO create(@Valid @RequestBody ProfessorDTO professor){
        return profService.create(professor);
    }
    @PatchMapping("professores/{id}")
    public ProfessorDTO update(@RequestBody ProfessorDTO professorBody,
                             @PathVariable int id ){

        return profService.update(id,professorBody);
    }

    @DeleteMapping("professores/{id}")
    public String delete(@PathVariable int id){

        return profService.delete(id);
    }

    @GetMapping("professores/{id}")
    public ProfessorDTO getOne(@PathVariable int id) {
        return profService.getOne(id);
    }
    @GetMapping("professores")
    public List<ProfessorDTO> getAllProfessores(){
        profService.mostrarMensagemService();
        return profService.getAllProfessores();
    }
    }
