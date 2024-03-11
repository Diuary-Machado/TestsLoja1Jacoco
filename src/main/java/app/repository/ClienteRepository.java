package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{


	public List<Cliente> findByClienteNome(String nome);

	public List<Cliente> findByClienteCpf(String cpf);

	@Query("FROM Cliente c WHERE c.telefone LIKE '45%'")
	List<Cliente> findByTelefoneCom45(String telefone);
}
