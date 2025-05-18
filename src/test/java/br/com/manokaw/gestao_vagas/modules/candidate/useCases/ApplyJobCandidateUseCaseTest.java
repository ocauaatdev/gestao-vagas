package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.manokaw.gestao_vagas.exceptions.JobNotFoundException;
import br.com.manokaw.gestao_vagas.exceptions.UserNotFoundException;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.manokaw.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.manokaw.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;
import br.com.manokaw.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class) 
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks 
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock // Cria um mock do CandidateRepository, que simula o comportamento do repositório
    private CandidateRepository candidateRepository;

    @Mock 
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;
    
    @Test
    @DisplayName("Should not be able to apply job if candidate not found") //traduzido: "Não deve ser possível se aplicar a vaga se o candidato não for encontrado"

    // Cenário: Candidato não encontrado
    public void should_not_be_able_to_apply_job_if_candidate_not_found(){

        // Cenário: Candidato não encontrado
        // Dado que o candidato não existe no banco de dados. Quando o candidato tenta se aplicar a uma vaga
        // Então deve lançar uma exceção UserNotFoundException
        try {
            applyJobCandidateUseCase.execute(null, null); // quando o candidato for se aplicar a uma vaga e o candidato não existir retorna a exceção UserNotFoundException.
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);  //no teste, se a exceção disparada não for a UserNotFoundException, o teste falhará.
        }
    }

    @Test
    public void should_not_be_able_to_apply_job_with_job_not_found(){ // traduzido: "Não deve ser possível se aplicar a vaga se a vaga não for encontrada"

        // Dado que a vaga não existe no banco de dados. Quando o candidato tenta se aplicar a uma vaga
        // Então deve lançar uma exceção JobNotFoundException

        var idCandidate = UUID.randomUUID(); // Gerando um UUID aleatório para o candidato
        var candidate = new CandidateEntity(); // Criando um candidato qualquer
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate)); // Simulando o comportamento do repositório, retornando um candidato qualquer

        try {
            applyJobCandidateUseCase.execute(idCandidate, null); // quando o candidato for se aplicar a uma vaga e a vaga não existir retorna a exceção JobNotFoundException.
            
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);// No contexto do teste, se o candidato não existir, o teste falhará.
        }
    }

    @Test
    public void should_be_able_to_create_a_new_apply_job(){ // traduzido: "Deve ser possível criar uma nova vaga na aplicação"

        // Cenário: Candidato se aplicando a uma vaga:
        // Dado que o candidato existe no banco de dados e a vaga também. Quando o candidato tenta se aplicar a uma vaga, então deve retornar a aplicação do candidato na vaga

       var idCandidate = UUID.randomUUID(); // gerando um UUID aleatório para o candidato de teste
       var idJob = UUID.randomUUID(); // gerando um UUID aleatório para a vaga de teste

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build(); // criando uma aplicação de vaga qualquer inserindo o id do candidato e o id da vaga gerados acima
        // nesse builder, o id não é gerado, pois o builder não gera o id automaticamente, então foi necessário criar uma outra variável para o id da aplicação de vaga criada. Apenas temos o id do candidato e da vaga, que são os ids gerados acima.

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build(); // cria uma aplicação de vaga com id aleatorio, afim de que o teste não falhe, pois o id não vai ser nulo.

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity())); // quando o candidato for se aplicar a uma vaga, o findById do repositorio retorna um candidato qualquer
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

       when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated); // aqui o repositorio vai salvar a aplicação e retornar a aplicação criada. No caso foi necessario criar uma outra variavel de aplicação criada porque o id não pode ser nulo e o applyjob é o que foi criado no teste e ele não tem id por conta do builder, então se o id for nulo, o teste falharia. Para evitar isso, foi criado uma outra variavel de aplicação com id aleatorio.

       var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
        assertThat(result).hasFieldOrProperty("id"); // verificando se o resultado tem a propriedade id
        assertNotNull(result.getId()); // verificando se o id não é nulo

    }
}
// Os assert são utilizados para verificar se o resultado do teste é o esperado.
// O assertThat é utilizado para verificar se o resultado do teste é o esperado, e o assertNotNull é utilizado para verificar se o resultado não é nulo.

// O @DisplayName é utilizado para dar um nome mais legível para o teste, facilitando a leitura e compreensão do código.

// O @Test é utilizado para indicar que o método é um teste, e o @ExtendWith é utilizado para indicar que o teste deve ser executado com a extensão do Mockito.

// O @InjectMocks é utilizado para injetar as dependências necessárias para o teste, e o @Mock é utilizado para criar um mock do repositório, que simula o comportamento do repositório.

// when é utilizado para simular o comportamento do repositório, onde ele vai utilizar os métodos do repositório e retornar o que foi definido no teste. No exemplo, quando o candidato for se aplicar a uma vaga, o findById do repositorio retorna um candidato qualquer
