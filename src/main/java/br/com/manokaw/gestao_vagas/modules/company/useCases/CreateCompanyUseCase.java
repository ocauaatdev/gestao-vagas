package br.com.manokaw.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.manokaw.gestao_vagas.exceptions.UserFoundException;
import br.com.manokaw.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.manokaw.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase { // Classe responsável por criar uma nova empresa no sistema

    @Autowired
    private CompanyRepository companyRepository;

    //necessario para criptografar senha
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity){

        //Verificação para analisar se já existe uma company com o mesmo username ou email
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) -> {
            throw new UserFoundException();
        });

        //Criptografando senha
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password); //senha criptografada


        return this.companyRepository.save(companyEntity);
    }
}
