/**
 *
 */
package um.haberes.rest.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.GeograficaException;
import um.haberes.rest.kotlin.model.Geografica;
import um.haberes.rest.repository.IGeograficaRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dquinteros
 *
 */
@Service
@Slf4j
public class GeograficaService {

    private final IGeograficaRepository repository;

    @Autowired
    public GeograficaService(IGeograficaRepository repository) {
        this.repository = repository;
    }

    public List<Geografica> findAll() {
        var geograficas = repository.findAll(Sort.by("nombre").ascending());
        try {
            log.debug("geograficas = {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(geograficas));
        } catch (JsonProcessingException e) {
            log.debug("geograficas = null");
        }
        return geograficas;
    }

    public List<Geografica> findAllByGeograficaIdIn(List<Integer> ids) {
        return repository.findAllByGeograficaIdIn(ids);
    }

    public Geografica findByGeograficaId(Integer geograficaId) {
        return repository.findByGeograficaId(geograficaId)
                .orElseThrow(() -> new GeograficaException(geograficaId));
    }

    public Geografica update(Geografica newgeografica, Integer geograficaId) {
        return repository.findByGeograficaId(geograficaId).map(geografica -> {
            geografica = new Geografica(geograficaId, newgeografica.getNombre(), newgeografica.getReducido(),
                    newgeografica.getDesarraigo(), newgeografica.getGeograficaIdReemplazo());
            geografica = repository.save(geografica);
            log.debug("Geografica -> " + geografica);
            return geografica;
        }).orElseThrow(() -> new GeograficaException(geograficaId));
    }

}
