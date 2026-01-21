/**
 *
 */
package um.haberes.core.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LiquidacionException;
import um.haberes.core.kotlin.model.Liquidacion;
import um.haberes.core.kotlin.model.LiquidacionVersion;
import um.haberes.core.kotlin.model.view.LiquidacionPeriodo;
import um.haberes.core.repository.LiquidacionRepository;
import um.haberes.core.service.view.LiquidacionPeriodoService;
import um.haberes.core.util.Periodo;

/**
 * @author daniel
 */
@Service
@RequiredArgsConstructor
public class LiquidacionService {

    private final LiquidacionRepository repository;
    private final LiquidacionVersionService liquidacionVersionService;
    private final LiquidacionPeriodoService liquidacionPeriodoService;

    public List<Liquidacion> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
        if (limit == 0)
            return repository.findAllByAnhoAndMesOrderByLegajoId(anho, mes, PageRequest.of(0, 999999));
        return repository.findAllByAnhoAndMesOrderByLegajoId(anho, mes, PageRequest.of(0, limit));
    }

    public List<Liquidacion> findAllByPeriodoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds) {
        return repository.findAllByAnhoAndMesAndLegajoIdIn(anho, mes, legajoIds);
    }

    public List<Liquidacion> findAllByPeriodoLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        if (limit == 0)
            return repository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, PageRequest.of(0, 999999));
        return repository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, PageRequest.of(0, limit));
    }

    public List<Liquidacion> findAllBySemestre(Integer anho, Integer semestre, Integer limit) {
        Integer mes_desde = (semestre - 1) * 6 + 1;
        Integer mes_hasta = semestre * 6;
        if (limit == 0)
            return repository.findAllByAnhoAndMesBetweenOrderByLegajoId(anho, mes_desde, mes_hasta,
                    PageRequest.of(0, 999999));
        return repository.findAllByAnhoAndMesBetweenOrderByLegajoId(anho, mes_desde, mes_hasta,
                PageRequest.of(0, limit));
    }

    public List<Liquidacion> findAllBySemestreLegajo(Integer anho, Integer semestre, Long legajoId, Integer limit) {
        Integer mes_desde = (semestre - 1) * 6 + 1;
        Integer mes_hasta = semestre * 6;
        if (limit == 0)
            return repository.findAllByAnhoAndMesBetweenAndLegajoId(anho, mes_desde, mes_hasta, legajoId,
                    PageRequest.of(0, 999999));
        return repository.findAllByAnhoAndMesBetweenAndLegajoId(anho, mes_desde, mes_hasta, legajoId,
                PageRequest.of(0, limit));
    }

    public List<Liquidacion> findAllByAnhoAndMesBetween(Integer anho, Integer mes_desde, Integer mes_hasta) {
        return repository.findAllByAnhoAndMesBetween(anho, mes_desde, mes_hasta);
    }

    public List<Liquidacion> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes);
    }

    public List<Liquidacion> findAllByLegajo(Long legajoId) {
        return repository.findAllByLegajoId(legajoId, Sort.by("anho").ascending().and(Sort.by("mes").ascending()));
    }

    public List<LiquidacionPeriodo> findAllByLegajoForward(Long legajoId, Integer anho, Integer mes) {
        return liquidacionPeriodoService.findAllByLegajoIdForward(legajoId, anho, mes);
    }

    public List<Liquidacion> findAllByDependencia(Integer dependenciaId, Integer anho, Integer mes, String salida) {
        return repository.findAllByDependenciaIdAndAnhoAndMesAndSalida(dependenciaId, anho, mes, salida,
                Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending()));
    }

    public List<Liquidacion> findAllByAcreditado(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMesAndFechaAcreditacionNotNull(anho, mes,
                Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending()));
    }

    public List<Liquidacion> findAllAcreditadoByLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds) {
        return repository.findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(anho, mes, legajoIds, Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending()));
    }

    public Liquidacion findByLiquidacionId(Long liquidacionId) {
        return repository.findByLiquidacionId(liquidacionId)
                .orElseThrow(() -> new LiquidacionException(liquidacionId));
    }

    public Liquidacion findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
                .orElseThrow(() -> new LiquidacionException(legajoId, anho, mes));
    }

    public Liquidacion findByPeriodoAnterior(Long legajoId, Integer anho, Integer mes) {
        Periodo periodo = Periodo.prevMonth(anho, mes);
        return repository.findByLegajoIdAndAnhoAndMes(legajoId, periodo.getAnho(), periodo.getMes())
                .orElseThrow(() -> new LiquidacionException(legajoId, periodo.getAnho(), periodo.getMes()));
    }

    public Liquidacion add(Liquidacion liquidacion) {
        liquidacion = repository.save(liquidacion);
        return liquidacion;
    }

    @Transactional
    public Liquidacion addVersion(Liquidacion liquidacion, Integer version) {
        liquidacion = add(liquidacion);
        liquidacionVersionService.add(new LiquidacionVersion(null, liquidacion.getLegajoId(), liquidacion.getAnho(),
                liquidacion.getMes(), version, liquidacion.getFechaLiquidacion(), liquidacion.getFechaAcreditacion(),
                liquidacion.getDependenciaId(), liquidacion.getSalida(), liquidacion.getTotalRemunerativo(),
                liquidacion.getTotalNoRemunerativo(), liquidacion.getTotalDeduccion(), liquidacion.getTotalNeto(),
                liquidacion.getBloqueado(), liquidacion.getEstado(), liquidacion.getLiquida()));
        return liquidacion;
    }

    public Liquidacion update(Liquidacion newLiquidacion, Long liquidacionId) {
        return repository.findByLiquidacionId(liquidacionId).map(liquidacion -> {
            liquidacion = new Liquidacion(liquidacionId, newLiquidacion.getLegajoId(), newLiquidacion.getAnho(),
                    newLiquidacion.getMes(), newLiquidacion.getFechaLiquidacion(),
                    newLiquidacion.getFechaAcreditacion(), newLiquidacion.getDependenciaId(),
                    newLiquidacion.getSalida(), newLiquidacion.getTotalRemunerativo(),
                    newLiquidacion.getTotalNoRemunerativo(), newLiquidacion.getTotalDeduccion(),
                    newLiquidacion.getTotalNeto(), newLiquidacion.getBloqueado(), newLiquidacion.getEstado(),
                    newLiquidacion.getLiquida(), newLiquidacion.getPersona(), newLiquidacion.getDependencia());
            liquidacion = repository.save(liquidacion);
            return liquidacion;
        }).orElseThrow(() -> new LiquidacionException(liquidacionId));
    }

    @Transactional
    public Liquidacion updateVersion(Liquidacion liquidacion, Long liquidacionId, Integer version) {
        liquidacion = update(liquidacion, liquidacionId);
        liquidacionVersionService.add(new LiquidacionVersion(null, liquidacion.getLegajoId(), liquidacion.getAnho(),
                liquidacion.getMes(), version, liquidacion.getFechaLiquidacion(), liquidacion.getFechaAcreditacion(),
                liquidacion.getDependenciaId(), liquidacion.getSalida(), liquidacion.getTotalRemunerativo(),
                liquidacion.getTotalNoRemunerativo(), liquidacion.getTotalDeduccion(), liquidacion.getTotalNeto(),
                liquidacion.getBloqueado(), liquidacion.getEstado(), liquidacion.getLiquida()));
        return liquidacion;
    }

    @Transactional
    public List<Liquidacion> saveall(List<Liquidacion> liquidaciones, Integer version) {
        liquidaciones = repository.saveAll(liquidaciones);
        List<LiquidacionVersion> backups = new ArrayList<>();
        for (Liquidacion liquidacion : liquidaciones) {
            backups.add(new LiquidacionVersion(null, liquidacion.getLegajoId(), liquidacion.getAnho(),
                    liquidacion.getMes(), version, liquidacion.getFechaLiquidacion(),
                    liquidacion.getFechaAcreditacion(), liquidacion.getDependenciaId(), liquidacion.getSalida(),
                    liquidacion.getTotalRemunerativo(), liquidacion.getTotalNoRemunerativo(),
                    liquidacion.getTotalDeduccion(), liquidacion.getTotalNeto(), liquidacion.getBloqueado(),
                    liquidacion.getEstado(), liquidacion.getLiquida()));
        }
        liquidacionVersionService.saveAll(backups);
        return liquidaciones;
    }

    public Liquidacion acreditado(Liquidacion liquidacion, OffsetDateTime fechaAcreditacion) {
        liquidacion.setFechaAcreditacion(fechaAcreditacion);
        liquidacion.setBloqueado((byte) 1);
        liquidacion = this.update(liquidacion, liquidacion.getLiquidacionId());
        return liquidacion;
    }

    @Transactional
    public void deleteByPeriodo(Integer anho, Integer mes) {
        repository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Transactional
    public void deleteByLegajo(Long legajoId, Integer anho, Integer mes) {
        repository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

}
