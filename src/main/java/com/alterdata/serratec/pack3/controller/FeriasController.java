package com.alterdata.serratec.pack3.controller;

import java.time.LocalDate;
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

import com.alterdata.serratec.pack3.domain.Ferias;
import com.alterdata.serratec.pack3.service.FeriasService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/ferias") 
public class FeriasController {
	
	@Autowired 
	private FeriasService feriasService;
	
	//método get
	@GetMapping 
		@ApiOperation(value = "Retorna todas as férias programadas", notes = "listagem de times")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna todas as férias programadas"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<List<Ferias>> listar() {
			List<Ferias> listaFerias = feriasService.pesquisarTodos();
			return ResponseEntity.ok(listaFerias);
			
	}
	

	
	//método get por filtro de id 
	@GetMapping("/{id}")
		@ApiOperation(value = "Retorna férias programadas", notes = "retorna férias programadas por id")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna férias programadas"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<Ferias> pesquisar(@PathVariable Long id) {
			Optional<Ferias> ferias = feriasService.pesquisarUm(id);
			if (ferias.isPresent()) {
				return ResponseEntity.ok(ferias.get());
			}
			return ResponseEntity.notFound().build();
		}	
	
	
	//método post
		@PostMapping
		@ApiOperation(value = "Cadastra férias", notes = "listagem de férias")
		@ApiResponses(value = {
				@ApiResponse(code = 201, message = "Férias adicionadas"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		@ResponseStatus(HttpStatus.CREATED) 
		public Ferias inserir(@Valid @RequestBody Ferias ferias) {
			return feriasService.inserir(ferias);
		}		

		//método put por filtro de id
		@PutMapping("/{id}")
		@ApiOperation(value = "Atualiza férias programadas cadastradas", notes = "atualiza férias")
		@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Férias atualizadas"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
	})
		public ResponseEntity<Ferias> atualizar(@Valid @RequestBody Ferias ferias, @PathVariable Long id) {
			if (!feriasService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}
			ferias.setIdFerias(id);
			ferias = feriasService.inserir(ferias);
			return ResponseEntity.ok(ferias);
		}	
		
			
	//método delete	
		@DeleteMapping("/{id}")
		@ApiOperation(value = "Deletar férias por id", notes = "deletar férias")
		@ApiResponses(value = { 
				@ApiResponse(code = 200, message = "Férias removidas"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 500, message = "Erro interno do servidor"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
		public ResponseEntity<String> remover(@Valid @PathVariable Long id) {
			if (!feriasService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}		
			feriasService.remover(id);
			return ResponseEntity.ok("Férias removidas");		
		}
}

