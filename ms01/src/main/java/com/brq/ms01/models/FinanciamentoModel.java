package com.brq.ms01.models;

import com.brq.ms01.dtos.FinanciamentoDTO;
import com.brq.ms01.dtos.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "financiamentos")
public class FinanciamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financiamento")
    private Integer id;

    @Column(name = "numero_contrato")
    private Integer numeroContrato;

    @Column(name = "valor")
    private Double valor;
//a entidade que possui a chave estrangeira deve ter o @JoinColumn
    @ManyToOne
    @JoinColumn (name = "usuario_id")
    private UsuarioModel usuario;

    public FinanciamentoDTO toDTOF() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, FinanciamentoDTO.class);
    }
}