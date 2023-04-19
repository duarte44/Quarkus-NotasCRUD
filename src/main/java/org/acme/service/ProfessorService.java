package org.acme.service;

import org.acme.dto.AlunoDTO;
import org.acme.dto.ProfessorDTO;
import org.acme.entity.Aluno;
import org.acme.entity.Professor;
import org.acme.repositories.AlunoRepository;
import org.acme.repositories.ProfessorRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.*;


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


    public Professor findById(Long id){
        var respo = professorRepository.findById(id);
        return respo;
    }


    public Response delete(Long id){
        var respo = professorRepository.deleteById(id);
        return Response.ok(respo).build();

   }
    public Response update(Long id, ProfessorDTO newDTO){
        Professor obj = professorRepository.findById(id);
        updateData(newDTO, obj);
        return Response.noContent().build();

    }

    public static Professor fromDTO(ProfessorDTO objDto){
        return new Professor(objDto.getId(), objDto.getNome(), objDto.getMateria());
    }

    private void updateData(ProfessorDTO newObj, Professor obj){
        obj.setNome(newObj.getNome());
        obj.setMateria(newObj.getMateria());
    }

    public Response alteraAluno(Professor professor, Aluno aluno){
        aluno.setProfessor(professor);

        return Response.noContent().build();

    }


}
