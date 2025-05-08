package br.com.manokaw.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.manokaw.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.manokaw.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.manokaw.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController  // Define que esta classe é um controlador REST do Spring
@RequestMapping("/candidate") // Define o caminho base para os endpoints deste controlador

public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create( @Valid @RequestBody CandidateEntity candidateEntity){
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity); // Chama o caso de uso para criar um candidato 
            return ResponseEntity.ok().body(result); // Retorna uma resposta HTTP 200 OK com o resultado da criação do candidato 
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna uma resposta HTTP 400 Bad Request com a mensagem de erro caso ocorra uma exceção durante o processo de criação do candidato
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')") // Define que este endpoint requer autorização com a role 'candidate'
    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id"); // Obtém o ID do candidato a partir do atributo da requisição HTTP
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString())); // Chama o caso de uso para obter o perfil do candidato
            return ResponseEntity.ok().body(profile); // Retorna uma resposta HTTP 200 OK com o perfil do candidato
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna uma resposta HTTP 400 Bad Request com a mensagem de erro caso ocorra uma exceção durante o processo de obtenção do perfil do candidato
        }  
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidato", description = "Informações do candidato") 
    @Operation(summary = "Listagem de vagas disponiveis para o candidato", description = "Essa função é responsável por listar todas as vagas disponiveis, baseada no filtro.") 
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
            )
        })
    })

    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
    

}
