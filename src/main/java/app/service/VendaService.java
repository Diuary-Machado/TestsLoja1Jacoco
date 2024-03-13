package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Venda;
import app.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	public String save(Venda venda) {
		this.vendaRepository.save(venda);
		return "Venda Salva com Sucesso";
	}

	public String update(Venda venda, long id) {
		venda.setId(id);
		this.vendaRepository.save(venda);
		return "Venda Atualizada com Sucesso";
	}


	public String delete(long id) {
		this.vendaRepository.deleteById(id);	
		return "Venda Deletada com Sucesso";
	}


	public List<Venda> findAll(){
		List<Venda> lista = this.vendaRepository.findAll();
		return lista;
	}

	public Venda findById(long id) {
		Venda venda = this.vendaRepository.findById(id).get();
		return venda;
	}

	public boolean existsById(long id) {
		return vendaRepository.existsById(id);
	}

	
	public List<Venda> findByClienteNome(String nome){
		return this.vendaRepository.findByClienteNome(nome);
	}
	
	public List<Venda> findByFuncionarioMatricula(String matricula){
		return this.vendaRepository.findByFuncionarioMatricula(matricula);
	}
	
	public List<Venda> findyByEndereco(String enderecoEntrega){
		return this.vendaRepository.findyByEndereco(enderecoEntrega);
	}
	
}
