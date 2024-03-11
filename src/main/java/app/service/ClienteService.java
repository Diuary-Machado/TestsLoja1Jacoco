package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.repository.ClienteRepository;

@Service
public class ClienteService {


	@Autowired
	private ClienteRepository clienteRepository;

	public String save(Cliente cliente) {
		this.clienteRepository.save(cliente);
		return "Cliente Salvo com Sucesso";
	}

	public String update(Cliente cliente, long id) {
		cliente.setId(id);
		this.clienteRepository.save(cliente);
		return "Cliente Atualizado com Sucesso";
	}


	public String delete(long id) {
		this.clienteRepository.deleteById(id);	
		return "Cliente Deletado com Sucesso";
	}

	public List<Cliente> findAll(){
		List<Cliente> lista = this.clienteRepository.findAll();
		return lista;
	}


	public Cliente findById(long id) {
		Cliente cliente = this.clienteRepository.findById(id).get();
		return cliente;
	}

	public boolean existsById(long id) {
		return clienteRepository.existsById(id);
	}


	public List<Cliente> findByClienteNome(String nome){
		return this.clienteRepository.findByClienteNome(nome);
	}

	public List<Cliente> findByClienteCpf(String cpf){
		return this.clienteRepository.findByClienteCpf(cpf);
	}

	public List<Cliente> findByTelefoneCom45(String telefone){
		return this.clienteRepository.findByTelefoneCom45(telefone);
	}

}
