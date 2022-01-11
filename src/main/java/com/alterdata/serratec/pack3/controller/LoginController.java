package com.alterdata.serratec.pack3.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alterdata.serratec.pack3.dto.LoginRequest;
import com.alterdata.serratec.pack3.dto.LoginResponse;
import com.alterdata.serratec.pack3.service.ColaboradorService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ColaboradorService colaboradorService;

	@PostMapping
	public LoginResponse login(@RequestBody LoginRequest request) {
		return colaboradorService.logar(request);
	}

}
