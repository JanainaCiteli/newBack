package com.alterdata.serratec.pack3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Time;


@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
	List<Colaborador> findAllByNomeColaboradorContainingIgnoreCase(String nomeColaborador);
//    List<Colaborador> findAllByAtivoTrue();
//    List<Colaborador>  findAllByAtivoTrueOrderById();

	List<Colaborador> findAllByTime(Time time);
	Optional<Colaborador> findByLogin(String login);
	
	@Modifying
	@Query(value = "DELETE FROM colaborador.horaDisponivel", nativeQuery=true)
	void deleteHoras();
}
