package br.com.manokaw.gestao_vagas.modules.candidate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


//                                                         (entidade) e (id) a chave primaria
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
    Optional <CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);
}
