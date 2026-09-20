package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasAsignablesUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.DesignacionRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasAsignablesUseCaseImpl implements GetCategoriasAsignablesUseCase {

    private final CategoriaRepository categoriaRepository;
    private final DesignacionRepository designacionRepository;

    @Override
    public List<Categoria> getCategoriasAsignables() {
        List<Integer> asignadas = designacionRepository.findCategoriaIdsAsignadas();
        if (asignadas.isEmpty()) {
            return categoriaRepository.findAll();
        }
        return categoriaRepository.findAllByCategoriaIdNotIn(asignadas);
    }
}
