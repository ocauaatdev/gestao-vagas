package br.com.manokaw.gestao_vagas.modules.candidate.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotação do Lombok para gerar automaticamente os métodos getters e setters, além de outros métodos úteis como toString(), equals() e hashCode()
@Builder // Anotação do Lombok para permitir a construção de objetos dessa classe usando o padrão Builder
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os argumentos
public class AuthCandidateResponseDTO { 
    
    // DTO (Data Transfer Object) utilizado para encapsular a resposta de autenticação de um candidato. Contém os campos necessários para o processo de autenticação, como o token de acesso (access_token) e o tempo de expiração (expires_in).
    
    private String acess_token;
    private Long expires_in;
}
