package br.com.manokaw.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Indica que essa classe é uma classe de configuração do Spring
@Configuration
@EnableMethodSecurity // Habilita a segurança em métodos, permitindo o uso de anotações como @PreAuthorize
public class SecurityConfig {
    // Classe responsável por configurar a segurança da aplicação, incluindo autenticação e autorização de usuários.
    // Ela define quais rotas são públicas e quais requerem autenticação, além de configurar o filtro de segurança.

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    private static final String[] SWAGGER_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**"
    };
    
     // Bean responsável por configurar a cadeia de filtros de segurança
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Desativa a proteção contra CSRF (útil em APIs REST, pois geralmente não há sessão nem cookies)
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                // Define as rotas públicas que não precisam de autenticação
                auth.requestMatchers("/candidate/").permitAll() // Acesso público à rota /candidate/
                .requestMatchers("/company/").permitAll() // Acesso público à rota /company/
                .requestMatchers("/company/auth").permitAll() // Acesso público à rota de autenticação /auth/company
                .requestMatchers(SWAGGER_LIST).permitAll() // Acesso público à interface Swagger
                .requestMatchers("/candidate/auth").permitAll();

                // Qualquer outra requisição precisa estar autenticada
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
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
