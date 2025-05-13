package br.com.manokaw.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.manokaw.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.manokaw.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

// Define que essa classe é um controller REST e que responderá requisições para "/auth"
@RestController
@RequestMapping("/company")
public class AuthCompanyController {
    
    
    // Injeta automaticamente a instância de AuthCompanyUseCase (injeção de dependência)
    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO){
        // Recebe um objeto AuthCompanyDTO no corpo da requisição para autenticação da empresa
        // O AuthCompanyDTO deve conter os dados necessários para autenticação, como email e senha
        try{
            // Executa o caso de uso de autenticação com os dados recebidos
            var result = this.authCompanyUseCase.execute(authCompanyDTO);

             // Retorna uma resposta HTTP 200 (OK) com o resultado da autenticação
            return ResponseEntity.ok().body(result);
        }catch(Exception e){
             // Caso ocorra algum erro (como dados inválidos), retorna HTTP 401 (Não autorizado) com a mensagem do erro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
