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

import app.entity.Produto;
import app.repository.ProdutoRepository;
import app.service.ProdutoService;

@SpringBootTest
public class ProdutoControllerTest {



	@Autowired
	ProdutoController produtoController;

	@MockBean
	ProdutoRepository produtoRepository;



	@Autowired
	ProdutoService produtoService; 


	@Test
	@DisplayName("Teste para delete")
	void testDelete() {

		when(this.produtoRepository.existsById(1L)).thenReturn(true);

		ResponseEntity<String> response = produtoController.delete(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}



	@Test
	@DisplayName("Teste para findAll")
	void testFindAll() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(new Produto());
		produtos.add(new Produto());

		when(produtoRepository.findAll()).thenReturn(produtos);

		ResponseEntity<List<Produto>> response = produtoController.findAll();

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Produto> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(2, lista.size());
	}




	@Test
	@DisplayName("Teste para findById")
	void testFindById() {
		Produto produtoEncontrado = new Produto();
		produtoEncontrado.setId(1L);
		produtoEncontrado.setNome("Cafe");
		produtoEncontrado.setValor(20.0);
		produtoEncontrado.setMarca("Nestle");

		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEncontrado));

		ResponseEntity<Object> response = produtoController.findById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(produtoEncontrado, response.getBody());
	}



	@Test
	@DisplayName("Teste para findByNome()")
	void testFindByNome() {
		when(produtoRepository.findByNome("Doritos")).thenReturn(Collections.singletonList(new Produto()));

		ResponseEntity<List<Produto>> response = produtoController.findByNome("Doritos");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Produto> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}



	@Test
	@DisplayName("Teste para findByMarca()")
	void testFindByMarca() {
		when(produtoRepository.findByMarca("Dove")).thenReturn(Collections.singletonList(new Produto()));

		ResponseEntity<List<Produto>> response = produtoController.findByMarca("Dove");

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Produto> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}



	@Test
	@DisplayName("Teste para findByValor()")
	void testFindByValor() {
		when(produtoRepository.findByValor(50.0)).thenReturn(Collections.singletonList(new Produto()));

		ResponseEntity<List<Produto>> response = produtoController.findByValor(50.0);

		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Produto> lista = response.getBody();
		assertNotNull(lista);

		assertEquals(1, lista.size());
	}


	@Test
	@DisplayName("Teste para save()")
	void testSave() {
		Produto produto = new Produto();
		produto.setNome("Cookies");
		produto.setValor(10.0);
		produto.setMarca("Balduco");

		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		Mockito.when(bindingResult.hasErrors()).thenReturn(false);

		ResponseEntity<Object> response = produtoController.save(produto, bindingResult);

		String mensagem = (String) response.getBody();

		assertEquals("Produto Salvo com Sucesso", mensagem);

		assertEquals("Cookies", produto.getNome());
		assertEquals(10.0, produto.getValor());
		assertEquals("Balduco", produto.getMarca());
	}



	@Test
	@DisplayName("Teste para Update com Produto inexistente")
	void testUpdateProdutoInexistente() {
		long id = 1;
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome("Rufles");
		produto.setValor(12.0);
		produto.setMarca("Helma Chips");

		when(produtoService.existsById(id)).thenReturn(false);

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "produto");

		ResponseEntity<Object> responseEntity = produtoController.update(id, produto, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Produto não encontrado para o ID: " + id, responseEntity.getBody());
	}


	@Test
	@DisplayName("Teste para Update com Exceção")
	void testUpdateProdutoExcecao() {
		long id = 1;
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome("Rufles");
		produto.setValor(12.0);
		produto.setMarca("Helma Chips");

		when(produtoService.existsById(id)).thenReturn(true);
		when(produtoService.update(produto, id)).thenThrow(new RuntimeException("Erro durante a atualização"));

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "produto");

		ResponseEntity<Object> responseEntity = produtoController.update(id, produto, bindingResult);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());
	}

}
