package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetCategoriaImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out.CategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriaImputacionByUniqueUseCaseImpl implements GetCategoriaImputacionByUniqueUseCase {

    private final CategoriaImputacionRepository categoriaImputacionRepository;

    @Override
    public Optional<CategoriaImputacion> getCategoriaImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Integer categoriaId) {
        return categoriaImputacionRepository.findByUnique(dependenciaId, facultadId, geograficaId, categoriaId);
    }
}
