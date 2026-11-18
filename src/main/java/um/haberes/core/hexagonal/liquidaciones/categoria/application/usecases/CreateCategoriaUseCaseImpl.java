package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.CreateCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class CreateCategoriaUseCaseImpl implements CreateCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaPeriodoRepository categoriaPeriodoRepository;

    @Override
    @Transactional
    public Categoria createCategoria(Categoria categoria, Integer anho, Integer mes) {
        if (categoria.getCategoriaId() == null) {
            throw new CategoriaException("La categoría debe tener un id");
        }
        if (categoriaRepository.findByCategoriaId(categoria.getCategoriaId()).isPresent()) {
            throw new CategoriaException("La categoría " + categoria.getCategoriaId() + " ya existe");
        }
        Categoria saved = categoriaRepository.save(categoria);
        if (anho > 0 && mes > 0) {
            categoriaPeriodoRepository.upsert(saved, anho, mes);
        }
        return saved;
    }
}
