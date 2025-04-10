package br.com.manokaw.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.manokaw.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.manokaw.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service // Indica que esta classe é um componente do tipo "Service" do Spring (lógica de negócio)
public class AuthCompanyUseCase {

    // Recupera a chave secreta definida no application.properties (ou .yml) para gerar o token JWT
    @Value("${security.token.secret}")
    private String secretKey;
    
    // Injeta a dependência do repositório de empresas
    @Autowired
    private CompanyRepository companyRepository;

    // Injeta a dependência do codificador de senhas (geralmente BCrypt)
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método responsável por autenticar uma empresa e gerar um token JWT caso os dados estejam corretos.
     * 
     * @param authCompanyDTO DTO contendo username e senha
     * @return token JWT caso a autenticação seja bem-sucedida
     * @throws AuthenticationException caso o username não exista ou a senha esteja incorreta
     */
    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        // Busca a empresa pelo username informado
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                // Lança exceção se o username não for encontrado
                throw new UsernameNotFoundException("Username/password incorrect");
            }
        );

        // Verifica se a senha informada corresponde à senha armazenada (criptografada)
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            // Se a senha estiver incorreta, lança uma exceção de autenticação
            throw new AuthenticationException();
        }

        // Se a senha estiver correta, gera um token JWT usando a chave secreta
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // Cria o token com emissor e o ID da empresa como "subject"
        var token = JWT.create()
            .withIssuer("javagas") // nome identificador da aplicação
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(company.getId().toString()) // define o ID da empresa como subject
            .sign(algorithm); // assina o token com o algoritmo e a chave secreta

        // Retorna o token gerado
        return token;
    }
}
