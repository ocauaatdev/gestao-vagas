package br.com.manokaw.gestao_vagas.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.manokaw.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apply_jobs") //criando a tabela apply_jobs no banco de dados
// a tabela apply_jobs é responsável por armazenar as informações de aplicação de candidatos a vagas
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne // Relacionamento com a entidade CandidateEntity ao qual o candidato se aplica a mais de uma vaga
    
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false) // aqui o JoinColumn é usado para mapear a chave estrangeira que no caso se referencia as colunas
    // candidate_id e job_id, que são as chaves estrangeiras que referenciam as tabelas de candidatos e vagas respectivamente
    private CandidateEntity candidateEntity;

    @ManyToOne // e a vaga pode ter mais de um candidato
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity jobEntity;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Object thenReturn(ApplyJobEntity applyJob) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'thenReturn'");
    }

}
