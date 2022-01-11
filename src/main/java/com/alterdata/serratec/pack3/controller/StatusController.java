package com.alterdata.serratec.pack3.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alterdata.serratec.pack3.domain.Status;
import com.alterdata.serratec.pack3.repository.StatusRepository;
import com.alterdata.serratec.pack3.service.StatusService;

@RestController
@RequestMapping("/status")
public class StatusController {
	
	private StatusRepository statusRepository;

	  StatusController(StatusRepository statusRepository) {
	      this.statusRepository = statusRepository;
	  }

		@Autowired
		private StatusService statusService;

		@GetMapping
		public List<Status> obterTodos(){
	        return statusService.obterTodos();
	    }

	    @GetMapping("/{id}")
	    public Optional<Status> obterPorId(@PathVariable Long id){
	        return statusService.obterPorId(id);
	    }
	    @PostMapping
	    public Status adicionar(@RequestBody Status status){
	        return statusService.adicionar(status);
	    }
	    
	    @PutMapping ("/{id}")
		public ResponseEntity <Status> alterar (@PathVariable Long id,@Valid @RequestBody Status status) {
			if (!statusRepository.existsById (id)) 
				return ResponseEntity.notFound().build();
			status.setIdStatus(id);
			status = statusRepository.save (status);
			return ResponseEntity.ok (status);
		}
 
	    
	    @DeleteMapping("/{id}")
		public ResponseEntity <Status> deletar(@PathVariable Long id) {
			try {
				statusRepository.deleteById(id);
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
		}
}