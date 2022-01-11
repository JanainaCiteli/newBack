package com.alterdata.serratec.pack3.repository;

import org.springframework.data.repository.CrudRepository;  
import org.springframework.stereotype.Repository;

import com.alterdata.serratec.pack3.domain.Status;

@Repository
public interface StatusRepository extends CrudRepository <Status, Long> {


   
}

