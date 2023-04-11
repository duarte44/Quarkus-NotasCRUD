package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.Aluno;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno> {

}
