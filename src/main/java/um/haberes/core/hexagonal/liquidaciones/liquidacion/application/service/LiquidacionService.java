package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases.GetLiquidacionByPeriodoAnteriorUseCaseImpl;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.AcreditarLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.CreateLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.CreateLiquidacionWithVersionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.DeleteLiquidacionByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.DeleteLiquidacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByPeriodoAnteriorUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAcreditadoAndLegajoIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAcreditadoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAnhoAndMesBetweenUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByDependenciaUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoForwardUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoIdsAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByPeriodoAndLegajoIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByPeriodoLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesBySemestreLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesBySemestreUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.SaveAllLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.UpdateLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.UpdateLiquidacionWithVersionUseCase;

@Service
@RequiredArgsConstructor
public class LiquidacionService {

    private final GetLiquidacionesByPeriodoUseCase getLiquidacionesByPeriodoUseCase;
    private final GetLiquidacionesByPeriodoAndLegajoIdsUseCase getLiquidacionesByPeriodoAndLegajoIdsUseCase;
    private final GetLiquidacionesByPeriodoLegajoUseCase getLiquidacionesByPeriodoLegajoUseCase;
    private final GetLiquidacionesBySemestreUseCase getLiquidacionesBySemestreUseCase;
    private final GetLiquidacionesBySemestreLegajoUseCase getLiquidacionesBySemestreLegajoUseCase;
    private final GetLiquidacionesByAnhoAndMesBetweenUseCase getLiquidacionesByAnhoAndMesBetweenUseCase;
    private final GetLiquidacionesByLegajoIdsAndPeriodoUseCase getLiquidacionesByLegajoIdsAndPeriodoUseCase;
    private final GetLiquidacionesByLegajoUseCase getLiquidacionesByLegajoUseCase;
    private final GetLiquidacionesByLegajoForwardUseCase getLiquidacionesByLegajoForwardUseCase;
    private final GetLiquidacionesByDependenciaUseCase getLiquidacionesByDependenciaUseCase;
    private final GetLiquidacionesByAcreditadoUseCase getLiquidacionesByAcreditadoUseCase;
    private final GetLiquidacionesByAcreditadoAndLegajoIdsUseCase getLiquidacionesByAcreditadoAndLegajoIdsUseCase;
    private final GetLiquidacionByIdUseCase getLiquidacionByIdUseCase;
    private final GetLiquidacionByUniqueKeyUseCase getLiquidacionByUniqueKeyUseCase;
    private final GetLiquidacionByPeriodoAnteriorUseCase getLiquidacionByPeriodoAnteriorUseCase;
    private final CreateLiquidacionUseCase createLiquidacionUseCase;
    private final CreateLiquidacionWithVersionUseCase createLiquidacionWithVersionUseCase;
    private final UpdateLiquidacionUseCase updateLiquidacionUseCase;
    private final UpdateLiquidacionWithVersionUseCase updateLiquidacionWithVersionUseCase;
    private final SaveAllLiquidacionesUseCase saveAllLiquidacionesUseCase;
    private final AcreditarLiquidacionUseCase acreditarLiquidacionUseCase;
    private final DeleteLiquidacionesByPeriodoUseCase deleteLiquidacionesByPeriodoUseCase;
    private final DeleteLiquidacionByLegajoUseCase deleteLiquidacionByLegajoUseCase;

    public List<Liquidacion> getLiquidacionesByPeriodo(Integer anho, Integer mes, Integer limit) {
        return getLiquidacionesByPeriodoUseCase.getLiquidacionesByPeriodo(anho, mes, limit);
    }

