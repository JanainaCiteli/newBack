package com.alterdata.serratec.pack3.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Time;
import com.alterdata.serratec.pack3.dto.LoginRequest;
import com.alterdata.serratec.pack3.dto.LoginResponse;
import com.alterdata.serratec.pack3.repository.ColaboradorRepository;
import com.alterdata.serratec.pack3.repository.TimeRepository;
import com.alterdata.serratec.pack3.security.JWTService;

import javassist.NotFoundException;

@Service
@Component
public class ColaboradorService {

	private static final String headerPrefix = "Bearer ";

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEnconder;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private TimeRepository timeRepository;

	public List<Colaborador> pesquisarTodos() {
		return colaboradorRepository.findAll();
	}

	public List<Colaborador> buscarNome(String nome) {
		return colaboradorRepository.findAllByNomeColaboradorContainingIgnoreCase(nome);
	}

	public Optional<Colaborador> pesquisarUm(Long idColaborador) {
		return colaboradorRepository.findById(idColaborador);
	}

	public Colaborador inserir(Colaborador colaborador) {
		try {

			Optional<Time> time = timeRepository.findById(colaborador.getTime().getIdTime());

			if (!time.isPresent()) {

				throw new NotFoundException("Objeto não encontrado");
			}
			colaborador.setTime(time.get());
			String senha = passwordEnconder.encode(colaborador.getSenha());
			colaborador.setSenha(senha);

		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return colaboradorRepository.save(colaborador);
	}

	public Colaborador editarHorasDisponiveis(Long IdColaborador, double horaDisponivel) {

		Colaborador colaborador = colaboradorRepository.getById(IdColaborador);
		colaborador.setHoraDisponivel(horaDisponivel);

		return colaboradorRepository.saveAndFlush(colaborador);
	}

	public boolean idExiste(Long idColaborador) {
		return colaboradorRepository.existsById(idColaborador);
	}

	public void remover(Long idColaborador) {
		colaboradorRepository.deleteById(idColaborador);
	}

	public Colaborador editar(Colaborador colaborador) {

		String senha = passwordEnconder.encode(colaborador.getSenha());
		colaborador.setSenha(senha);
		return colaboradorRepository.save(colaborador);
	}

	public LoginResponse logar(LoginRequest loginRequest) {

		Authentication autenticacao = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getLogin(), loginRequest.getSenha(), Collections.emptyList()));

		SecurityContextHolder.getContext().setAuthentication(autenticacao);

		String token = headerPrefix + jwtService.gerarToken(autenticacao);
		Optional<Colaborador> usuario = colaboradorRepository.findByLogin(loginRequest.getLogin());

		return new LoginResponse(token, usuario.get());
	}

	@Transactional
	@Scheduled(cron = "@monthly")
	public void deleteHoras() {
		colaboradorRepository.deleteHoras();

	}

}