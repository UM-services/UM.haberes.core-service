package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.FindLegajoCargoClaseImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out.LegajoCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class FindLegajoCargoClaseImputacionesByLegajoUseCaseImpl
        implements FindLegajoCargoClaseImputacionesByLegajoUseCase {

    private final LegajoCargoClaseImputacionRepository legajoCargoClaseImputacionRepository;

    @Override
    public List<LegajoCargoClaseImputacion> findLegajoCargoClaseImputacionesByLegajo(Long legajoId, Integer anho,
            Integer mes) {
        return legajoCargoClaseImputacionRepository.findByLegajo(legajoId, anho, mes);
    }
}
