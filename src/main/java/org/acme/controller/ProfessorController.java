package org.acme.controller;


import lombok.AllArgsConstructor;
import org.acme.dto.AlunoDTO;
import org.acme.dto.ProfessorDTO;
import org.acme.entity.Aluno;
import org.acme.entity.Professor;
import org.acme.exceptions.ResponseError;
import org.acme.service.AlunoService;
import org.acme.service.ProfessorService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/api/professor")
@AllArgsConstructor
public class ProfessorController {

    @Inject
    ProfessorService professorService;

    @Inject
    AlunoService alunoService;

    @Inject
    Validator validator;

    @GET
    public List<Professor> retrieveCustomers(){
        List<Professor> professores = new ArrayList<>();
        try {
            professores = professorService.findAll();
        } catch (Exception e){
            e.printStackTrace();
        }

        return professores;
    }


    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id){
        var respo = professorService.findById(id);
        if (respo != null){
            return Response.ok(respo).build();
        }
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed("manager")
    public Response delete(@PathParam("id") Long id){
        var respo = professorService.findById(id);

        if(respo != null){
            professorService.delete(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    @RolesAllowed("manager")
    public Response insert(ProfessorDTO objDTO){

        Professor obj = professorService.fromDTO(objDTO);
        Set<ConstraintViolation<ProfessorDTO>> violations = validator.validate(objDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        professorService.insert(obj);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed("manager")
    public Response update(@PathParam("id")Long id, ProfessorDTO newProfessor){
        var respo = professorService.findById(id);

        if (respo != null){
            professorService.update(id, newProfessor);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}/{idAluno}")
    @Transactional
    @RolesAllowed("manager")
    public Response alteraAluno(@PathParam("id")Long id, @PathParam("idAluno")Long idAluno){
        var professor = professorService.findById(id);
        var aluno = alunoService.findById(idAluno);

        if(professor != null && aluno != null) {
            professorService.alteraAluno(professor, aluno);

            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();

    }


}
