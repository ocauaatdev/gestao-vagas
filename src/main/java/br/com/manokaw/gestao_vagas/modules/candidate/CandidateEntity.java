package br.com.manokaw.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data  // Gera automaticamente os métodos getters, setters, toString, equals e hashCode
@Entity(name="candidate")  // Define que esta classe é uma entidade JPA e será mapeada para a tabela "candidate"
public class CandidateEntity {

    // Atributos da entidade CandidateEntity, que representa um candidato no sistema de gestão de vagas
    // Cada atributo corresponde a uma coluna na tabela "candidate" no banco de dados

    @Id  // Indica que este campo é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.UUID)  // Gera automaticamente um UUID como identificador único
    private UUID id;

    private String name;  // Nome do candidato (não possui validações obrigatórias)

    @NotBlank  // Garante que o campo não pode ser nulo nem vazio
    @Pattern(regexp = "\\S+", message = "O campo [username] não pode conter espaços")  
    // Validação: o nome de usuário não pode conter espaços em branco
    private String username;

    @Length(min = 10, max = 100, message = "A senha deve ter entre 10 até 100 caracteres")  
    // Garante que a senha tenha entre 10 e 100 caracteres
    private String password;

    @Email(message = "O campo [email] deve ser preenchido com um e-mail válido")  
    // Valida se o campo contém um e-mail no formato correto
    private String email;

    private String description;  // Campo para descrição do candidato (não obrigatório)
    private String curriculum;  // Link ou caminho para o currículo do candidato

    @CreationTimestamp  // Preenche automaticamente este campo com a data/hora no momento da criação
    private LocalDateTime createdAt;
}
