package com.brq.ms01.controllers;

import com.brq.ms01.dtos.FinanciamentoDTO;
import com.brq.ms01.models.FinanciamentoModel;
import com.brq.ms01.services.FinanciamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FinanciamentoController {

    @Autowired
    private FinanciamentoService finService;

    @GetMapping("financiamentos")
    public List<FinanciamentoDTO> getAllfinanciamentos() {
        finService.mostrarMensagemService();
        return finService.getAllFinanciamentos();
    }

    @PostMapping("financiamentos")
    public FinanciamentoDTO create(@Valid @RequestBody FinanciamentoDTO financimento) {
        return finService.create(financimento);
    }

    @PatchMapping("financiamentos/{id}")
    public FinanciamentoDTO update(@RequestBody FinanciamentoDTO financiamentoBody,
                                   @PathVariable int id) {

        return finService.update(id, financiamentoBody);
    }

    @DeleteMapping("financiamentos/{id}")
    public String delete(@PathVariable int id) {
        return finService.delete(id);
    }

    @GetMapping("financiamentos/{id}")
    public FinanciamentoDTO getOne(@PathVariable int id) {
        return finService.getOne(id);
    }
}