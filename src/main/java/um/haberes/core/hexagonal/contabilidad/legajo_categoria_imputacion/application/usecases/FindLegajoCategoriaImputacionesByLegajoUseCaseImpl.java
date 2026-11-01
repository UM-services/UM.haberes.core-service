package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.FindLegajoCategoriaImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out.LegajoCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class FindLegajoCategoriaImputacionesByLegajoUseCaseImpl
        implements FindLegajoCategoriaImputacionesByLegajoUseCase {

    private final LegajoCategoriaImputacionRepository legajoCategoriaImputacionRepository;

    @Override
    public List<LegajoCategoriaImputacion> findLegajoCategoriaImputacionesByLegajo(Long legajoId, Integer anho,
            Integer mes) {
        return legajoCategoriaImputacionRepository.findByLegajo(legajoId, anho, mes);
    }
}
