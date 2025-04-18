package br.com.manokaw.gestao_vagas.modules.candidate.dto;

/**
 * DTO (Data Transfer Object) utilizado para encapsular os dados de autenticação
 * de um candidato. Contém os campos necessários para o processo de login,
 * como o nome de usuário (username) e a senha (password).
 */
public record AuthCandidateRequestDTO(String username, String password) {
    
}
