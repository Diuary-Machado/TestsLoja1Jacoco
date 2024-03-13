package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produto;
import app.repository.ProdutoRepository;

@Service
public class ProdutoService {


	@Autowired
	private ProdutoRepository produtoRepository;

	public String save(Produto produto) {
		this.produtoRepository.save(produto);
		return "Produto Salvo com Sucesso";
	}

	public String update(Produto produto, long id) {
		produto.setId(id);
		this.produtoRepository.save(produto);
		return "Produto Atualizado com Sucesso";
	}


	public String delete(long id) {
		this.produtoRepository.deleteById(id);	
		return "Produto Deletado com Sucesso";
	}

	public List<Produto> findAll(){
		List<Produto> lista = this.produtoRepository.findAll();
		return lista;
	}

	public Produto findById(long id) {
		Produto produto= this.produtoRepository.findById(id).get();
		return produto;
	}
	public boolean existsById(long id) {
		return produtoRepository.existsById(id);
	}

	public List<Produto> findByProdutoNome(String nome){
		return this.produtoRepository.findByProdutoNome(nome);
	}
	
	public List<Produto> findByProdutoMarca(String marca){
		return this.produtoRepository.findByProdutoMarca(marca);
	}

	public List<Produto> findByValorA200(double valor){
		return this.produtoRepository.findByValorA200();
	}

}
