package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in.DeleteLegajoCategoriaImputacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out.LegajoCategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLegajoCategoriaImputacionesByPeriodoUseCaseImpl
        implements DeleteLegajoCategoriaImputacionesByPeriodoUseCase {

    private final LegajoCategoriaImputacionRepository legajoCategoriaImputacionRepository;

    @Override
    @Transactional
    public void deleteLegajoCategoriaImputacionesByPeriodo(Integer anho, Integer mes) {
        legajoCategoriaImputacionRepository.deleteAllByPeriodo(anho, mes);
    }
}
