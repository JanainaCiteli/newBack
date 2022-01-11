package com.alterdata.serratec.pack3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alterdata.serratec.pack3.domain.Time;

@Repository
public interface TimeRepository extends JpaRepository <Time, Long> {
	
	Optional <Time> findById(Long id); 

}
