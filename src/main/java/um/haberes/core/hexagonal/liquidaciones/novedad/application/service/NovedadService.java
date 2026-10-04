package um.haberes.core.hexagonal.liquidaciones.novedad.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.exception.NovedadException;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.CreateNovedadUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.DeleteNovedadUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.DeleteNovedadesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadByUniqueKeyWithoutDependenciaUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByImportadoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByLegajoAndCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.SaveAllNovedadesUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.UpdateNovedadUseCase;

@Service
@RequiredArgsConstructor
public class NovedadService {

    private final GetNovedadesByLegajoUseCase getNovedadesByLegajoUseCase;
    private final GetNovedadesByCodigoUseCase getNovedadesByCodigoUseCase;
    private final GetNovedadesByImportadoUseCase getNovedadesByImportadoUseCase;
    private final GetNovedadesByLegajoAndCodigoUseCase getNovedadesByLegajoAndCodigoUseCase;
    private final GetNovedadByIdUseCase getNovedadByIdUseCase;
    private final GetNovedadByUniqueKeyUseCase getNovedadByUniqueKeyUseCase;
    private final GetNovedadByUniqueKeyWithoutDependenciaUseCase getNovedadByUniqueKeyWithoutDependenciaUseCase;
    private final CreateNovedadUseCase createNovedadUseCase;
    private final UpdateNovedadUseCase updateNovedadUseCase;
    private final SaveAllNovedadesUseCase saveAllNovedadesUseCase;
    private final DeleteNovedadesByPeriodoUseCase deleteNovedadesByPeriodoUseCase;
    private final DeleteNovedadUseCase deleteNovedadUseCase;

    public List<Novedad> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getNovedadesByLegajoUseCase.getNovedadesByLegajo(legajoId, anho, mes);
    }

    public List<Novedad> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return getNovedadesByCodigoUseCase.getNovedadesByCodigo(codigoId, anho, mes);
    }

    public List<Novedad> findAllByImportado(Byte importado, Integer anho, Integer mes) {
        return getNovedadesByImportadoUseCase.getNovedadesByImportado(importado, anho, mes);
    }

    public List<Novedad> findAllByLegajoAndCodigo(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return getNovedadesByLegajoAndCodigoUseCase.getNovedadesByLegajoAndCodigo(legajoId, anho, mes, codigoId);
    }

    public Novedad findByNovedadId(Long novedadId) {
        return getNovedadByIdUseCase.getNovedadById(novedadId)
                .orElseThrow(() -> new NovedadException(novedadId));
    }

    public Novedad findByUnique(Long legajoId, Integer anho, Integer mes, Integer codigoId, Integer dependenciaId) {
        return getNovedadByUniqueKeyUseCase.getNovedadByUniqueKey(legajoId, anho, mes, codigoId, dependenciaId)
                .orElseThrow(() -> new NovedadException(legajoId, anho, mes, codigoId, dependenciaId));
    }

    public Novedad findByUniqueWithoutDependencia(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return getNovedadByUniqueKeyWithoutDependenciaUseCase
                .getNovedadByUniqueKeyWithoutDependencia(legajoId, anho, mes, codigoId)
                .orElseThrow(() -> new NovedadException(legajoId, anho, mes, codigoId, null));
    }

    public Novedad add(Novedad novedad) {
        return createNovedadUseCase.createNovedad(novedad);
    }

    public Novedad update(Novedad novedad, Long novedadId) {
        return updateNovedadUseCase.updateNovedad(novedadId, novedad)
                .orElseThrow(() -> new NovedadException(novedadId));
    }

    public List<Novedad> saveAll(List<Novedad> novedades) {
        return saveAllNovedadesUseCase.saveAllNovedades(novedades);
    }

    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        deleteNovedadesByPeriodoUseCase.deleteNovedadesByPeriodo(anho, mes);
    }

    public void deleteByNovedadId(Long novedadId) {
        deleteNovedadUseCase.deleteNovedad(novedadId);
    }

}
