package br.com.manokaw.gestao_vagas.modules.company.entities;

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

@Entity(name="company")
@Data
public class CompanyEntity {
    // Essa classe representa a entidade de uma empresa no sistema, ela contém os dados necessários para a autenticação e criação de uma nova empresa.

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

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
    
    private String website;
    private String name;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
