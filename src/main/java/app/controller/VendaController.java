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

import app.entity.Venda;
import app.service.VendaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	//Salvar Dados	
	//METODO Http Requisições // http://localhost:8080/api/venda/save
	@PostMapping("/save")
	// Método para salvar uma venda e verificar se há erros de validação nos dados recebidos.
	// Em caso de erro, retorna uma resposta com os erros.
	// Caso contrário, tenta salvar a venda.
	// Em caso de exceção durante o processo de salvamento, retorna uma resposta (Bad Request).
	public ResponseEntity<Object> save(@Valid @RequestBody Venda venda, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			String mensagem = this.vendaService.save(venda);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Atualizar Dados
	//METODO Http Requisições // http://localhost:8080/api/venda/update/1
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody Venda venda, BindingResult bindingResult) {
		try {
			// Verifica se a venda com o ID fornecido existe no banco de dados
			if (vendaService.existsById(id)) {
				venda.setId(id); // Atribui o ID recebido ao objeto venda
				String mensagem = this.vendaService.update(venda, id); // Atualiza a venda
				// Retorna a mensagem de sucesso.
				return ResponseEntity.ok("Venda atualizada com sucesso: " + mensagem);
			} else {
				// Se a venda com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada para o ID: " + id);
			}
		} catch (Exception e) {
			// Em caso de exceção, retorna Menssagem!
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Deletar Dados
	//METODO Http Requisições // http://localhost:8080/api/venda/delete/1
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		// Verifica se a venda com o ID fornecido existe no banco de dados
		if (!vendaService.existsById(id)) {
			// Se a venda com o ID não existir, retorna Menssagem!
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada para o ID: " + id);
		}

		try {
			String mensagem = this.vendaService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Listar Dados
	//METODO Http Requisições // http://localhost:8080/api/venda/findAll
	@GetMapping("/findAll")
	public ResponseEntity<List<Venda>> findAll(){
		try {
			List<Venda> lista = this.vendaService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}


	//Procurar Dados por ID
	//METODO Http Requisições // http://localhost:8080/api/venda/findById/1
	@GetMapping("/findById/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) {
		try {
			Venda venda = this.vendaService.findById(id);
			if (venda != null) {
				return new ResponseEntity<>(venda, HttpStatus.OK);
			} else {
				// Se a venda com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada para o ID: " + id);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findByVendaClienteNome")
	public ResponseEntity<List<Venda>> findByClienteNome (@RequestParam String nome){

		try {

			List<Venda> lista = this.vendaService.findByClienteNome(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
	
	@GetMapping("/findByVendaFuncionarioMatricula")
	public ResponseEntity<List<Venda>> findByFuncionarioMatricula (@RequestParam String matricula){

		try {

			List<Venda> lista = this.vendaService.findByFuncionarioMatricula(matricula);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
	
	
	@GetMapping("/findyByEndereco")
	public ResponseEntity<List<Venda>> findyByEndereco (@RequestParam String enderecoEntrega){

		try {

			List<Venda> lista = this.vendaService.findyByEndereco(enderecoEntrega);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}
	
}
