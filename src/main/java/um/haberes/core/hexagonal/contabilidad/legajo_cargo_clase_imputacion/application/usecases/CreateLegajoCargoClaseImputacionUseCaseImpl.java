package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in.CreateLegajoCargoClaseImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out.LegajoCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateLegajoCargoClaseImputacionUseCaseImpl implements CreateLegajoCargoClaseImputacionUseCase {

    private final LegajoCargoClaseImputacionRepository legajoCargoClaseImputacionRepository;

    @Override
    public LegajoCargoClaseImputacion createLegajoCargoClaseImputacion(
            LegajoCargoClaseImputacion legajoCargoClaseImputacion) {
        return legajoCargoClaseImputacionRepository.create(legajoCargoClaseImputacion);
    }
}
