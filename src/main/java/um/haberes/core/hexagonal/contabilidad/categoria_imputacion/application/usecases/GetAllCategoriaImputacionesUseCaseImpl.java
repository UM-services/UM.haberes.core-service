package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in.GetAllCategoriaImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.out.CategoriaImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetAllCategoriaImputacionesUseCaseImpl implements GetAllCategoriaImputacionesUseCase {

    private final CategoriaImputacionRepository categoriaImputacionRepository;

    @Override
    public List<CategoriaImputacion> getAllCategoriaImputaciones() {
        return categoriaImputacionRepository.findAll();
    }
}
