package br.com.manokaw.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super("User not found");
    }
}
