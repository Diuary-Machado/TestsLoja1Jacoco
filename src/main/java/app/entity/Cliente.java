package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
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
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull(message = "O campo Nome não pode ser nulo")
	private String nome;
	@NotNull
	@Length(max = 11, message = "O CPF deve ter no máximo 11 caracteres")
	private String cpf;
	@NotNull
	@Positive(message = "A idade deve ser um número positivo")
	private int idade;
	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "O telefone deve conter apenas números")
	private String telefone;


	/**
	 * Relacionamento de "um para muitos" (One-to-Many) entre a entidade atual e a entidade Cliente.
	 * Cada instância desta entidade pode estar associada a várias instâncias da entidade Cliente.
	 * O atributo "mappedBy" especifica o nome do campo na entidade Cliente que faz referência a este relacionamento.
	 */

	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente") 
	private List<Venda> vendas;



}
