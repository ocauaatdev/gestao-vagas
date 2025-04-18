package br.com.manokaw.gestao_vagas.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID>{
    // Essa interface é responsável por realizar operações de CRUD (Create, Read, Update e Delete) na entidade JobEntity.
    // Ela estende a interface JpaRepository, que fornece métodos prontos para realizar essas operações.
    // Não é necessário implementar os métodos, pois o Spring Data JPA já fornece a implementação padrão para os métodos de CRUD.
    
}
