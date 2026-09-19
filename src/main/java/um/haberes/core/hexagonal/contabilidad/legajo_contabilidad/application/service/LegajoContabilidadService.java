package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.exception.LegajoContabilidadException;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.CreateLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.DeleteLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.FindLegajoContabilidadesDiferenciaByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.GetLegajoContabilidadByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in.UpdateLegajoContabilidadUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.entity.LegajoContabilidadEntity;

@Service
@RequiredArgsConstructor
public class LegajoContabilidadService {

    private final FindLegajoContabilidadesDiferenciaByPeriodoUseCase findLegajoContabilidadesDiferenciaByPeriodoUseCase;
    private final GetLegajoContabilidadByUniqueUseCase getLegajoContabilidadByUniqueUseCase;
    private final CreateLegajoContabilidadUseCase createLegajoContabilidadUseCase;
    private final UpdateLegajoContabilidadUseCase updateLegajoContabilidadUseCase;
    private final DeleteLegajoContabilidadUseCase deleteLegajoContabilidadUseCase;

    public List<LegajoContabilidad> findAllDiferenciaByPeriodo(Integer anho, Integer mes) {
        return findLegajoContabilidadesDiferenciaByPeriodoUseCase.findLegajoContabilidadesDiferenciaByPeriodo(anho,
                mes);
    }

    public LegajoContabilidad findByUnique(Long legajoId, Integer anho, Integer mes) {
        return getLegajoContabilidadByUniqueUseCase.getLegajoContabilidadByUnique(legajoId, anho, mes)
                .orElseThrow(() -> new LegajoContabilidadException(legajoId, anho, mes));
    }

    public LegajoContabilidad save(LegajoContabilidad legajoContabilidad) {
        return createLegajoContabilidadUseCase.createLegajoContabilidad(legajoContabilidad);
    }

    /**
     * Puente de compatibilidad para consumidores legacy del tipo JPA
     * (ej. {@code um.haberes.core.service.facade.ContableService}). Eliminar cuando
     * dichos consumidores trabajen con el modelo de dominio {@link LegajoContabilidad}.
     */
    @Deprecated
    public LegajoContabilidad save(LegajoContabilidadEntity legajoContabilidadEntity) {
        return save(toDomain(legajoContabilidadEntity));
    }

    public LegajoContabilidad updateLegajoContabilidad(Long legajoContabilidadId, LegajoContabilidad legajoContabilidad) {
        return updateLegajoContabilidadUseCase.updateLegajoContabilidad(legajoContabilidadId, legajoContabilidad)
                .orElseThrow(() -> new LegajoContabilidadException(legajoContabilidadId));
    }

    public void delete(Long legajoContabilidadId) {
        deleteLegajoContabilidadUseCase.deleteLegajoContabilidad(legajoContabilidadId);
    }

    private static LegajoContabilidad toDomain(LegajoContabilidadEntity entity) {
        if (entity == null) {
            return null;
        }
        return LegajoContabilidad.builder()
                .legajoContabilidadId(entity.getLegajoContabilidadId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .diferencia(entity.getDiferencia())
                .remunerativo(entity.getRemunerativo())
                .noRemunerativo(entity.getNoRemunerativo())
                .build();
    }
}
