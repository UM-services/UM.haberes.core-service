package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.CreateLegajoCategoriaImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out.LegajoCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateLegajoCategoriaImputacionUseCaseImpl implements CreateLegajoCategoriaImputacionUseCase {

    private final LegajoCategoriaImputacionRepository legajoCategoriaImputacionRepository;

    @Override
    public LegajoCategoriaImputacion createLegajoCategoriaImputacion(
            LegajoCategoriaImputacion legajoCategoriaImputacion) {
        return legajoCategoriaImputacionRepository.create(legajoCategoriaImputacion);
    }
}
