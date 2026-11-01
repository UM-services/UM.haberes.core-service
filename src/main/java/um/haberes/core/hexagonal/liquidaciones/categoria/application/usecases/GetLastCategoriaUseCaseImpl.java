package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetLastCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetLastCategoriaUseCaseImpl implements GetLastCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Optional<Categoria> getLastCategoria() {
        return categoriaRepository.findLast();
    }
}
