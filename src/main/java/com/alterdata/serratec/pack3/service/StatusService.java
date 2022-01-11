package com.alterdata.serratec.pack3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Status;
import com.alterdata.serratec.pack3.repository.StatusRepository;

@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepository;

	public List<Status> obterTodos() {
		return (List<Status>) statusRepository.findAll();
	}
	
    public Optional<Status> obterPorId(Long idStatus){
        return statusRepository.findById(idStatus);
    }
    
    public Status adicionar(Status status){
        return statusRepository.save(status);
    }
    public void deletar(Long id){
        statusRepository.deleteById(id);
    }
 }
