package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.manokaw.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.manokaw.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

    //Classe responsável por autenticar o candidato, gerando um token JWT para acesso a API

    @Value("${security.token.secret.candidate}")
    private String secretKey; //Chave secreta para assinar o token JWT

    @Autowired
    private CandidateRepository candidateRepository; //Repositório para acessar os dados do candidato

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{ 
        
        //Método que executa a autenticação do candidato
        //Verifica se o candidato existe no banco de dados através do username fornecido

        //Se o candidato não existir dispara uma Exception
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/Password incorrect");
        });

        //Se ele existir valida a senha
        var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), candidate.getPassword()); //Verifica se a senha fornecida corresponde a senha armazenada no banco de dados

        //Se a senha não corresponder dispara uma Exception
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        //Se a senha corresponder, gera o token JWT
        //O token contém informações como o ID do candidato, o papel (role) e a data de expiração
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        //A data de expiração do token é definida para 10 minutos a partir do momento atual
        //O token é assinado com a chave secreta e contém as informações do candidato
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
            .withIssuer("javagas") //Emissor do token
            .withSubject(candidate.getId().toString()) //ID do candidato
            .withClaim("roles", Arrays.asList("CANDIDATE")) //Papel do candidato
            .withExpiresAt(expiresIn) //aumentando expiração do token
            .sign(algorithm); //Assinatura do token com a chave secreta

        //Cria um objeto AuthCandidateResponseDTO com o token e a data de expiração
        var authCandidateResponse = AuthCandidateResponseDTO.builder()
        .acess_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

        //Retorna o objeto AuthCandidateResponseDTO com o token e a data de expiração
        return authCandidateResponse;

    }
}
