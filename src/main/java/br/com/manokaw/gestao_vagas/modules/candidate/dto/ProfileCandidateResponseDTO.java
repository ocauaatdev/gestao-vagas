package br.com.manokaw.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

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
    
    private String description;
    private String username;
    private String email;
    private UUID id;
    private String name;
}
