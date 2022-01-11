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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alterdata.serratec.pack3.domain.Compensacao;
import com.alterdata.serratec.pack3.repository.CompensacaoRepository;
import com.alterdata.serratec.pack3.service.CompensacaoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/compensacao") 
public class CompensacaoController {
	
	@Autowired 
	private CompensacaoService compensacaoService;
	
	@Autowired 
	private CompensacaoRepository compensacaoRepository;
	
	//método get 
	@GetMapping 
		@ApiOperation(value = "Retorna todas as compensações cadastradas", notes = "listagem de compensações")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna todos os colaboradores"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<List<Compensacao>> listar() {
			List<Compensacao> listaCompensacao = compensacaoService.pesquisarTodos();
			return ResponseEntity.ok(listaCompensacao);
	}
	
			
	//método get por filtro de id 
	@GetMapping("/{id}")
		@ApiOperation(value = "Retorna compensação cadastrada", notes = "retorna compensação por id")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna colaborador"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<Compensacao> pesquisar(@PathVariable Long id) {
			Optional<Compensacao> compensacao = compensacaoService.pesquisarUm(id);
			if (compensacao.isPresent()) {
				return ResponseEntity.ok(compensacao.get());
			}
			return ResponseEntity.notFound().build();
		}	
	
	
	//método post
		@PostMapping
		@ApiOperation(value = "Cadastra compensações", notes = "listagem de compensações")
		@ApiResponses(value = {
				@ApiResponse(code = 201, message = "Colaborador adicionado"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		@ResponseStatus(HttpStatus.CREATED) 
		public Compensacao inserir(@Valid @RequestBody Compensacao compensacao) {
			return compensacaoService.inserir(compensacao);
		}		

		//método put por filtro de id
		@PutMapping("/{id}")
		@ApiOperation(value = "Atualiza compensação cadastrada", notes = "atualizar compensação")
		@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Compensação atualizada"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
	})
		public ResponseEntity<Compensacao> atualizar(@Valid @RequestBody Compensacao compensacao, @PathVariable Long id) {
			if (!compensacaoService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}
			compensacao.setIdCompensacao(id);
			compensacao = compensacaoService.inserir(compensacao);
			return ResponseEntity.ok(compensacao);
		}	
		
			
	//método delete
	//não é lógico
		@DeleteMapping("/{id}")
		@ApiOperation(value = "Deletar compensação por id", notes = "Deletar compensação")
		@ApiResponses(value = { 
				@ApiResponse(code = 200, message = "Compensação removida"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 500, message = "Erro interno do servidor"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
		public ResponseEntity<String> remover(@Valid @PathVariable Long id) {
			if (!compensacaoService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}		
			compensacaoService.remover(id);
			return ResponseEntity.ok("Compensação removida");		
		}	
	
	

}

