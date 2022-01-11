package com.alterdata.serratec.pack3.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "time")
public class Time {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id de Time")
	//@Column (name = "id_time")
	private Long idTime;
	
	@ApiModelProperty(value = "Nome do time")
	@Column(name = "nome_time", unique = true)
	private String nomeTime;
	
	@ApiModelProperty(value = "Quantidade de integrantes do time")
	private int integrantes;
	
	@ApiModelProperty(value = "Colaborador ativo")
	private boolean ativo = true;
	
	//Colaborador
	@OneToMany(mappedBy = "time")
	@ApiModelProperty(value = "Lista de colaboradores de um time")
	//@JsonIgnoreProperties("compensacoes")
	private List <Colaborador> colaboradores;
	
	public Time() {
		super();
	}


	public Long getIdTime() {
		return idTime;
	}


	public void setIdTime(Long idTime) {
		this.idTime = idTime;
	}


	public String getNomeTime() {
		return nomeTime;
	}

	public void setNomeTime(String nomeTime) {
		this.nomeTime = nomeTime;
	}

	public int getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(int integrantes) {
		this.integrantes = integrantes;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}


	public boolean getAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	
	

}
