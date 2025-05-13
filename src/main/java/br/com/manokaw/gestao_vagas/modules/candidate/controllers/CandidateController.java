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
import br.com.manokaw.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController  // Define que esta classe é um controlador REST do Spring
@RequestMapping("/candidate") // Define o caminho base para os endpoints deste controlador
@Tag(name = "Candidato", description = "Informações do candidato") // Define a tag para o endpoint na documentação Swagger

public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato") // Define a operação para o endpoint de cadastro de candidato
    @ApiResponses({ // Define as respostas possíveis para este endpoint que basicamente servem para a documentação Swagger identificar os tipos de resposta
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class) ) 
        }), // Define a resposta de sucesso com código 200 e o conteúdo esperado
        @ApiResponse(responseCode = "400", description = "Usuário já existente") // Define a resposta de erro com código 400 e a descrição do erro
    })
    public ResponseEntity<Object> create( @Valid @RequestBody CandidateEntity candidateEntity){
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity); // Chama o caso de uso para criar um candidato 
            return ResponseEntity.ok().body(result); // Retorna uma resposta HTTP 200 OK com o resultado da criação do candidato 
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna uma resposta HTTP 400 Bad Request com a mensagem de erro caso ocorra uma exceção durante o processo de criação do candidato
        }
    }

    @GetMapping("/") // Define o endpoint para obter o perfil do candidato
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato") 

    @ApiResponses({ // Define as respostas possíveis para este endpoint que basicamente servem para a documentação Swagger identificar os tipos de resposta
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class) ) 
        }), // Define a resposta de sucesso com código 200 e o conteúdo esperado
        @ApiResponse(responseCode = "400", description = "User not found")
    })

    @SecurityRequirement(name = "jwt_auth") // Define que este endpoint requer autenticação JWT

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
    @Operation(summary = "Listagem de vagas disponiveis para o candidato", description = "Essa função é responsável por listar todas as vagas disponiveis, baseada no filtro.") 
    @ApiResponses({ // Define as respostas possíveis para este endpoint que basicamente servem para a documentação Swagger identificar os tipos de resposta
        @ApiResponse(responseCode = "200", content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)) // Define o conteúdo esperado da resposta com um array de JobEntity 
            ) // Define o conteúdo esperado da resposta com um array de JobEntity
        }) // Define a resposta de sucesso com código 200 e o conteúdo esperado
    })

    @SecurityRequirement(name = "jwt_auth") // Define que este endpoint requer autenticação JWT
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
    

}
