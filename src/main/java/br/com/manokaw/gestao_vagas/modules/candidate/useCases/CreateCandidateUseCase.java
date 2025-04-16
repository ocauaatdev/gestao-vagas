package br.com.manokaw.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.manokaw.gestao_vagas.exceptions.UserFoundException;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.manokaw.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    //Chamando repositorio
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        // Antes de salvar, verifica se já existe um candidato com o mesmo username ou email
        this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).
        ifPresent((user) ->{
            // Se encontrar um usuário com o mesmo username ou email, lança uma exceção
            throw new UserFoundException();
        });
        // Se não houver duplicação, salva o candidato no banco de dados

        var password = passwordEncoder.encode(candidateEntity.getPassword()); //Criptografa senha
        candidateEntity.setPassword(password); //lança senha criptografada no banco de dados

        return this.candidateRepository.save(candidateEntity);
    }
}
