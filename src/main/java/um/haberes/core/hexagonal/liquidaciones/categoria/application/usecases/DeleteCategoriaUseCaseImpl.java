package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.DeleteCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class DeleteCategoriaUseCaseImpl implements DeleteCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public void deleteCategoria(Integer categoriaId) {
        categoriaRepository.deleteByCategoriaId(categoriaId);
    }
}
