package br.com.manokaw.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.manokaw.gestao_vagas.exceptions.UserFoundException;
import br.com.manokaw.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.manokaw.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
    // Define que essa classe é um controller REST e que responderá requisições para "/company"
    
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){ 
        // Recebe um objeto CompanyEntity no corpo da requisição para criar uma nova empresa
        try{
            var result = this.createCompanyUseCase.execute(companyEntity); // Executa o caso de uso de criação de empresa com os dados recebidos
            // Retorna uma resposta HTTP 200 (OK) com o resultado da criação da empresa
            return ResponseEntity.ok().body(result);
        }catch(UserFoundException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage()); // Caso ocorra algum erro (como dados inválidos), retorna HTTP 400 (Bad Request) com a mensagem do erro
        }
       
    }
}
