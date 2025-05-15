package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.manokaw.gestao_vagas.exceptions.UserNotFoundException;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.manokaw.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class) // Extensão do Mockito, para permitir o uso de anotações do Mockito
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks // Injeta as dependências necessárias para o teste, pois o autowired não funciona em testes
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock // Cria um mock do CandidateRepository, que simula o comportamento do repositório
    private CandidateRepository candidateRepository;

    @Mock // Cria um mock do CandidateRepository, que simula o comportamento do repositório
    private JobRepository jobRepository;
    
    @Test
    @DisplayName("Should not be able to apply job if candidate not found")
    // Cenário: Candidato não encontrado
    public void should_not_be_able_to_apply_job_if_candidate_not_found(){ // TODO Auto-generated method stub
        // Cenário: Candidato não encontrado
        // Dado que o candidato não existe no banco de dados
        // Quando o candidato tenta se aplicar a uma vaga
        // Então deve lançar uma exceção UserNotFoundException
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
        

    }
}
