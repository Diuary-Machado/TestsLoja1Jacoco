package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.entity.Produto;
import app.entity.Venda;

@SpringBootTest
public class VendaServiceTest {

	@Autowired
	VendaService vendaService;

	@Test
	@DisplayName("Teste unitario de calculo de valor total retornado")
	void testCalcularValorTotal() {

		Produto produto1 = new Produto();
		produto1.setValor(10.0);

		Produto produto2 = new Produto();
		produto2.setValor(20.0);

		List<Produto> produtos = Arrays.asList(produto1, produto2);

		double valorTotal = vendaService.calcularValorTotal(produtos);

		assertEquals(30.0, valorTotal);
	}

	@Test
	@DisplayName("Teste unitario de calculo de valor total com lista vazia")
	void testCalcularValorTotalListaVazia() {

		List<Produto> produtos = Arrays.asList();

		assertThrows(IllegalArgumentException.class, () -> vendaService.calcularValorTotal(produtos));
	}



	@Test
	@DisplayName("Teste unitario para verificar status Cancelado")
	void testVerificarStatusCancelado() {
		Venda venda = new Venda();
		venda.setStatus("CANCELADO");
		venda.setProduto(new ArrayList<>());

		vendaService.verificarStatus(venda);

		assertNotNull(venda.getProduto());

		assertEquals(0.0, venda.getValorTotal());
	}



	@Test
	@DisplayName("Teste unitario para verificar Exception de Validaçao com status cancelado e lista de produtos nula ")
	void testStatusCanceladoEListaProdutosNula() {

		Venda venda = new Venda();
		venda.setStatus("CANCELADO");
		venda.setProduto(null); 

		assertThrows(IllegalArgumentException.class, () -> vendaService.verificarStatus(venda));
	}

}
