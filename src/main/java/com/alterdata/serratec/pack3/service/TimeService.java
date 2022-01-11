package com.alterdata.serratec.pack3.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Time;
import com.alterdata.serratec.pack3.repository.TimeRepository;

@Service
public class TimeService {
	
	 @Autowired
	 private TimeRepository timeRepository;
	 
	 	    public List<Time> pesquisarTodos() {
	 	        return timeRepository.findAll();
	 	    }

	 	    public Optional<Time> pesquisarUm(Long idTime) {
	 	        return timeRepository.findById(idTime);
	 	    }

	
	 	    public Time inserir(Time time) {
	 	        return timeRepository.save(time);
	 	    }
	 	    

	 	
	 	    public boolean idExiste(Long idTime) {
	 	        return timeRepository.existsById(idTime);
	 	    }
	 	    

	 	
	 	    public void remover(Long idTime) {
	 	    	timeRepository.deleteById(idTime);
	 	    }

	
	 		public Time editar(Time time) {
	 			return timeRepository.save(time);
	 		}
	 		
	 	
	 }
