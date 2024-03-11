package app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired 
	private ClienteService clienteService;


	//Salvar Dados	
	//METODO Http Requisições // http://localhost:8080/api/cliente/save
	@PostMapping("/save")
	// Método para salvar um cliente e verificar se há erros de validação nos dados recebidos.
	// Em caso de erro, retorna uma resposta com os erros.
	// Caso contrário, tenta salvar o cliente.
	// Em caso de exceção durante o processo de salvamento, retorna uma resposta (Bad Request).
	public ResponseEntity<Object> save(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			String mensagem = this.clienteService.save(cliente);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Atualizar Dados
	//METODO Http Requisições // http://localhost:8080/api/cliente/update/1
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
		try {
			// Verifica se o cliente com o ID fornecido existe no banco de dados
			if (clienteService.existsById(id)) {
				cliente.setId(id); // Atribui o ID recebido ao objeto cliente
				String mensagem = this.clienteService.update(cliente, id); // Atualiza o cliente
				// Retorna a mensagem de sucesso.
				return ResponseEntity.ok("Cliente atualizado com sucesso: " + mensagem);
			} else {
				// Se o cliente com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			// Em caso de exceção, retorna Menssagem!
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}


	//Deletar Dados
	//METODO Http Requisições // http://localhost:8080/api/cliente/delete/1
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		// Verifica se o cliente com o ID fornecido existe no banco de dados
		if (!clienteService.existsById(id)) {
			// Se o cliente com o ID não existir, retorna Menssagem!
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado para o ID: " + id);
		}

		try {
			String mensagem = this.clienteService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Listar Dados
	//METODO Http Requisições // http://localhost:8080/api/cliente/findAll
	@GetMapping("/findAll")
	public ResponseEntity<List<Cliente>> findAll(){
		try {
			List<Cliente> lista = this.clienteService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Procurar Dados por ID
	//METODO Http Requisições // http://localhost:8080/api/cliente/findById/1
	@GetMapping("/findById/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) {
		try {
			Cliente cliente = this.clienteService.findById(id);
			if (cliente != null) {
				return new ResponseEntity<>(cliente, HttpStatus.OK);
			} else {
				// Se o cliente com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
