package br.com.manokaw.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.manokaw.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter{
    // Classe responsável por filtrar as requisições HTTP e verificar se o token JWT é válido
    // Ela estende a classe OncePerRequestFilter, que garante que o filtro seja executado apenas uma vez por requisição.

    @Autowired
    private JWTCandidateProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                // Método que executa a lógica de filtragem da requisição
        
                //SecurityContextHolder.getContext().setAuthentication(null);
                String header = request.getHeader("Authorization");

                if (request.getRequestURI().startsWith("/candidate")) { // Verifica se a requisição é para o endpoint "/candidate"
                    
                    if(header != null){
                        var token = this.jwtProvider.validateToken(header);
    
                        if (token == null) {
                            // Se o header Authorization não estiver presente, retorna 401 Unauthorized
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
    
                        request.setAttribute("candidate_id", token.getSubject()); // Adiciona o ID do candidato ao atributo da requisição
                        var roles = token.getClaim("roles").asList(Object.class); // Recupera as roles do token


                        var grants = roles.stream()
                        .map(
                            role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()) // Adiciona o prefixo "ROLE_" a cada role
                        ).toList(); // Converte as roles em uma lista de GrantedAuthority
                        // Cria um objeto UsernamePasswordAuthenticationToken com o ID do candidato e as roles

                        UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);// Cria um objeto de autenticação com o ID do candidato e as roles

                        SecurityContextHolder.getContext().setAuthentication(auth); // Define o contexto de segurança com o objeto de autenticação criado
                    }
                }
                
                filterChain.doFilter(request, response);
    }
    
}
