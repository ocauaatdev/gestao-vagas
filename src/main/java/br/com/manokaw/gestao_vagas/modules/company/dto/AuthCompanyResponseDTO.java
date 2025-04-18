package br.com.manokaw.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera metodos getters e setters automaticamente
@Builder // Gera o padrão Builder para a classe facilitando a criação de objetos
@NoArgsConstructor // Gera um construtor padrão (sem argumentos)
@AllArgsConstructor // gera um construtor com todos os argumentos
// DTO (Data Transfer Object) para a resposta de autenticação da empresa
public class AuthCompanyResponseDTO {

    // Essa classe é usada para armazenar a resposta da autenticação da empresa no sistema, ela contém os dados necessários para a autenticação.
    
    private String acess_token; // Token de acesso gerado após a autenticação
    private Long expires_in; // Tempo de expiração do token em segundos
}
