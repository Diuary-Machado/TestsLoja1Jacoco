package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{


	public List<Funcionario> findByFuncionarioNome(String nome);

	public List<Funcionario> findByFuncionarioIdade(int idade);

	@Query("FROM Funcionario f WHERE f.matricula LIKE '%000%'")
	List<Funcionario> findByMatriculaTresZeros(); 
}
