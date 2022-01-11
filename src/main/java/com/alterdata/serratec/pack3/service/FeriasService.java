package com.alterdata.serratec.pack3.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alterdata.serratec.pack3.domain.Colaborador;
import com.alterdata.serratec.pack3.domain.Ferias;
import com.alterdata.serratec.pack3.domain.Time;
import com.alterdata.serratec.pack3.repository.ColaboradorRepository;
import com.alterdata.serratec.pack3.repository.FeriasRepository;
import com.alterdata.serratec.pack3.repository.TimeRepository;


@Service
public class FeriasService {

	@Autowired
	private FeriasRepository feriasRepository;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private TimeRepository timeRepository;

	public List<Ferias> pesquisarTodos() {
		return feriasRepository.findAll();
	}

	public Optional<Ferias> pesquisarUm(Long idFerias) {
		return feriasRepository.findById(idFerias);
	}

	private Boolean buscarColaboradorDeFerias(Long id, Integer ano) {
        List<Ferias> todasAsFerias = feriasRepository.findaAllBuscarAno(ano);

        Optional<Colaborador> achou = null;
        if (todasAsFerias.size() > 0 ) {
            System.out.println(ano);

            for (Ferias ferias : todasAsFerias) {
                achou = ferias.getColaboradores().stream().filter(t -> t.getIdColaborador().equals(id)).findAny();

            }
        }
        System.out.println("aqui " + achou);

        if(achou == null || !achou.isPresent()) {
        return false;
        }else {
            return true;
        }

    }

	public Ferias inserir(Ferias ferias) {

		List<Colaborador> listaColaborador = new ArrayList<Colaborador>();
		LocalDate dataLocal = LocalDate.now();

		for (Colaborador c : ferias.getColaboradores()) {
			Time time = new Time();

			Optional<Colaborador> colaborador = colaboradorRepository.findById(c.getIdColaborador());
			listaColaborador.add(colaborador.get());

			time = timeRepository.findById(colaborador.get().getTime().getIdTime()).get();
			Period period = Period.between(dataLocal, colaborador.get().getInicioContrato());

			int years = Math.abs(period.getYears());
			System.out.println("Difference is : " + years + " year");

			if (years == 0) {
				throw new Error("Você ainda não pode tirar férias");
			}

			if (buscarColaboradorDeFerias(c.getIdColaborador(), ferias.getDataInicio().getYear())) {

				throw new Error("Você já está tirando férias");

			}

			List<Ferias> periodos = feriasRepository.findaAllPeridoData(ferias.getDataInicio(), ferias.getDataFim());

			if (periodos.size() > 0) {
				for (Ferias periodo : periodos) {
					Colaborador co = periodo.getColaboradores().stream().map(cola -> cola).findAny().get();

					System.out.println(co.getIdColaborador());
					if (time.getColaboradores().size() <= 12) {

						if (time.getIdTime().equals(co.getTime().getIdTime())) {
							throw new Error(
									"Você ainda não pode tirar férias, " + co.getLogin() + " já está tirando");
						}

					} else {
						Optional<Colaborador> teste = periodo.getColaboradores().stream()
								.filter(t -> t.getCargo().equalsIgnoreCase("senior")).findFirst();
						if (teste.isPresent()) {

							if (time.getColaboradores().size() > 12
									&& colaborador.get().getCargo().equals(teste.get().getCargo())) {
								throw new Error("Você ainda não pode tirar férias, outro sênior do seu time já está tirando");
							}
						}
					}
				}

			}

		}
		ferias.setColaboradores(listaColaborador);
		return feriasRepository.save(ferias);
	}

	public boolean idExiste(Long idFerias) {
		return feriasRepository.existsById(idFerias);
	}

	public void remover(Long idFerias) {
		feriasRepository.deleteById(idFerias);
	}

	public Ferias editar(Ferias ferias) {
		return feriasRepository.save(ferias);
	}

}

