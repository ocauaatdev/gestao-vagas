package br.com.manokaw.gestao_vagas.exceptions;

//import br.com.manokaw.gestao_vagas.modules.candidate.controllers.CandidateController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Indica que esta classe é um controlador global de exceções
public class ExceptionHandlerController {

    // Dependência para obter mensagens de erro internacionalizadas
    private MessageSource messageSource;

    // Construtor que recebe a instância de MessageSource para buscar mensagens no idioma correto
    public ExceptionHandlerController(MessageSource message){
        this.messageSource = message;
    }

    /**
     * Método responsável por capturar exceções de validação de argumentos em métodos anotados com @Valid.
     * Exemplo: Quando um campo obrigatório não for preenchido.
     *
     * @param e Exceção capturada (MethodArgumentNotValidException)
     * @return ResponseEntity contendo a lista de erros no formato ErrorMessageDTO e status HTTP 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> dto = new ArrayList<>();

        // Percorre os erros de validação e os adiciona na lista DTO
        e.getBindingResult().getFieldErrors().forEach(err -> {
            // Obtém a mensagem de erro no idioma configurado no contexto da aplicação
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            // Cria um objeto DTO contendo a mensagem e o campo onde ocorreu o erro
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        // Retorna a lista de erros no corpo da resposta com status 400 (Bad Request)
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }   
}  
    

