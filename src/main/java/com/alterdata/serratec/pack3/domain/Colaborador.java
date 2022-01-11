package com.alterdata.serratec.pack3.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "colaborador")
public class Colaborador implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id de Colaborador", required = true)
	@Column(name = "id_colaborador")
	private Long idColaborador;

	@ApiModelProperty(value = "Nome do colaborador")
	@Column(name = "nome_colaborador")
	private String nomeColaborador;

	@ApiModelProperty(value = "Cargo do colaborador")
	@Column(name = "cargo")
	private String cargo;

	@ApiModelProperty(value = "Login do colaborador", required = true)
	@Column(name = "login", unique = true)
	private String login;

	@ApiModelProperty(value = "Senha do colaborador", required = true)
	@Column(name = "senha")
	private String senha;

	@ApiModelProperty(value = "Data do início do contrato do colaborador", required = true)
	@Column(name = "inicio_contrato")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate inicioContrato;
	
	@ApiModelProperty(value = "Horas disponíveis para uso do colaborador")
	@Column(name = "hora_disponivel")
	private Double horaDisponivel = 0.0;

	// Status
	@OneToOne
	@ApiModelProperty(value = "Status do colaborador")
	@JoinColumn(name = "id_status")
	private Status status;

	// Time
	@ApiModelProperty(value = "Time do colaborador")
	@ManyToOne
	@JsonIgnoreProperties("colaboradores")
	@JoinColumn(name = "id_time")
	private Time time;
	
	//Compensação 
	@OneToMany(mappedBy = "colaborador")
	private List <Compensacao> compensacoes;
	
	public Colaborador() {
		super();
	}

	public Long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getNomeColaborador() {
		return nomeColaborador;
	}

	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(LocalDate inicioContrato) {
		this.inicioContrato = inicioContrato;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public double getHoraDisponivel() {
		return horaDisponivel;
	}

	public void setHoraDisponivel(double horaDisponivel) {
		this.horaDisponivel = horaDisponivel;
	}

	public List<Compensacao> getCompensacoes() {
		return compensacoes;
	}

	public void setCompensacoes(List<Compensacao> compensacoes) {
		this.compensacoes = compensacoes;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
	
	

	
}