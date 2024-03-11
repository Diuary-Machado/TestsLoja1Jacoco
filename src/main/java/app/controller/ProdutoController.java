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

import app.entity.Produto;
import app.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	//Salvar Dados	
	//METODO Http Requisições // http://localhost:8080/api/produto/save
	@PostMapping("/save")
	// Método para salvar um produto e verificar se há erros de validação nos dados recebidos.
	// Em caso de erro, retorna uma resposta com os erros.
	// Caso contrário, tenta salvar o produto.
	// Em caso de exceção durante o processo de salvamento, retorna uma resposta (Bad Request).
	public ResponseEntity<Object> save(@Valid @RequestBody Produto produto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			String mensagem = this.produtoService.save(produto);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Atualizar Dados
	//METODO Http Requisições // http://localhost:8080/api/produto/update/1
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody Produto produto, BindingResult bindingResult) {
		try {
			// Verifica se o produto com o ID fornecido existe no banco de dados
			if (produtoService.existsById(id)) {
				produto.setId(id); // Atribui o ID recebido ao objeto produto
				String mensagem = this.produtoService.update(produto, id); // Atualiza o produto
				// Retorna a mensagem de sucesso.
				return ResponseEntity.ok("Produto atualizado com sucesso: " + mensagem);
			} else {
				// Se o produto com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			// Em caso de exceção, retorna Menssagem!
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}


	//Deletar Dados
	//METODO Http Requisições // http://localhost:8080/api/produto/delete/1
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		// Verifica se o produto com o ID fornecido existe no banco de dados
		if (!produtoService.existsById(id)) {
			// Se o produto com o ID não existir, retorna Menssagem!
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado para o ID: " + id);
		}

		try {
			String mensagem = this.produtoService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Listar Dados
	//METODO Http Requisições // http://localhost:8080/api/produto/findAll
	@GetMapping("/findAll")
	public ResponseEntity<List<Produto>> findAll(){
		try {
			List<Produto> lista = this.produtoService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	//Procurar Dados por ID
	//METODO Http Requisições // http://localhost:8080/api/produto/findById/1	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) {
		try {
			Produto produto = this.produtoService.findById(id);
			if (produto != null) {
				return new ResponseEntity<>(produto, HttpStatus.OK);
			} else {
				// Se o produto com o ID não existir, retorna Menssagem!
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado para o ID: " + id);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
