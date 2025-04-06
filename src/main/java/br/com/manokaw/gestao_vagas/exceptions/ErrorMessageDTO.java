package br.com.manokaw.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // Gera automaticamente os métodos getters, setters, equals, hashCode e toString
@AllArgsConstructor // Gera um construtor com todos os atributos da classe
public class ErrorMessageDTO {
    private String message; // Mensagem de erro descritiva
    private String field;   // Campo da requisição onde ocorreu o erro
}
