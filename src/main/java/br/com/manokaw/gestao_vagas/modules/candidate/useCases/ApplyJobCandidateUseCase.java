package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manokaw.gestao_vagas.exceptions.JobNotFoundException;
import br.com.manokaw.gestao_vagas.exceptions.UserNotFoundException;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.manokaw.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.manokaw.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.manokaw.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase { //Classe responsável onde o candidato se aplica a uma vaga

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;
    
    public ApplyJobEntity execute( UUID idCandidate, UUID idJob){

        //Verifica se o candidato existe:
        this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
            throw new UserNotFoundException(); //criando exceção personalizada
        });

        //Verifica se a vaga existe:
        this.jobRepository.findById(idJob)
        .orElseThrow(() -> {
            throw new JobNotFoundException(); //criando exceção personalizada
        });

        //Candidato se aplicando a vaga:
        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob).build();
        
        applyJob = applyJobRepository.save(applyJob); //salvando a aplicação do candidato na vaga
        return applyJob;
    }
}
