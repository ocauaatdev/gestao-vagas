package br.com.manokaw.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Indica que essa classe é uma classe de configuração do Spring
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;
    
     // Bean responsável por configurar a cadeia de filtros de segurança
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Desativa a proteção contra CSRF (útil em APIs REST, pois geralmente não há sessão nem cookies)
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                // Define as rotas públicas que não precisam de autenticação
                auth.requestMatchers("/candidate/").permitAll() // Acesso público à rota /candidate/
                .requestMatchers("/company/").permitAll() // Acesso público à rota /company/
                .requestMatchers("/auth/company").permitAll(); // Acesso público à rota de autenticação /auth/company

                // Qualquer outra requisição precisa estar autenticada
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
        
        ;
        // Retorna o objeto SecurityFilterChain com as configurações definidas
        return http.build();
    }

    //Criptografa senhas dos usuarios
    // Bean que define o codificador de senhas a ser usado na aplicação
    // Aqui está sendo utilizado o BCrypt, um algoritmo forte de hash
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
