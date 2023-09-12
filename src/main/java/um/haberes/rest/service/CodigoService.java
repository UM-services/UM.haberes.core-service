/**
 *
 */
package um.haberes.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CodigoException;
import um.haberes.rest.kotlin.model.Codigo;
import um.haberes.rest.model.view.CodigoPeriodo;
import um.haberes.rest.model.view.CodigoSearch;
import um.haberes.rest.repository.ICodigoRepository;
import um.haberes.rest.repository.view.ICodigoPeriodoRepository;
import um.haberes.rest.repository.view.ICodigoSearchRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 */
@Service
@Slf4j
public class CodigoService {

    @Autowired
    private ICodigoRepository repository;

    @Autowired
    private ICodigoPeriodoRepository codigoperiodorepository;

    @Autowired
    private ICodigoSearchRepository codigosearchrepository;

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

    public void delete(Integer codigoId) {
        repository.deleteById(codigoId);
    }

    public Codigo add(Codigo codigo) {
        repository.save(codigo);
        log.debug(codigo.toString());
        return codigo;
    }

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
    public List<Codigo> saveAll(List<Codigo> codigos) {
        repository.saveAll(codigos);
        log.debug(codigos.toString());
        return codigos;
    }

}
