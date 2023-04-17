package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.Aluno;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorDTO {

    private Long id;

    @NotBlank(message = "não pode ser nulo")
    private String nome;

    @NotBlank(message = "não pode ser nulo")
    private String materia;

    private List<Aluno> alunos;

}
