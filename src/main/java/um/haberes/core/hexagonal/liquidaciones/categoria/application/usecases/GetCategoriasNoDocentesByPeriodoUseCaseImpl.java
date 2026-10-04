package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasNoDocentesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasNoDocentesByPeriodoUseCaseImpl implements GetCategoriasNoDocentesByPeriodoUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaPeriodoRepository categoriaPeriodoRepository;

    @Override
    public List<Categoria> getCategoriasNoDocentesByPeriodo(Integer anho, Integer mes) {
        List<Integer> categoriaIds = categoriaPeriodoRepository.findNoDocenteCategoriaIdsByPeriodo(anho, mes);
        if (categoriaIds.isEmpty()) {
            return List.of();
        }
        return categoriaRepository.findAllByCategoriaIdIn(categoriaIds);
    }
}
