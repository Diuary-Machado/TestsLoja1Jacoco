package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
import app.service.FuncionarioService;

@SpringBootTest
public class FuncionarioControllerTest {



	@Autowired
	FuncionarioController funcionarioController;

	@MockBean
	FuncionarioRepository funcionarioRepository;

	@Autowired
	FuncionarioService funcionarioService; 

	@Test
	@DisplayName("Teste para delete")
	void testDelete() {

		when(this.funcionarioRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<String> response = funcionarioController.delete(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}


	@Test
	@DisplayName("Teste para findAll")
	void testFindAll() {

		List<Funcionario> funcionarios = new ArrayList<>();
		funcionarios.add(new Funcionario());
		funcionarios.add(new Funcionario());
		when(this.funcionarioRepository.findAll()).thenReturn(funcionarios);

		ResponseEntity<List<Funcionario>> response = funcionarioController.findAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Funcionario> lista = response.getBody();
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}


	@Test
	@DisplayName("Teste para findById")
	void testFindById() {
		Funcionario funcionarioEncontrado = new Funcionario();
		funcionarioEncontrado.setId(1L);
		funcionarioEncontrado.setNome("Pedro");
		funcionarioEncontrado.setIdade(20);
		funcionarioEncontrado.setMatricula("120003456");

		when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioEncontrado));

		ResponseEntity<Object> response = funcionarioController.findById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(funcionarioEncontrado, response.getBody());
	}



	@Test
	@DisplayName("Teste para findByNome()")
	void testFindByNome() {
		when(funcionarioRepository.findByNome("Pedro")).thenReturn(Collections.singletonList(new Funcionario()));

		ResponseEntity<List<Funcionario>> response = funcionarioController.findByNome("Pedro");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Funcionario> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste para findByIdade()")
	void testFindByIdade() {
		when(funcionarioRepository.findByIdade(20)).thenReturn(Collections.singletonList(new Funcionario()));

		ResponseEntity<List<Funcionario>> response = funcionarioController.findByIdade(20);

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Funcionario> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}


	@Test
	@DisplayName("Teste para findByMatriculaTresZeros()")
	void testFindByMatriculaTresZeros() {
		when(funcionarioRepository.findByMatriculaTresZeros()).thenReturn(Collections.singletonList(new Funcionario()));

		ResponseEntity<List<Funcionario>> response = funcionarioController.findByMatriculaTresZeros("000");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Funcionario> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste para save()")
	void testSave() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Pedro");
		funcionario.setIdade(20);
		funcionario.setMatricula("123450006");

		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		Mockito.when(bindingResult.hasErrors()).thenReturn(false);

		ResponseEntity<Object> response = funcionarioController.save(funcionario, bindingResult);

		String mensagem = (String) response.getBody();

		assertEquals("Funcionario Salvo com Sucesso", mensagem);

		assertEquals("Pedro", funcionario.getNome());
		assertEquals(20, funcionario.getIdade());
		assertEquals("123450006", funcionario.getMatricula());
	}


	@Test
	@DisplayName("Teste para Update de Funcionario inexistente")
	void testUpdateFuncionarioInexistente() {
		long id = 1;
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome("Pedro");
		funcionario.setIdade(25);
		funcionario.setMatricula("123456789");

		when(funcionarioService.existsById(id)).thenReturn(false);

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "funcionario");

		ResponseEntity<Object> responseEntity = funcionarioController.update(id, funcionario, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Funcionário não encontrado para o ID: " + id, responseEntity.getBody());
	}


	@Test
	@DisplayName("Teste para Update de Funcionario com Exceção")
	void testUpdateFuncionarioExcecao() {
		long id = 1;
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome("Miguel ");
		funcionario.setIdade(26);
		funcionario.setMatricula("F523045");

		when(funcionarioService.existsById(id)).thenReturn(true);
		when(funcionarioService.update(funcionario, id)).thenThrow(new RuntimeException("Erro durante a atualização"));

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "funcionario");

		ResponseEntity<Object> responseEntity = funcionarioController.update(id, funcionario, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody()); 
	}


}
