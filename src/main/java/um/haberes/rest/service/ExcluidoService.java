/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ExcluidoException;
import um.haberes.rest.kotlin.model.Excluido;
import um.haberes.rest.repository.IExcluidoRepository;

/**
 * @author daniel
 *
 */
@Service
public class ExcluidoService {

	@Autowired
	private IExcluidoRepository repository;

	public List<Excluido> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public Excluido findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new ExcluidoException(legajoId, anho, mes));
	}

	public Excluido add(Excluido excluido) {
		repository.save(excluido);
		return excluido;
	}

	public Excluido update(Excluido newexcluido, Long excluidoId) {
		return repository.findByExcluidoId(excluidoId).map(excluido -> {
			excluido = new Excluido(excluidoId, newexcluido.getLegajoId(), newexcluido.getAnho(), newexcluido.getMes(),
					newexcluido.getFecha(), newexcluido.getObservaciones());
			repository.save(excluido);
			return excluido;
		}).orElseThrow(() -> new ExcluidoException(excluidoId));
	}

	@Transactional
	public void deleteByUnique(Long legajoId, Integer anho, Integer mes) {
		repository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

}
