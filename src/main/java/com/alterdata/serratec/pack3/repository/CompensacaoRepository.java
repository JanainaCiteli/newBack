package com.alterdata.serratec.pack3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Compensacao;

@Repository
public interface CompensacaoRepository extends JpaRepository <Compensacao, Long> {
	
	@Query(value = "SELECT * FROM Compensacao WHERE id_colaborador = :idColaborador", nativeQuery = true)
	List<Compensacao> findAllByIdColaborador(Long idColaborador);

}
