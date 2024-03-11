package app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull(message = "O campo Nome não pode ser nulo")
	@NotBlank(message = "O campo Nome não pode estar em branco")
	private String nome;
	@NotNull
	@Positive(message = "A Idade deve ser um número positivo")
	private int idade;
	@NotNull(message = "O campo Matricula não pode ser nulo")
	private String matricula;


	/**
	 * Relacionamento de "um para muitos" (One-to-Many) entre a entidade atual e a entidade Venda.
	 * Cada instância desta entidade pode estar associada a várias instâncias da entidade Venda,
	 * onde o atributo "funcionario" na entidade Venda faz referência a este relacionamento.
	 */

	@OneToMany(mappedBy = "funcionario")
	private List<Venda> vendas;





}
