package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Professor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String materia;

    @OneToMany(mappedBy = "professor")
    private List<Aluno> alunos;


    public Professor(Long id, String nome, String materia) {
        this.id = id;
        this.nome = nome;
        this.materia = materia;
    }

    public void addAlunos(Aluno newAluno){
        alunos.add(newAluno);

    }
}
