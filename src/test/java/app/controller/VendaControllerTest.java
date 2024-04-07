package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import app.entity.Cliente;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;
import app.service.VendaService;

@SpringBootTest
public class VendaControllerTest {

	@Autowired
	VendaController vendaController;

	@MockBean
	VendaRepository vendaRepository;

	@Autowired
	VendaService vendaService; 



	@Test
	@DisplayName("Teste para findAll")
	void testFindAll() {

		List<Venda> vendas = new ArrayList<>();
		vendas.add(new Venda());
		vendas.add(new Venda());
		when(this.vendaRepository.findAll()).thenReturn(vendas);

		ResponseEntity<List<Venda>> response = vendaController.findAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Venda> lista = response.getBody();
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}



	@Test
	@DisplayName("Teste para findById")
	void testFindById() {

		Venda vendaEncontrada = new Venda();
		vendaEncontrada.setId(1L);
		vendaEncontrada.setEnderecoEntrega("Jardim das Flores"); 
		vendaEncontrada.setValorTotal(0.0);
		vendaEncontrada.setStatus("Em andamento");
		vendaEncontrada.setCliente(new Cliente()); 
		vendaEncontrada.setFuncionario(new Funcionario()); 

		when(this.vendaRepository.findById(1L)).thenReturn(Optional.of(vendaEncontrada));

		ResponseEntity<Object> response = vendaController.findById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(vendaEncontrada, response.getBody());
	}



	@Test
	@DisplayName("Teste para findById com Venda existente")
	void testFindByIdVendaExistente() {
		List<Venda> list = new ArrayList<>();
		Venda venda = new Venda();
		venda.setId(1L);
		venda.setValorTotal(99.99);
		list.add(venda);

		when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

		ResponseEntity<Object> responseEntity = vendaController .findById(1L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(venda, responseEntity.getBody());
	}




	@Test
	@DisplayName("Teste para delete")
	void testDelete() {

		when(this.vendaRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<String> response = vendaController.delete(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}




	@Test
	@DisplayName("Teste para findByClienteNome()")
	void testFindByClienteNome() {
		when(vendaRepository.findByClienteNome("Marco")).thenReturn(Collections.singletonList(new Venda()));

		ResponseEntity<List<Venda>> response = vendaController.findByClienteNome("Marco");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Venda> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}




	@Test
	@DisplayName("Teste para findByFuncionarioMatricula()")
	void testFindByFuncionarioMatricula() {
		when(vendaRepository.findByFuncionarioMatricula("000")).thenReturn(Collections.singletonList(new Venda()));

		ResponseEntity<List<Venda>> response = vendaController.findByFuncionarioMatricula("000");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Venda> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}


	@Test
	@DisplayName("Teste para findByEndereco()")
	void testFindByEndereco() {
		when(vendaRepository.findyByEndereco("Jardim das Flores")).thenReturn(Collections.singletonList(new Venda()));

		ResponseEntity<List<Venda>> response = vendaController.findyByEndereco("Jardim das Flores");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Venda> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}


	@Test
	@DisplayName("Teste para Save de uma Venda")
	void testSave() {
		Venda venda = new Venda();
		venda.setId(1L); 
		venda.setEnderecoEntrega("Rua Capitao");
		venda.setValorTotal(45.00); 
		venda.setStatus("Pendente");

		Cliente cliente = new Cliente();
		cliente.setNome("Aurelio");
		cliente.setCpf("1230540213");
		cliente.setIdade(21);
		cliente.setTelefone("4599874535");
		cliente.setId(1L); 

		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Afonso");
		funcionario.setIdade(35);
		funcionario.setMatricula("F2904500");
		funcionario.setId(1L); 

		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);

		List<Produto> produtos = new ArrayList<>();

		Produto produto = new Produto();
		produto.setNome("Detergente Premium");
		produto.setValor(45.00);
		produto.setMarca("LimpaMais");
		produto.setId(1L); 

		produtos.add(produto);

		venda.setProduto(produtos);


		ResponseEntity<Object> response = vendaController.save(venda, mock(BindingResult.class));
		String mensagem = (String) response.getBody();

		assertEquals("Venda Salva com Sucesso", mensagem);

		assertEquals("Rua Capitao", venda.getEnderecoEntrega());
		assertEquals(45.00, venda.getValorTotal());
		assertEquals("Pendente", venda.getStatus());
		assertEquals(cliente, venda.getCliente());
		assertEquals(funcionario, venda.getFuncionario());
		assertEquals(produtos, venda.getProduto());
	}



	@Test
	@DisplayName("Teste para update com Venda inexistente")
	void testUpdateVendaInexistente() {
		long vendaId = 1;
		Venda venda = new Venda();
		venda.setId(vendaId);
		venda.setId(1L); 
		venda.setEnderecoEntrega("Rua Marin");
		venda.setValorTotal(100.00); 
		venda.setStatus("Pendente");


		when(vendaService.existsById(vendaId)).thenReturn(false);

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "venda");

		ResponseEntity<Object> responseEntity = vendaController.update(vendaId, venda, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Venda não encontrada para o ID: " + vendaId, responseEntity.getBody());
	}



	@Test
	@DisplayName("Teste para Update de Venda com Exceção")
	void testUpdateVendaComExcecao() {
		long vendaId = 1;
		Venda venda = new Venda();
		venda.setId(vendaId);
		venda.setEnderecoEntrega("Jardim Kalo");
		venda.setValorTotal(70.00); 
		venda.setStatus("CANCELADO");

		List<Produto> produtos = new ArrayList<>();

		Produto produto = new Produto();
		produto.setNome("Papel Higienico");
		produto.setValor(35.00);
		produto.setMarca("LimpaMais");
		produto.setId(1L); 

		produtos.add(produto);

		venda.setProduto(produtos);

		when(vendaService.existsById(vendaId)).thenReturn(true);
		when(vendaService.update(venda, vendaId)).thenThrow(new RuntimeException("Erro durante a atualização"));

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "funcionario");

		ResponseEntity<Object> responseEntity = vendaController.update(vendaId, venda, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());
	}



}
