package com.brq.ms01.services;

import com.brq.ms01.dtos.FinanciamentoDTO;
import com.brq.ms01.dtos.UsuarioDTO;
import com.brq.ms01.models.FinanciamentoModel;
import com.brq.ms01.repositories.FinanciamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class FinanciamentoService {
    @Autowired
    private FinanciamentoRepository finRepository;
    public void mostrarMensagemService(){
        System.out.println("Mensagem do serviço");

    }
    public List<FinanciamentoDTO> getAllFinanciamentos(){
        List<FinanciamentoModel> list = finRepository.findAll();
        List<FinanciamentoDTO> listDTOF = new ArrayList<>();
        for(FinanciamentoModel x : list) {
            listDTOF.add(x.toDTOF());
        }
        return listDTOF;
    }
    public FinanciamentoDTO create(FinanciamentoDTO financiamento){

        FinanciamentoModel financiamentoSalvo = null;

        try{
            // INSERT INTO usuarios (name_user, email_user ) VALUEs()....
            financiamentoSalvo = finRepository.save( financiamento.toModelF() );
            log.info(financiamentoSalvo.toString());
        }
        catch (Exception exception){
            log.error("Erro ao salvar o financiamento: " + exception.getMessage());
        }

        return financiamentoSalvo.toDTOF();
    }
    public FinanciamentoDTO update(int id, FinanciamentoDTO financiamentoBody)  {

        FinanciamentoModel financiamento = finRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Financiamento não localizado") );
        financiamento.setNumeroContrato(financiamentoBody.getNumeroContrato());
        return finRepository.save(financiamento).toDTOF();
    }
    public String delete(int id){
        finRepository.deleteById(id);
        return "Financiamento delatado com sucesso!";
    }

    public FinanciamentoDTO getOne(int id){

        FinanciamentoModel financiamento = finRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Financiamento não localizado"));
        return financiamento.toDTOF();

    }
}
