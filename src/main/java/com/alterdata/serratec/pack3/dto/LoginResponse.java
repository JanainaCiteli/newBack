package com.alterdata.serratec.pack3.dto;

import com.alterdata.serratec.pack3.domain.Colaborador;

public class LoginResponse {
	
	private String token;
	private Colaborador colaborador;
	

	public LoginResponse(String token, Colaborador colaborador) {
		this.token = token;
		this.colaborador = colaborador;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}



	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

		
}
