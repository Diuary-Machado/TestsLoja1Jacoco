package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
public class ValidationErrorControllerTest {

	@Autowired
	ValidationErrorController validationErrorController;




	@Test
	@DisplayName("Teste 1")
	void testControllerValidationNotNull() {
		assertNotNull(validationErrorController);
	}


	@Test
	@DisplayName("Teste 2")
	void testDeValidaçoesErrosComExcecoes() {
		BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");

		ResponseEntity<Object> responseEntity = validationErrorController.handleValidationExceptions(
				new MethodArgumentNotValidException(null, bindingResult));

		assertNotNull(responseEntity);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		assertEquals(0, ((Map<?, ?>) responseEntity.getBody()).size());
	}



}
