package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasDocentesUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasDocentesUseCaseImpl implements GetCategoriasDocentesUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getCategoriasDocentes() {
        return categoriaRepository.findAllByDocente((byte) 1);
    }
}
