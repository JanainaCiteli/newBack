package com.alterdata.serratec.pack3.security;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.repository.ColaboradorRepository;




@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) {
		Colaborador colaborador = getUser(() -> colaboradorRepository.findByLogin(login));
		return colaborador;
	}
	
	public UserDetails pegarUsuarioPorId(Long id) {
		Colaborador colaborador = getUser(() -> colaboradorRepository.findById(id));
		return colaborador;
	}
	
	
	private Colaborador getUser(Supplier<Optional<Colaborador>> supplier) {
		return supplier.get().orElseThrow(() -> 
				new UsernameNotFoundException("Usuário não encontrado"));
	}

}
