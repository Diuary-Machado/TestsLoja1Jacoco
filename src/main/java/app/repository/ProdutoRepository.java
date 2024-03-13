package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	public List<Produto> findByProdutoNome(String nome);

	public List<Produto> findByProdutoMarca(String marca);
	
	@Query("FROM Produto p WHERE p.valor <= 200")
	List<Produto> findByValorA200(); 
	


}
