// Exceção personalizada para indicar que um usuário já existe
package br.com.manokaw.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    /**
     * Construtor que define a mensagem de erro quando um usuário já existe.
     */
    public UserFoundException() {
        super("Usuário já existente");
    }
}
