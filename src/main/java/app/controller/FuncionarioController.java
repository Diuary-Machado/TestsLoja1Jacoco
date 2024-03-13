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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Funcionario;
import app.service.FuncionarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	//Salvar Dados	
	//METODO Http Requisições // http://localhost:8080/api/funcionario/save
	@PostMapping("/save")
	// Método para salvar um funcionario e verificar se há erros de validação nos dados recebidos.
	// Em caso de erro, retorna uma resposta com os erros.
	// Caso contrário, tenta salvar o funcionario.
	// Em caso de exceção durante o processo de salvamento, retorna uma resposta (Bad Request).
	public ResponseEntity<Object> save(@Valid @RequestBody Funcionario funcionario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			String mensagem = this.funcionarioService.save(funcionario);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Atualizar Dados
	//METODO Http Requisições // http://localhost:8080/api/funcionario/update/1
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody Funcionario funcionario, BindingResult bindingResult) {
		try {
			// Verifica se o funcionário com o ID fornecido existe no banco de dados
			if (funcionarioService.existsById(id)) {
				funcionario.setId(id); // Atribui o ID recebido ao objeto funcionário
				String mensagem = this.funcionarioService.update(funcionario, id); // Atualiza o funcionário
				// Retorna a mensagem de sucesso.
				return ResponseEntity.ok("Funcionário atualizado com sucesso: " + mensagem);
			} else {
				// Se o funcionario com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			// Em caso de exceção, retorna Menssagem!
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Deletar Dados
	//METODO Http Requisições // http://localhost:8080/api/funcionario/delete/1
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		// Verifica se o funcionário com o ID fornecido existe no banco de dados
		if (!funcionarioService.existsById(id)) {
			// Se o funcionario com o ID não existir, retorna Menssagem!
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado para o ID: " + id);
		}

		try {
			String mensagem = this.funcionarioService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Listar Dados
	//METODO Http Requisições // http://localhost:8080/api/funcionario/findAll
	@GetMapping("/findAll")
	public ResponseEntity<List<Funcionario>> findAll(){
		try {
			List<Funcionario> lista = this.funcionarioService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	//Procurar Dados por ID
	//METODO Http Requisições // http://localhost:8080/api/funcionario/findById/1	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) {
		try {
			Funcionario funcionario = this.funcionarioService.findById(id);
			if (funcionario != null) {
				return new ResponseEntity<>(funcionario, HttpStatus.OK);
			} else {
				// Se o funcionario com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findByFuncionarioNome")
	public ResponseEntity<List<Funcionario>> findByNome (@RequestParam String nome){

		try {

			List<Funcionario> lista = this.funcionarioService.findByNome(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}


	@GetMapping("/findByFuncionarioIdade")
	public ResponseEntity<List<Funcionario>> findByIdade (@RequestParam int idade){

		try {

			List<Funcionario> lista = this.funcionarioService.findByIdade(idade);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}


	@GetMapping("/findByMatriculaTresZeros")
	public ResponseEntity<List<Funcionario>> findByMatriculaTresZeros (@RequestParam String matricula){

		try {

			List<Funcionario> lista = this.funcionarioService.findByMatriculaTresZeros(matricula);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
}
