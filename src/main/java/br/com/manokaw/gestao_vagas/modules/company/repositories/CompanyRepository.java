package br.com.manokaw.gestao_vagas.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.manokaw.gestao_vagas.modules.company.entities.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    // Essa interface é responsável por realizar operações de CRUD (Create, Read, Update e Delete) na entidade CompanyEntity.
    // Ela estende a interface JpaRepository, que fornece métodos prontos para realizar essas operações.

    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email); // Método para encontrar uma empresa pelo nome de usuário ou e-mail
    Optional<CompanyEntity> findByUsername(String username); // Método para encontrar uma empresa pelo nome de usuário
}
