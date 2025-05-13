package br.com.manokaw.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {

    // Essa classe é usada para criar uma nova vaga de emprego no sistema, ela contém os dados necessários para a criação da vaga.
    
    @Schema(example = "Vaga para pessoa desenvolvedora Junior", requiredMode = RequiredMode.REQUIRED) // adiciona exemplo para ser exibido na documentação
    // e define que esse campo é obrigatório
    private String description;

    @Schema(example = "GymPass, plano de saude, vale transporte", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