    public List<Liquidacion> getLiquidacionesByPeriodoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds) {
        return getLiquidacionesByPeriodoAndLegajoIdsUseCase.getLiquidacionesByPeriodoAndLegajoIds(anho, mes, legajoIds);
    }

    public List<Liquidacion> getLiquidacionesByPeriodoLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return getLiquidacionesByPeriodoLegajoUseCase.getLiquidacionesByPeriodoLegajo(anho, mes, legajoId, limit);
    }

    public List<Liquidacion> getLiquidacionesBySemestre(Integer anho, Integer semestre, Integer limit) {
        return getLiquidacionesBySemestreUseCase.getLiquidacionesBySemestre(anho, semestre, limit);
    }

    public List<Liquidacion> getLiquidacionesBySemestreLegajo(Integer anho, Integer semestre, Long legajoId, Integer limit) {
        return getLiquidacionesBySemestreLegajoUseCase.getLiquidacionesBySemestreLegajo(anho, semestre, legajoId, limit);
    }

    public List<Liquidacion> getLiquidacionesByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta) {
        return getLiquidacionesByAnhoAndMesBetweenUseCase.getLiquidacionesByAnhoAndMesBetween(anho, mesDesde, mesHasta);
    }

    public List<Liquidacion> getLiquidacionesByLegajoIdsAndPeriodo(List<Long> legajoIds, Integer anho, Integer mes) {
        return getLiquidacionesByLegajoIdsAndPeriodoUseCase.getLiquidacionesByLegajoIdsAndPeriodo(legajoIds, anho, mes);
    }

    public List<Liquidacion> getLiquidacionesByLegajo(Long legajoId) {
        return getLiquidacionesByLegajoUseCase.getLiquidacionesByLegajo(legajoId);
    }

    public List<LiquidacionPeriodoForward> getLiquidacionesByLegajoForward(Long legajoId, Integer anho, Integer mes) {
        return getLiquidacionesByLegajoForwardUseCase.getLiquidacionesByLegajoForward(legajoId, anho, mes);
    }

    public List<Liquidacion> getLiquidacionesByDependencia(Integer dependenciaId, Integer anho, Integer mes, String salida) {
        return getLiquidacionesByDependenciaUseCase.getLiquidacionesByDependencia(dependenciaId, anho, mes, salida);
    }

    public List<Liquidacion> getLiquidacionesByAcreditado(Integer anho, Integer mes) {
        return getLiquidacionesByAcreditadoUseCase.getLiquidacionesByAcreditado(anho, mes);
    }

    public List<Liquidacion> getLiquidacionesByAcreditadoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds) {
        return getLiquidacionesByAcreditadoAndLegajoIdsUseCase.getLiquidacionesByAcreditadoAndLegajoIds(anho, mes, legajoIds);
    }

    public Liquidacion getLiquidacionById(Long liquidacionId) {
        return getLiquidacionByIdUseCase.getLiquidacionById(liquidacionId)
                .orElseThrow(() -> new LiquidacionException(liquidacionId));
    }

    public Liquidacion getLiquidacionByUniqueKey(Long legajoId, Integer anho, Integer mes) {
        return getLiquidacionByUniqueKeyUseCase.getLiquidacionByUniqueKey(legajoId, anho, mes)
                .orElseThrow(() -> new LiquidacionException(legajoId, anho, mes));
    }

    public Liquidacion getLiquidacionByPeriodoAnterior(Long legajoId, Integer anho, Integer mes) {
        return getLiquidacionByPeriodoAnteriorUseCase.getLiquidacionByPeriodoAnterior(legajoId, anho, mes)
                .orElseThrow(() -> new LiquidacionException(legajoId,
                        GetLiquidacionByPeriodoAnteriorUseCaseImpl.prevAnho(anho, mes),
                        GetLiquidacionByPeriodoAnteriorUseCaseImpl.prevMes(anho, mes)));
    }

    public Liquidacion add(Liquidacion liquidacion) {
        return createLiquidacionUseCase.createLiquidacion(liquidacion);
    }

    public Liquidacion addVersion(Liquidacion liquidacion, Integer version) {
        return createLiquidacionWithVersionUseCase.createLiquidacionWithVersion(liquidacion, version);
    }

    public Liquidacion update(Liquidacion liquidacion, Long liquidacionId) {
        return updateLiquidacionUseCase.updateLiquidacion(liquidacionId, liquidacion)
                .orElseThrow(() -> new LiquidacionException(liquidacionId));
    }

    public Liquidacion updateVersion(Liquidacion liquidacion, Long liquidacionId, Integer version) {
        return updateLiquidacionWithVersionUseCase.updateLiquidacionWithVersion(liquidacionId, liquidacion, version)
                .orElseThrow(() -> new LiquidacionException(liquidacionId));
    }

    public List<Liquidacion> saveAll(List<Liquidacion> liquidaciones, Integer version) {
        return saveAllLiquidacionesUseCase.saveAllLiquidaciones(liquidaciones, version);
    }

    public Liquidacion acreditado(Liquidacion liquidacion, OffsetDateTime fechaAcreditacion) {
        return acreditarLiquidacionUseCase.acreditarLiquidacion(liquidacion, fechaAcreditacion)
                .orElseThrow(() -> new LiquidacionException(liquidacion.getLiquidacionId()));
    }

    public void deleteByPeriodo(Integer anho, Integer mes) {
        deleteLiquidacionesByPeriodoUseCase.deleteLiquidacionesByPeriodo(anho, mes);
    }

    public void deleteByLegajo(Long legajoId, Integer anho, Integer mes) {
        deleteLiquidacionByLegajoUseCase.deleteLiquidacionByLegajo(legajoId, anho, mes);
    }
}
