package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	public List<Venda> findByClienteNome(String nome);

	public List<Venda> findByFuncionarioMatricula(String matricula);


	@Query("FROM Venda v WHERE v.enderecoEntrega = :endereco")
	public List<Venda> buscarPorEndereco(String endereco);

}
