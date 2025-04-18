package br.com.manokaw.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {
    // Classe responsável por validar o token JWT de um usuário
    // Ela contém a lógica de validação do token, como verificar a assinatura e a data de expiração.

    @Value("${security.token.secret}")
    private String secretKey;
    
    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ","");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try{
        var tokenDecoded = JWT.require(algorithm)
        .build()
        .verify(token);
        return tokenDecoded;
        }catch(JWTVerificationException ex){
            ex.printStackTrace();
            return null;
        }
        
    }
}
