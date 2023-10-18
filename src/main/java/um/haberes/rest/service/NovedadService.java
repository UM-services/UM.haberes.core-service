/**
 *
 */
package um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.NovedadException;
import um.haberes.rest.model.Novedad;
import um.haberes.rest.repository.INovedadRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 */
@Service
@Slf4j
public class NovedadService {

    @Autowired
    private INovedadRepository repository;

    public List<Novedad> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    public List<Novedad> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return repository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes, Sort.by("legajoId").ascending());
    }

    public List<Novedad> findAllByImportado(Byte importado, Integer anho, Integer mes) {
        return repository.findAllByImportadoAndAnhoAndMes(importado, anho, mes);
    }

    public List<Novedad> findAllByLegajoAndCodigo(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return repository.findAllByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId);
    }

    public Novedad findByNovedadId(Long novedadId) {
        return repository.findByNovedadId(novedadId).orElseThrow(() -> new NovedadException(novedadId));
    }

    public Novedad findByUnique(Long legajoId, Integer anho, Integer mes, Integer codigoId, Integer dependenciaId) {
        return repository
                .findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(legajoId, anho, mes, codigoId, dependenciaId)
                .orElseThrow(() -> new NovedadException(legajoId, anho, mes, codigoId, dependenciaId));
    }

    public Novedad findByUniqueWithoutDependencia(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return repository.findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(legajoId, anho, mes, codigoId)
                .orElseThrow(() -> new NovedadException(legajoId, anho, mes, codigoId, null));
    }

    public Novedad add(Novedad novedad) {
        repository.save(novedad);
        log.debug(novedad.toString());
        return novedad;
    }

    public Novedad update(Novedad newNovedad, Long novedadId) {
        return repository.findByNovedadId(novedadId).map(novedad -> {
            novedad = new Novedad(novedadId, newNovedad.getLegajoId(), newNovedad.getAnho(), newNovedad.getMes(),
                    newNovedad.getCodigoId(), newNovedad.getDependenciaId(), newNovedad.getImporte(),
                    newNovedad.getValue(), newNovedad.getObservaciones(), newNovedad.getImportado(),
                    newNovedad.getNovedadUploadId(), newNovedad.getPersona(), newNovedad.getCodigo(),
                    newNovedad.getDependencia());
            repository.save(novedad);
            log.debug(novedad.toString());
            return novedad;
        }).orElseThrow(() -> new NovedadException(novedadId));
    }

    @Transactional
    public List<Novedad> saveAll(List<Novedad> novedades) {
        return repository.saveAll(novedades);
    }

    @Transactional
    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        repository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Transactional
    public void deleteByNovedadId(Long novedadId) {
        repository.deleteById(novedadId);
    }

}
