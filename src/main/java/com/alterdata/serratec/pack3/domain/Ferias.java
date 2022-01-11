package com.alterdata.serratec.pack3.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Ferias {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id de Férias")
	private Long idFerias;
	
	@ApiModelProperty(value = "Data de início das férias")
	private LocalDate dataInicio;
	
	@ApiModelProperty(value = "Data de fim das férias")
	private LocalDate dataFim;
	
	//Colaborador
	@ManyToMany
	@JoinTable(name = "colaborador_ferias",
	joinColumns = @JoinColumn(name = "id_ferias"),
	inverseJoinColumns = @JoinColumn(name = "id_colaborador"))
	private List <Colaborador> colaboradores;
	

	public Ferias() {
		super();
	}

	public Long getIdFerias() {
		return idFerias;
	}

	public void setIdFerias(Long idFerias) {
		this.idFerias = idFerias;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}
	


}
