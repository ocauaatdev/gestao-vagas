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
    
    private String acess_token;
    private Long expires_in;
}
