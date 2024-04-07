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

import app.entity.Cliente;
import app.repository.ClienteRepository;
import app.service.ClienteService;

@SpringBootTest
public class ClienteControllerTest {


	@Autowired
	ClienteController clienteController;

	@MockBean
	ClienteRepository clienteRepository;


	@Autowired
	ClienteService clienteService; 

	@Test
	@DisplayName("Teste para delete")
	void testDelete() {

		when(this.clienteRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<String> response = clienteController.delete(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}



	@Test
	@DisplayName("Teste para findAll")
	void testFindAll() {

		List<Cliente> clientes = new ArrayList<>();
		clientes.add(new Cliente());
		clientes.add(new Cliente());
		when(this.clienteRepository.findAll()).thenReturn(clientes);

		ResponseEntity<List<Cliente>> response = clienteController.findAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Cliente> lista = response.getBody();
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}



	@Test
	@DisplayName("Teste para findById")
	void testFindById() {
		Cliente clienteEncontrado = new Cliente();
		clienteEncontrado.setId(1L);
		clienteEncontrado.setNome("Joao");
		clienteEncontrado.setIdade(22);
		clienteEncontrado.setCpf("123456712357");
		clienteEncontrado.setTelefone("998989898");

		when(this.clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEncontrado));

		ResponseEntity<Object> response = clienteController.findById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(clienteEncontrado, response.getBody());
	}



	@Test
	@DisplayName("Teste para findByCpf()")
	void testFindByCpf() {
		when(clienteRepository.findByCpf("123")).thenReturn(Collections.singletonList(new Cliente()));

		ResponseEntity<List<Cliente>> response = clienteController.findByCpf("123");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Cliente> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}



	@Test
	@DisplayName("Teste para findByTelefoneCom45()")
	void testFindByTelefoneCom45() {

		when(clienteRepository.findByTelefoneCom45()).thenReturn(Collections.singletonList(new Cliente()));

		ResponseEntity<List<Cliente>> response = clienteController.findByTelefoneCom45("45919993123");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Cliente> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}



	@Test
	@DisplayName("Teste para findByNome()")
	void testFindByNome() {

		when(clienteRepository.findByNome("Joao")).thenReturn(Collections.singletonList(new Cliente()));

		ResponseEntity<List<Cliente>> response = clienteController.findByNome("Joao");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Cliente> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}


	@Test
	@DisplayName("Teste para metodo save()")
	void testSave() {
		Cliente cliente = new Cliente();
		cliente.setNome("Joao");
		cliente.setIdade(30);
		cliente.setCpf("123456789");
		cliente.setTelefone("999999999");

		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		Mockito.when(bindingResult.hasErrors()).thenReturn(false);

		ResponseEntity<Object> response = clienteController.save(cliente, bindingResult);

		String mensagem = (String) response.getBody();

		assertEquals("Cliente Salvo com Sucesso", mensagem);

		assertEquals("Joao", cliente.getNome());
		assertEquals(30, cliente.getIdade());
		assertEquals("123456789", cliente.getCpf());
		assertEquals("999999999", cliente.getTelefone());

	}

	@Test
	@DisplayName("Teste para Update de Cliente inexistente")
	void testUpdateClienteInexistente() {
		long id = 1;
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setNome("Joao");
		cliente.setIdade(30);
		cliente.setCpf("123456789");
		cliente.setTelefone("999999999");

		when(clienteService.existsById(id)).thenReturn(false);

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "cliente");

		ResponseEntity<Object> responseEntity = clienteController.update(id, cliente, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Cliente não encontrado para o ID: " + id, responseEntity.getBody());

	}


	@Test
	@DisplayName("Teste para o Update com Exceção")
	void testUpdateClienteExcecao() {
		long id = 1;
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setNome("Joao");
		cliente.setIdade(30);
		cliente.setCpf("123456789");
		cliente.setTelefone("999999999");

		when(clienteService.existsById(id)).thenReturn(true);
		when(clienteService.update(cliente, id)).thenThrow(new RuntimeException("Erro durante a atualização"));

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "cliente");

		ResponseEntity<Object> responseEntity = clienteController.update(id, cliente, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody()); 
	}




}
