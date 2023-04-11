package org.acme.service;

import org.acme.dto.AlunoDTO;
import org.acme.entity.Aluno;
import org.acme.repositories.AlunoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;


    public List<Aluno> findAll(){
        return alunoRepository.findAll().list();
    }

    public Response findById(Long id){
        var respo = alunoRepository.findById(id);
        return Response.ok(respo).build();

    }

    public Response delete(Long id){
        var respo = alunoRepository.deleteById(id);
        return Response.ok(respo).build();
    }

    public Aluno fromDTO(AlunoDTO objDto){
        return new Aluno(objDto.getId(), objDto.getNome(), objDto.getN1(), objDto.getN2(), objDto.getN3());
    }


    public void insert(Aluno obj){
        obj.setId(null);
        alunoRepository.persist(obj);
    }

}
