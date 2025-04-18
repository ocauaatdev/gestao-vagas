package br.com.manokaw.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
    // Essa classe é usada para autenticar a empresa no sistema, ela contém os dados necessários para a autenticação.
    // O DTO é um padrão de projeto que tem como objetivo transportar dados entre processos, geralmente entre a camada de controle e a camada de serviço.
    
    private String password;
    private String username;
}
