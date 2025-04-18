package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.manokaw.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    //Classe responsável por encontrar o candidato pelo Id e retornar as informações do perfil
    //para serem exibidas na tela de perfil do candidato
    
    @Autowired
    private CandidateRepository candidateRepository;

    //Encontrando candidate pelo Id

    public ProfileCandidateResponseDTO execute(UUID idCandidate){
        // Método que executa a busca do candidato pelo ID
        // Ele recebe um UUID que representa o ID do candidato a ser buscado

       var candidate = this.candidateRepository.findById(idCandidate)
       .orElseThrow(() -> {
        throw new UsernameNotFoundException("User not found");
       }); // Se o candidato não for encontrado, lança uma exceção

       // Se o candidato for encontrado, cria um objeto ProfileCandidateResponseDTO com as informações do candidato
       //Selecionando informações que vão ser exibidas no perfil através do DTO
       var candidateDTO = ProfileCandidateResponseDTO.builder()
       .description(candidate.getDescription())
       .username(candidate.getUsername())
       .email(candidate.getEmail())
       .name(candidate.getName())
       .id(candidate.getId())
       .build();
       
       return candidateDTO; // Retorna o objeto ProfileCandidateResponseDTO com as informações do candidato
    }
}
