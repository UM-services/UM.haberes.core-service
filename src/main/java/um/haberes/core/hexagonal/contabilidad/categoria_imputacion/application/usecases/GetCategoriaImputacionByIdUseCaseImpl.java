package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetCategoriaImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out.CategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriaImputacionByIdUseCaseImpl implements GetCategoriaImputacionByIdUseCase {

    private final CategoriaImputacionRepository categoriaImputacionRepository;

    @Override
    public Optional<CategoriaImputacion> getCategoriaImputacionById(Long categoriaImputacionId) {
        return categoriaImputacionRepository.findById(categoriaImputacionId);
    }
}
