package app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorController {

	// Método para tratar exceções do tipo MethodArgumentNotValidException.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// Cria um mapa (semelhante a uma lista, mas cada elemento tem uma chave exclusiva associada a ele).
		// Para armazenar os erros de validação, onde cada chave representa o nome do campo com erro.
		// E o valor que retorna é a mensagem de erro correspondente ja criada.
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			// Obtém o nome do campo onde ocorreu o erro.
			String fieldName = ((FieldError) error).getField();
			// Obtém a mensagem de erro associada ao erro de validação.
			String errorMessage = error.getDefaultMessage();
			// Adiciona o nome do campo e a mensagem de erro ao mapa de erro
			errors.put(fieldName, errorMessage);
		});
		// Retorna uma resposta HTTP 400.
		return ResponseEntity.badRequest().body(errors);
	}
}

//Esta Controller lida com exceções de validação.
//Quando ocorre uma exceção este método handleValidationExceptions é acionado.
//Ele extrai informações com o nome do campo coloca a mensagem de erro criada nas outras controllers.
//E retorna uma resposta de (Bad Request) contendo os erros da validação no corpo da resposta.
