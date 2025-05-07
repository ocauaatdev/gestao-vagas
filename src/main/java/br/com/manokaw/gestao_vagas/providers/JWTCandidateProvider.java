package br.com.manokaw.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;



@Service
public class JWTCandidateProvider {
    // Classe responsável por validar o token JWT de um candidato
    // Ela contém a lógica de validação do token, como verificar a assinatura e a data de expiração.

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public DecodedJWT validateToken(String token){ // Método responsável por validar o token JWT
        // O método recebe um token JWT como parâmetro e retorna um objeto DecodedJWT, que contém as informações decodificadas do token.

        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey); // Cria um algoritmo de assinatura HMAC256 usando a chave secreta definida no application.properties (ou .yml)
        // O algoritmo é usado para verificar a assinatura do token e garantir sua integridade.

        try {
            var tokenDecoded = JWT.require(algorithm)
        .build()
        .verify(token); 
        // Verifica o token usando o algoritmo definido. Se o token for válido, ele é decodificado e retornado.
        // Se o token estiver expirado ou inválido, uma exceção JWTVerificationException será lançada.

        // O método retorna o token decodificado, que contém as informações do token, como payload, header e assinatura.
        // O payload contém os dados do usuário, como ID, nome, e-mail, etc., que foram incluídos no token durante sua criação.

        return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null; // Se o token estiver inválido ou expirado, retorna null.
        }
    }
}
/**
 * Classe responsável por validar o token JWT de um candidato.
 * 
 * Um "Provider" é uma classe ou componente que fornece uma funcionalidade específica
 * ou serviço dentro de uma aplicação. Neste caso, o JWTCandidateProvider é um 
 * provedor que realiza a validação de tokens JWT, verificando sua assinatura e 
 * data de expiração, garantindo a segurança e autenticidade do token.
 */
