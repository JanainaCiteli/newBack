package com.alterdata.serratec.pack3.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.repository.ColaboradorRepository;
import com.alterdata.serratec.pack3.service.ColaboradorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@RestController 
@RequestMapping("/colaboradores") 
public class ColaboradorController {
	
	@Autowired 
	private ColaboradorService colaboradorService;
	
	@Autowired 
	private ColaboradorRepository colaboradorRepository;
	
	//método get 
	@GetMapping 
		@ApiOperation(value = "Retorna todos os colaboradores cadastrados", notes = "listagem de colaboradores")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna todos os colaboradores"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<List<Colaborador>> listar() {
			List<Colaborador> listaColaborador = colaboradorService.pesquisarTodos();
			return ResponseEntity.ok(listaColaborador);
	}
	
	/*@GetMapping("/teste")
	public ResponseEntity<List<Colaborador>> listar(@RequestParam String nome) {
		List<Colaborador> listaColaborador = colaboradorService.buscarNome(nome);
		return ResponseEntity.ok(listaColaborador);
}*/
	
	//método get por filtro de id 
	@GetMapping("/{id}")
		@ApiOperation(value = "Retorna colaborador cadastrado", notes = "retorna colaborador por id")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna colaborador"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<Colaborador> pesquisar(@PathVariable ("id") Long id) {
			Optional<Colaborador> colaborador = colaboradorService.pesquisarUm(id);
			if (colaborador.isPresent()) {
				return ResponseEntity.ok(colaborador.get());
			}
			return ResponseEntity.notFound().build();
		}	
	
	
	//método get por filtro de login 
			@GetMapping("/login/{login}")
			@ApiOperation(value = "Retorna colaborador cadastrado", notes = "retorna colaborador por login")
			@ApiResponses(value = {
					@ApiResponse(code = 200, message = "Retorna colaborador"),
					@ApiResponse(code = 401, message = "Erro de autenticação"),
					@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
					@ApiResponse(code = 404, message = "Recurso não encontrado"),
					@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
			})
		public ResponseEntity<List<Colaborador>> list(@RequestParam ("{login}") String login) {
			List<Colaborador> listaColaborador = colaboradorService.buscarNome(login);
			return ResponseEntity.ok(listaColaborador);
	}
	
	//método post
		@PostMapping
		@ApiOperation(value = "Cadastra colaboradores", notes = "listagem de colaboradores")
		@ApiResponses(value = {
				@ApiResponse(code = 201, message = "Colaborador adicionado"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		@ResponseStatus(HttpStatus.CREATED) 
		public Colaborador inserir(@Valid @RequestBody Colaborador colaborador) {
			return colaboradorService.inserir(colaborador);
		}		

		//método put por filtro de id144
		@PutMapping("/{id}")
		@ApiOperation(value = "Atualiza colaborador cadastrado", notes = "atualizar colaboradores")
		@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Colaborador atualizado"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
	})
		public ResponseEntity<Colaborador> atualizar(@Valid @RequestBody Colaborador colaborador, @PathVariable Long id) {
			if (!colaboradorService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}
			colaborador.setIdColaborador(id);
			colaborador = colaboradorService.inserir(colaborador);
			return ResponseEntity.ok(colaborador);
		}	
		
			
	//método delete
		@DeleteMapping("/{id}")
		@ApiOperation(value = "Deletar colaborador por id", notes = "deletar colaborador")
		@ApiResponses(value = { 
				@ApiResponse(code = 200, message = "Colaborador removido"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 500, message = "Erro interno do servidor"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
		public ResponseEntity<String> remover(@Valid @PathVariable Long id) {
			if (!colaboradorService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}		
			colaboradorService.remover(id);
			return ResponseEntity.ok("Colaborador removido");		
		}	
	
	

}

