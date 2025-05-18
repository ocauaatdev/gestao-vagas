package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
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
import br.com.manokaw.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class) // Extensão do Mockito, para permitir o uso de anotações do Mockito
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks // Injeta as dependências necessárias para o teste, pois o autowired não funciona em testes
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock // Cria um mock do CandidateRepository, que simula o comportamento do repositório
    private CandidateRepository candidateRepository;

    @Mock // Cria um mock do CandidateRepository, que simula o comportamento do repositório
    //Mock é uma simulação de um objeto real, que pode ser usado para testar o comportamento de outro objeto
    private JobRepository jobRepository;
    
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
}
