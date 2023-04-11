package org.acme.controller;

import lombok.AllArgsConstructor;
import org.acme.dto.AlunoDTO;
import org.acme.entity.Aluno;
import org.acme.exceptions.ResponseError;
import org.acme.service.AlunoService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/api/aluno")
@AllArgsConstructor
public class AlunoController {

    @Inject
    Validator validator;

    @Inject
    AlunoService alunoService;


    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id){
        var resposta = alunoService.findById(id);
        return resposta;
    }

    @GET
    public List<Aluno> retrieveCustomers(){
        List<Aluno> alunos = new ArrayList<>();
        try {
            alunos = alunoService.findAll();
        } catch (Exception e){
            e.printStackTrace();
        }

        return alunos;
    }


    @POST
    @Transactional
    public Response insert(AlunoDTO objDTO){

        Aluno obj = alunoService.fromDTO(objDTO);
        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(objDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        alunoService.insert(obj);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }
}
