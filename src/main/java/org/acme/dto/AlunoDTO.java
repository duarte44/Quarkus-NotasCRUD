package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.Aluno;
import org.acme.entity.Professor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoDTO {

    private Long id;

    @NotBlank(message = "Nome não pode ser nulo ")
    private String nome;

    @NotNull(message = "Nota não pode ser nula")
    private Double n1;

    @NotNull(message = "Nota não pode ser nula")
    private Double n2;

    @NotNull(message = "Nota não pode ser nula")
    private Double n3;

    private Professor professor;

}
