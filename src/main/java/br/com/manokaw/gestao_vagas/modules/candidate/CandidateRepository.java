package br.com.manokaw.gestao_vagas.modules.candidate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


//                                                         (entidade) e (id) a chave primaria
/**
 * A principal vantagem de utilizar um repository no contexto da sua aplicação é a abstração
 * e simplificação do acesso aos dados. O repository atua como uma camada intermediária entre
 * a aplicação e o banco de dados, permitindo que você manipule os dados de forma mais intuitiva
 * e com menos código repetitivo. 
 * 
 * No caso do JpaRepository, ele já fornece uma série de métodos prontos, como salvar, buscar,
 * atualizar e deletar entidades, além de permitir a criação de consultas personalizadas, como
 * os métodos findByUsernameOrEmail e findByUsername definidos aqui. Isso reduz a necessidade
 * de escrever SQL manualmente e melhora a legibilidade e manutenção do código.
 * 
 * Além disso, o uso de repositories promove o princípio da separação de responsabilidades,
 * mantendo a lógica de acesso a dados isolada da lógica de negócios, o que torna a aplicação
 * mais modular e fácil de testar.
 */
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{

    //Método para encontrar o candidato pelo id
    //O método findById já existe na interface JpaRepository, então não é necessário implementá-lo novamente



    Optional <CandidateEntity> findByUsernameOrEmail(String username, String email); //Método para encontrar o candidato pelo username ou email
    //O método findByUsernameOrEmail é um método personalizado que busca um candidato com base no username ou email fornecido.

    Optional<CandidateEntity> findByUsername(String username); //Método para encontrar o candidato pelo username
    //O método findByUsername é um método personalizado que busca um candidato com base no username fornecido.
}
