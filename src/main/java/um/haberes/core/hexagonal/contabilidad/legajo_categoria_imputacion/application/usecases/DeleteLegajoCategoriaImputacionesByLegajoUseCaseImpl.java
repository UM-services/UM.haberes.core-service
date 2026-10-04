package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.DeleteLegajoCategoriaImputacionesByLegajoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out.LegajoCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCategoriaImputacionesByLegajoUseCaseImpl
        implements DeleteLegajoCategoriaImputacionesByLegajoUseCase {

    private final LegajoCategoriaImputacionRepository legajoCategoriaImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCategoriaImputacionesByLegajo(Long legajoId, Integer anho, Integer mes) {
        legajoCategoriaImputacionRepository.deleteAllByLegajo(legajoId, anho, mes);
    }
}
