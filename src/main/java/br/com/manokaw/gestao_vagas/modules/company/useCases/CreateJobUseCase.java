package br.com.manokaw.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;
import br.com.manokaw.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    // Classe responsável por criar uma nova vaga no sistema
    // Ela contém a lógica de negócio para criar uma nova vaga, como validações e persistência no banco de dados.
    @Autowired
    private JobRepository jobRepository;
    
    public JobEntity execute(JobEntity jobEntity){ // Método responsável por criar uma nova vaga no sistema
        // O método recebe um objeto JobEntity, que contém os dados da vaga a ser criada.
        
        return this.jobRepository.save(jobEntity); 
        // O método save() do repositório é chamado para persistir a vaga no banco de dados.
        // O objeto JobEntity é retornado após a criação, contendo os dados da vaga, incluindo o ID gerado pelo banco de dados.
    }
}
