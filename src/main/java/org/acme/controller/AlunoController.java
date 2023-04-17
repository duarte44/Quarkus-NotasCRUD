package org.acme.controller;

import lombok.AllArgsConstructor;
import org.acme.dto.AlunoDTO;
import org.acme.entity.Aluno;
import org.acme.exceptions.ResponseError;
import org.acme.service.AlunoService;
import org.acme.service.ProfessorService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
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

    @Inject
    ProfessorService professorService;


    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id){
        var resposta = alunoService.findById(id);
        if (resposta != null){
            return Response.ok(resposta).build();
        }
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
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
    @Path("{id}")
    public Response insert(AlunoDTO objDTO, @PathParam("id") Long id){

        Aluno obj = alunoService.fromDTO(objDTO);
        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(objDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        alunoService.insert(obj, id);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response delete(@PathParam("id")Long id){
        var aluno = alunoService.findById(id);

        if(aluno != null){
            alunoService.delete(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, AlunoDTO newAluno){
        var aluno = alunoService.findById(id);

        if(aluno != null){
            alunoService.update(id, newAluno);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }



}
