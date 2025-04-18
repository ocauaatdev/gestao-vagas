package br.com.manokaw.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    // Essa classe representa a entidade de um trabalho (vaga) no sistema, ela contém os dados necessários para a criação de uma nova vaga.
    // Ela possui um relacionamento com a entidade CompanyEntity, onde uma empresa pode ter várias vagas de emprego.

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String benefits;

    @NotBlank(message = "Este campo é obrigatório!")
    private String level;

    @ManyToOne() //posso ter muitos trabalhos(job) para uma unica empresa(company)
    @JoinColumn(name = "company_id", insertable = false, updatable = false) //quando for inserir um job sem uma company (id da company inexistente) retorna um erro
    private CompanyEntity companyEntity; //chave primaria da tabela company

    @Column(name="company_id", nullable = false) //definindo o nome da coluna no banco de dados
    private UUID companyId; //Este item faz relacionamento com a tabela CompanyEntity - No caso companyId é a chave estrangeira

    @CreationTimestamp
    private LocalDateTime createdAt;
}
