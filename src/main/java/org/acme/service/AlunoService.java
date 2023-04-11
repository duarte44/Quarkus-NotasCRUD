package org.acme.service;

import org.acme.dto.AlunoDTO;
import org.acme.entity.Aluno;
import org.acme.repositories.AlunoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;


    public List<Aluno> findAll(){
        return alunoRepository.findAll().list();
    }

    public Aluno findById(Long id){
        var respo = alunoRepository.findById(id);
        return respo;

    }

    public Response delete(Long id){
        var respo = alunoRepository.deleteById(id);
        return Response.ok(respo).build();
    }

    public static Aluno fromDTO(AlunoDTO objDto){
        return new Aluno(objDto.getId(), objDto.getNome(), objDto.getN1(), objDto.getN2(), objDto.getN3());
    }


    public void insert(Aluno obj){
        obj.setId(null);
        alunoRepository.persist(obj);
    }

    public Response update(Long id, AlunoDTO newObj){
        Aluno obj = alunoRepository.findById(id);
            updateData(newObj, obj);
            return Response.noContent().build();

    }

    private void updateData(AlunoDTO newObj, Aluno obj){
        obj.setNome(newObj.getNome());
        obj.setN1(newObj.getN1());
        obj.setN2(newObj.getN2());
        obj.setN3(newObj.getN3());
    }


}
