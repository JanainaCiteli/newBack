package com.alterdata.serratec.pack3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alterdata.serratec.pack3.domain.Ferias;

@Repository
public interface FeriasRepository extends JpaRepository <Ferias, Long>{

    List<Ferias> findAllByDataInicio(LocalDate dataInicio);
    List<Ferias> findAllByDataFim(LocalDate dataInicio);
    
    @Query(value = "SELECT * FROM ferias WHERE data_inicio <= :data_inicio AND data_fim >= :data_fim", nativeQuery = true)
    List<Ferias> findaAllPeridoData(@Param("data_inicio") LocalDate data_inicio, @Param("data_fim") LocalDate data_fim  );
    
    @Query(value = "SELECT * FROM ferias WHERE  extract (year from data_inicio) = :data_inicio", nativeQuery = true)
    List<Ferias> findaAllBuscarAno(@Param("data_inicio") Integer data_inicio);

}
