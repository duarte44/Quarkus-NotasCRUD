package org.acme.controller;


import lombok.AllArgsConstructor;
import org.acme.dto.AlunoDTO;
import org.acme.dto.ProfessorDTO;
import org.acme.entity.Aluno;
import org.acme.entity.Professor;
import org.acme.exceptions.ResponseError;
import org.acme.service.ProfessorService;

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

@Path("/api/professor")
@AllArgsConstructor
public class ProfessorController {

    @Inject
    ProfessorService professorService;

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

    @POST
    @Transactional
    public Response insert(ProfessorDTO objDTO){

        Professor obj = professorService.fromDTO(objDTO);
        Set<ConstraintViolation<ProfessorDTO>> violations = validator.validate(objDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        professorService.insert(obj);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }
}
