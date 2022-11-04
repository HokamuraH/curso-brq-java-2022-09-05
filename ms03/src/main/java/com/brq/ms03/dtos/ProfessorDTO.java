package com.brq.ms03.dtos;

import com.brq.ms03.models.ProfessorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {
    private int id;
    @NotEmpty(message = "Valor em branco")
    @NotNull(message = "Preenchimento Obrigatório")
    @Length(min=3, max = 50, message = "O numero de caracteres deve ser entre 3 e 50")
    private String nome;
    private String cpf;
    private Double salario;
    @Pattern(regexp = "^\\([1-9]{2}\\) [9]{0,1}[6-9]{1}[0-9]{3}\\-[0-9]{4}$", message = "Telefone inválido")
    private String telefone;

    public ProfessorModel toModel() {
        ModelMapper mapper = new ModelMapper();
        ProfessorModel model = mapper.map(this, ProfessorModel.class);
        return model;
    }
}