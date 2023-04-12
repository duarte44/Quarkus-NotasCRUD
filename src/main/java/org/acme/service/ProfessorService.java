package org.acme.service;

import org.acme.dto.AlunoDTO;
import org.acme.dto.ProfessorDTO;
import org.acme.entity.Aluno;
import org.acme.entity.Professor;
import org.acme.repositories.ProfessorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;


@ApplicationScoped
public class ProfessorService {

    @Inject
    ProfessorRepository professorRepository;

    public void insert(Professor obj){
        obj.setId(null);
        professorRepository.persist(obj);

    }

    public List<Professor> findAll(){
        return professorRepository.findAll().list();
    }

    public static Professor fromDTO(ProfessorDTO objDto){
        return new Professor(objDto.getId(), objDto.getNome(), objDto.getMateria(), objDto.getAlunos());
    }
}
