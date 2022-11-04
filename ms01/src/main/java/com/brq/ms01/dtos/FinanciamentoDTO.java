package com.brq.ms01.dtos;

import com.brq.ms01.models.FinanciamentoModel;
import com.brq.ms01.models.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanciamentoDTO {

    private Integer id;
    private Integer numeroContrato;
    private Double valor;
//    private Integer usuario;
 //   private List<FinanciamentoDTO> financiamentos;
    //   private UsuarioDTO usuarios;

    public FinanciamentoModel toModelF(){
        ModelMapper mapper = new ModelMapper();
        FinanciamentoModel model = mapper.map(this , FinanciamentoModel.class );
        return model;
    }
}
