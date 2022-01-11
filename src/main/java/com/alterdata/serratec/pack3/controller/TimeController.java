package com.alterdata.serratec.pack3.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Time;
import com.alterdata.serratec.pack3.service.TimeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/times") 
public class TimeController {
	
	@Autowired 
	private TimeService timeService;
	
	//método get 
	@GetMapping 
		@ApiOperation(value = "Retorna todos os times ativos", notes = "listagem de times")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna todos os times ativos"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<List<Time>> listar() {
			List<Time> listaTime = timeService.pesquisarTodos();
			List <Time> listaFiltrada = listaTime.stream()
					.filter(time -> time.getAtivo() == (true))
					.collect(Collectors.toList());
			return ResponseEntity.ok(listaFiltrada);		
	}
			
	//método get por filtro de id 
	@GetMapping("/{id}")
		@ApiOperation(value = "Retorna time cadastrado", notes = "retorna time por id")
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Retorna time"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		public ResponseEntity<Time> pesquisar(@PathVariable Long id) {
			Optional<Time> time = timeService.pesquisarUm(id);
			if (time.isPresent()) {
				return ResponseEntity.ok(time.get());
			}
			return ResponseEntity.notFound().build();
		}	
	
	
	//método post
		@PostMapping
		@ApiOperation(value = "Cadastra times", notes = "listagem de times")
		@ApiResponses(value = {
				@ApiResponse(code = 201, message = "Time adicionado"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
		})
		@ResponseStatus(HttpStatus.CREATED) 
		public Time inserir(@Valid @RequestBody Time time) {
			return timeService.inserir(time);
		}		

		//método put por filtro de id
		@PutMapping("/{id}")
		@ApiOperation(value = "Atualiza time cadastrado", notes = "atualizar times")
		@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Time atualizado"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção"),
	})
		public ResponseEntity<Time> atualizar(@Valid @RequestBody Time time, @PathVariable Long id) {
			if (!timeService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}
			time.setIdTime(id);
			time = timeService.inserir(time);
			return ResponseEntity.ok(time);
		}	
		
			
	//método delete	lógico
		@DeleteMapping("/{id}")
		@ApiOperation(value = "Deletar time por id", notes = "Deletar time")
		@ApiResponses(value = { 
				@ApiResponse(code = 200, message = "Time removido"),
				@ApiResponse(code = 401, message = "Erro de autenticação"),
				@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
				@ApiResponse(code = 404, message = "Recurso não encontrado"),
				@ApiResponse(code = 500, message = "Erro interno do servidor"),
				@ApiResponse(code = 505, message = "Ocorreu uma exceção") })
		public ResponseEntity<String> remover(@Valid @PathVariable Long id) {
			if (!timeService.idExiste(id)) {
				return ResponseEntity.notFound().build();
			}
			Time time = timeService.pesquisarUm(id).get(); 
			time.setAtivo(false);
			timeService.inserir(time);
			return ResponseEntity.ok("Time desabilitado");

}
}
