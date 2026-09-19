package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.SaveAllCategoriasUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCategoriasUseCaseImpl implements SaveAllCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaPeriodoRepository categoriaPeriodoRepository;

    @Override
    @Transactional
    public List<Categoria> saveAllCategorias(List<Categoria> categorias, Integer anho, Integer mes) {
        List<Categoria> saved = categoriaRepository.saveAll(categorias);
        categoriaPeriodoRepository.upsertAll(categorias, anho, mes);
        return saved;
    }
}
