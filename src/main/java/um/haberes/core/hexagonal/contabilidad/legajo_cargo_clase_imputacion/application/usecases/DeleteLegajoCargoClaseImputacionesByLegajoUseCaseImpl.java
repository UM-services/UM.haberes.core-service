package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.DeleteLegajoCargoClaseImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out.LegajoCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCargoClaseImputacionesByLegajoUseCaseImpl
        implements DeleteLegajoCargoClaseImputacionesByLegajoUseCase {

    private final LegajoCargoClaseImputacionRepository legajoCargoClaseImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCargoClaseImputacionesByLegajo(Long legajoId, Integer anho, Integer mes) {
        legajoCargoClaseImputacionRepository.deleteAllByLegajo(legajoId, anho, mes);
    }
}
