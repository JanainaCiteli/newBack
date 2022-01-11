
package com.alterdata.serratec.pack3.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Compensacao;
import com.alterdata.serratec.pack3.domain.Time;
import com.alterdata.serratec.pack3.repository.ColaboradorRepository;
import com.alterdata.serratec.pack3.repository.CompensacaoRepository;

import javassist.NotFoundException;

@Service
public class CompensacaoService {

	@Autowired
	private CompensacaoRepository compensacaoRepository;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CompensacaoService compensacaoService;

	public List<Compensacao> pesquisarTodos() {
		return compensacaoRepository.findAll();
	}

	public Optional<Compensacao> pesquisarUm(Long idCompensacao) {
		return compensacaoRepository.findById(idCompensacao);

	}

	public Compensacao inserirCompensacao(Compensacao compensacao) {

		LocalDate diaSemana = LocalDate.now();
		DayOfWeek dia = diaSemana.getDayOfWeek();
		// System.out.println(diaSemana.getDayOfWeek());

		compensacao.setDataCompensacao(diaSemana);
		Long idColaborador = compensacao.getColaborador().getIdColaborador();
		List<Compensacao> compensacoes = compensacaoRepository.findAllByIdColaborador(idColaborador);
		Double horaDisponivel = colaboradorRepository.getById(idColaborador).getHoraDisponivel();
		Double comp = 0.0;
		Double totalPost = compensacao.getTotalCompensacao();
		for (Compensacao c : compensacoes) {
			comp += c.getTotalCompensacao();
		}
		horaDisponivel = comp + totalPost;
		colaboradorService.editarHorasDisponiveis(idColaborador, horaDisponivel);
		return compensacao;

	}

	public Compensacao inserir(Compensacao compensacao) {

		try {
			Optional<Colaborador> colaborador = colaboradorRepository
					.findById(compensacao.getColaborador().getIdColaborador());

			if (!colaborador.isPresent()) {

				throw new NotFoundException("Objeto não encontrado");
			}
			compensacao.setColaborador(colaborador.get());

		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		LocalDate diaSemana = LocalDate.now();
		DayOfWeek dia = diaSemana.getDayOfWeek();

		if (dia == DayOfWeek.SATURDAY && compensacao.getTotalCompensacao() <= 8) {

			compensacaoService.inserirCompensacao(compensacao);

		}

		else if ((dia == DayOfWeek.MONDAY || dia == DayOfWeek.TUESDAY || dia == DayOfWeek.WEDNESDAY
				|| dia == DayOfWeek.THURSDAY || dia == DayOfWeek.FRIDAY) && compensacao.getTotalCompensacao() <= 2) {

			compensacaoService.inserirCompensacao(compensacao);

		}

		else {
			throw new Error(
					"Em dias de semana, sua compensação não pode passar de 2 horas em um mesmo dia. Aos sábados, não deve passar de 8 horas");
		}

		return compensacaoRepository.saveAndFlush(compensacao);

	}

	public boolean idExiste(Long idCompensacao) {
		return compensacaoRepository.existsById(idCompensacao);
	}

	public void remover(Long idCompensacao) {
		compensacaoRepository.deleteById(idCompensacao);
	}

	public Compensacao editar(Compensacao compensacao) {
		compensacaoService.inserir(compensacao);
		return compensacaoRepository.save(compensacao);
	}

}
