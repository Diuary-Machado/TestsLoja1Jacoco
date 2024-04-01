package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import app.entity.Cliente;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Venda;
import app.repository.VendaRepository;

@SpringBootTest
public class VendaControllerTest {

	@Autowired
	VendaController vendaController;

	@MockBean
	VendaRepository vendaRepository;



	@Test
	@DisplayName("Teste de Integraçao para findAll")
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
	@DisplayName("Teste de Integraçao para findById")
	void testFindById() {

		Venda vendaEncontrada = new Venda();
		vendaEncontrada.setId(1L);
		vendaEncontrada.setEnderecoEntrega("Endereço"); 
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
	@DisplayName("Teste de Integraçao para delete")
	void testDelete() {

		when(this.vendaRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<String> response = vendaController.delete(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}






	@Test
	@DisplayName("Teste de integraçao para update de uma venda")
	void testUpdate() {

		Produto produto = new Produto();
		produto.setId(1L);
		produto.setNome("Produto 1");
		produto.setValor(50.0);

		Produto produto2 = new Produto();
		produto2.setId(2L);
		produto2.setNome("Produto 2");
		produto2.setValor(100.0);

		Venda vendaParaAtualizar = new Venda();
		vendaParaAtualizar.setId(1L);
		vendaParaAtualizar.setEnderecoEntrega("Novo endereço");
		vendaParaAtualizar.setValorTotal(150.0);
		vendaParaAtualizar.setStatus("Entregue");

		List<Produto> produtos = new ArrayList<>();
		produtos.add(produto);
		produtos.add(produto2);
		vendaParaAtualizar.setProduto(produtos);

		when(vendaRepository.existsById(vendaParaAtualizar.getId())).thenReturn(true);

		when(vendaRepository.save(any(Venda.class))).thenReturn(vendaParaAtualizar);

		BindingResult bindingResult = mock(BindingResult.class);

		ResponseEntity<Object> response = vendaController.update(vendaParaAtualizar.getId(), vendaParaAtualizar, bindingResult);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals("Venda atualizada com sucesso", response.getBody());
	}



	@Test
	@DisplayName("Teste de integraçao para salvar uma venda")
	void testSave() {

		Venda vendaParaSalvar = new Venda();
		vendaParaSalvar.setId(1L);
		vendaParaSalvar.setEnderecoEntrega("Endereço de entrega");
		vendaParaSalvar.setValorTotal(110.0);
		vendaParaSalvar.setStatus("Em andamento");

		List<Produto> produtos = new ArrayList<>();
		Produto produto1 = new Produto();
		produto1.setId(1L);
		produto1.setNome("Produto 1");
		produto1.setValor(50.0);
		produtos.add(produto1);
		Produto produto2 = new Produto();
		produto2.setId(2L);
		produto2.setNome("Produto 2");
		produto2.setValor(60.0);
		produtos.add(produto2);
		vendaParaSalvar.setProduto(produtos);

		when(vendaRepository.save(any(Venda.class))).thenReturn(vendaParaSalvar);

		BindingResult bindingResult = mock(BindingResult.class);

		ResponseEntity<Object> response = vendaController.save(vendaParaSalvar, bindingResult);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		assertEquals("Venda salva com sucesso", response.getBody());
	}	


}
