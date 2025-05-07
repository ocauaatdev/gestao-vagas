package br.com.manokaw.gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobDTO {

    // Essa classe é usada para criar uma nova vaga de emprego no sistema, ela contém os dados necessários para a criação da vaga.
    
    private String description;
    private String benefits;
    private String level;
}
