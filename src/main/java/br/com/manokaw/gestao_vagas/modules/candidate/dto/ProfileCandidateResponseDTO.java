package br.com.manokaw.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    //dto para selecionar informações que serão exibidas no perfil
    
    @Schema(example = "Desenvolvedor Java", description = "Descrição do candidato")
    private String description;

    @Schema(example = "joao_dev", description = "Nome de usuário do candidato")
    private String username;

    @Schema(example = "joao@email.com", description = "Email do candidato")
    private String email;

    private UUID id;

    @Schema(example = "João", description = "Nome do candidato")
    private String name;
}
