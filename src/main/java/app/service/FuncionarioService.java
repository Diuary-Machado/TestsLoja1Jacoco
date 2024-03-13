package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public String save(Funcionario funcionario) {
		this.funcionarioRepository.save(funcionario);
		return "Funcionario Salvo com Sucesso";
	}

	public String update(Funcionario funcionario, long id) {
		funcionario.setId(id);
		this.funcionarioRepository.save(funcionario);
		return "Funcionario Atualizado com Sucesso";
	}

	public String delete(long id) {
		this.funcionarioRepository.deleteById(id);	
		return "Funcionario Deletado com Sucesso";
	}


	public List<Funcionario> findAll(){
		List<Funcionario> lista = this.funcionarioRepository.findAll();
		return lista;
	}

	public Funcionario findById(long id) {
		Funcionario funcionario= this.funcionarioRepository.findById(id).get();
		return funcionario;
	}

	public boolean existsById(long id) {
		return funcionarioRepository.existsById(id);
	}

	public List<Funcionario> findByFuncionarioNome(String nome){
		return this.funcionarioRepository.findByFuncionarioNome(nome);
	}

	public List<Funcionario> findByFuncionarioIdade(int idade){
		return this.funcionarioRepository.findByFuncionarioIdade(idade);
	}

	public List<Funcionario> findByMatriculaTresZeros(String matricula) {
		return this.funcionarioRepository.findByMatriculaTresZeros();
	}

}
