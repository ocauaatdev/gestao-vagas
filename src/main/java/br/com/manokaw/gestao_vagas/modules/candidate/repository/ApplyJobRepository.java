package br.com.manokaw.gestao_vagas.modules.candidate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.manokaw.gestao_vagas.modules.candidate.entity.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    // esse repositório é responsável por gerenciar as operações de persistência da entidade ApplyJobEntity
    // e herda os métodos básicos de CRUD do JpaRepository, como save(), findById(), delete(), etc.
    // que vai servir basicamente para no momento em que o candidato se aplica a uma vaga
    // e o candidato se aplica a mais de uma vaga, então o repositório vai gerenciar essas operações
    
}
