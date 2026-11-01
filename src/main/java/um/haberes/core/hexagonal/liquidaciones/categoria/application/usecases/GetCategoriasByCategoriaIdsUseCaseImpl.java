package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasByCategoriaIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasByCategoriaIdsUseCaseImpl implements GetCategoriasByCategoriaIdsUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getCategoriasByCategoriaIds(Set<Integer> categoriaIds) {
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            return List.of();
        }
        return categoriaRepository.findAllByCategoriaIdIn(new ArrayList<>(categoriaIds));
    }
}
