package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriaByCategoriaIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriaByCategoriaIdUseCaseImpl implements GetCategoriaByCategoriaIdUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Optional<Categoria> getCategoriaByCategoriaId(Integer categoriaId) {
        return categoriaRepository.findByCategoriaId(categoriaId);
    }
}
