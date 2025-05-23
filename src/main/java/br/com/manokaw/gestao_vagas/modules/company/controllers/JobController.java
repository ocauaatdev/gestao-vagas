package br.com.manokaw.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.manokaw.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.manokaw.gestao_vagas.modules.company.entities.JobEntity;
import br.com.manokaw.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')") // Verifica se o usuário tem a role 'COMPANY' para acessar esse endpoint
    // Ou seja, apenas empresas autenticadas podem criar vagas
    // Se por acaso o usuário não tiver a role 'COMPANY', o Spring Security irá retornar um erro
    // Exemplo: caso o usuario candidate tente criar uma vaga, o Spring Security irá retornar um erro 403 (Forbidden)
    @Tag(name = "Vagas", description = "Informações das Vagas") 
    @Operation(summary = "Cadastro de vagas.", description = "Essa função é responsável por cadastrar as vagas dentro da empresa") 
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(
                schema = @Schema(implementation = JobEntity.class)
            ) 
        }) 
    })

    @SecurityRequirement(name = "jwt_auth") // Define que este endpoint requer autenticação JWT

    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){

        var companyId = request.getAttribute("company_id");

        //jobEntity.setCompanyId(UUID.fromString(companyId.toString()));

        var jobEntity = JobEntity.builder()
        .companyId(UUID.fromString(companyId.toString()))
        .benefits(createJobDTO.getBenefits())
        .description(createJobDTO.getDescription())
        .level(createJobDTO.getLevel())
        .build();

        // O método createJobUseCase.execute() é responsável por criar a vaga no banco de dados
        return this.createJobUseCase.execute(jobEntity);
    }
}
