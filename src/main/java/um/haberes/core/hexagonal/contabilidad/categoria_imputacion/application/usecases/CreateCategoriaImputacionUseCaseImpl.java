package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.CreateCategoriaImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out.CategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateCategoriaImputacionUseCaseImpl implements CreateCategoriaImputacionUseCase {

    private final CategoriaImputacionRepository categoriaImputacionRepository;

    @Override
    public CategoriaImputacion createCategoriaImputacion(CategoriaImputacion categoriaImputacion) {
        return categoriaImputacionRepository.create(categoriaImputacion);
    }
}
