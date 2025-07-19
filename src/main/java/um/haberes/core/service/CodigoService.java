/**
 *
 */
package um.haberes.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import um.haberes.core.exception.CodigoException;
import um.haberes.core.kotlin.model.Codigo;
import um.haberes.core.kotlin.model.view.CodigoPeriodo;
import um.haberes.core.kotlin.model.view.CodigoSearch;
import um.haberes.core.repository.CodigoRepository;
import um.haberes.core.repository.view.CodigoPeriodoRepository;
import um.haberes.core.repository.view.CodigoSearchRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 */
@Service
@Slf4j
public class CodigoService {

    @Autowired
    private CodigoRepository repository;

    @Autowired
    private CodigoPeriodoRepository codigoperiodorepository;

    @Autowired
    private CodigoSearchRepository codigosearchrepository;

    @Cacheable("codigos")
    public List<Codigo> findAll() {
        return repository.findAll(Sort.by("codigoId").ascending());
    }

    public List<Codigo> findAllByCodigoIds(List<Integer> codigoIds) {
        return repository.findAllByCodigoIdIn(codigoIds, Sort.by("codigoId").ascending());
    }

    public List<Codigo> findAllByPeriodo(Integer anho, Integer mes) {
        List<CodigoPeriodo> codigoperiodos = codigoperiodorepository.findAllByAnhoAndMes(anho, mes);
        Map<Integer, Codigo> hashcodigos = repository.findAll().stream()
                .collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo));
        List<Codigo> codigos = new ArrayList<Codigo>();
        for (CodigoPeriodo codigoperiodo : codigoperiodos) {
            Codigo codigo = hashcodigos.get(codigoperiodo.getCodigoId());
            codigos.add(codigo);
        }
        return codigos;
    }

    public List<Codigo> findAllByTransferible(Byte transferible) {
        return repository.findAllByTransferible(transferible);
    }

    public List<CodigoSearch> findAllSearch(String chain) {
        return codigosearchrepository.findTop50BySearchLikeOrderByNombre("%" + chain + "%");
    }

    public Codigo findByCodigoId(Integer codigoId) {
        return repository.findById(codigoId).orElseThrow(() -> new CodigoException(codigoId));
    }

    public Codigo findLast() {
        return repository.findTopByOrderByCodigoId().orElseThrow(() -> new CodigoException());
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public void delete(Integer codigoId) {
        repository.deleteById(codigoId);
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public Codigo add(Codigo codigo) {
        repository.save(codigo);
        log.debug(codigo.toString());
        return codigo;
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public Codigo update(Codigo newCodigo, Integer codigoId) {
        return repository.findById(codigoId).map(codigo -> {
            codigo = new Codigo(codigoId, newCodigo.getNombre(), newCodigo.getDocente(), newCodigo.getNoDocente(),
                    newCodigo.getTransferible(), newCodigo.getIncluidoEtec(), newCodigo.getAfipConceptoSueldoIdPrimerSemestre(), newCodigo.getAfipConceptoSueldoIdSegundoSemestre(), null, null);
            codigo = repository.save(codigo);
            log.debug(codigo.toString());
            return codigo;
        }).orElseThrow(() -> new CodigoException(codigoId));
    }

    @Transactional
    @CacheEvict(value = "codigos", allEntries = true)
    public List<Codigo> saveAll(List<Codigo> codigos) {
        repository.saveAll(codigos);
        log.debug(codigos.toString());
        return codigos;
    }

}
