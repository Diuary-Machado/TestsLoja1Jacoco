package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda {

	/**
	 * A entidade Venda representa uma transação de venda no sistema. Cada venda
	 * está associada a um único cliente que realizou a compra e a um funcionário
	 * que efetuou a venda. Além disso, uma venda pode conter vários produtos.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String enderecoEntrega;
	@NotNull(message = "O campo ValorTotal não pode ser nulo")
	private double valorTotal;


	/**
	 * Um cliente pode estar vinculado a várias vendas, o que representa múltiplas
	 * compras realizadas pelo mesmo cliente ao longo do tempo.
	 */




	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("vendas")
	@JoinColumn(name = "cliente_id")
	private Cliente cliente; 


	/**
	 * Um funcionário pode realizar várias vendas, representando várias transações
	 * de venda realizadas por esse funcionário.
	 */

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("vendas")
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;


	/**
	 * Uma venda pode conter múltiplos produtos, e um produto pode estar presente em
	 * várias vendas diferentes.
	 */

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("vendas")
	@JoinTable(name = "venda_produto")
	private List<Produto> produto;




}
