package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.DeleteLegajoCargoClaseImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out.LegajoCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCargoClaseImputacionesByPeriodoUseCaseImpl
        implements DeleteLegajoCargoClaseImputacionesByPeriodoUseCase {

    private final LegajoCargoClaseImputacionRepository legajoCargoClaseImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCargoClaseImputacionesByPeriodo(Integer anho, Integer mes) {
        legajoCargoClaseImputacionRepository.deleteAllByPeriodo(anho, mes);
    }
}
