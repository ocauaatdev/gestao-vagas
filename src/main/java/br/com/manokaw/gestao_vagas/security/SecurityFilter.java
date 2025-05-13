package br.com.manokaw.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.manokaw.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            // SecurityContextHolder.getContext().setAuthentication(null);
            String header = request.getHeader("Authorization");

           if (request.getRequestURI().startsWith("/company")) {
            
            if(header != null){
                var token = this.jwtProvider.validateToken(header);
                 if (token == null) {
                     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                     return;
                 }

                 var roles = token.getClaim("roles").asList(Object.class); // Obtem as roles do token
                 var grants = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                    .toList(); // Converte as roles em SimpleGrantedAuthority

                 request.setAttribute("company_id", token.getSubject()); // Adiciona o company_id no request
                UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                    grants); // Cria o token de autenticação
                 SecurityContextHolder.getContext().setAuthentication(auth); // Adiciona o token de autenticação no contexto de segurança
             }
           }

        
        filterChain.doFilter(request, response);

        //throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }
    
}
