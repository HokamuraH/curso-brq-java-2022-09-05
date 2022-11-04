package com.brq.ms03.models;

import com.brq.ms03.dtos.ProfessorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professor")
public class ProfessorModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prof")
	private int id;
	@Column(name = "nome_prof")
	private String nome;
	@Column(name = "cpf_prof")
	private String cpf;
	@Column(name = "salario_prof")
	private double salario;
	@Column(name = "telefone_prof")
	private String telefone;
	public ProfessorDTO toDTO() {
	ModelMapper mapper = new ModelMapper();
	return mapper.map (this , ProfessorDTO.class);
	}
}
