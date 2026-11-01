package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.UpdateCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class UpdateCategoriaUseCaseImpl implements UpdateCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaPeriodoRepository categoriaPeriodoRepository;

    @Override
    @Transactional
    public Optional<Categoria> updateCategoria(Integer categoriaId, Categoria newCategoria, Integer anho, Integer mes) {
        return categoriaRepository.findByCategoriaId(categoriaId).map(categoria -> {
            categoria.setNombre(newCategoria.getNombre());
            categoria.setBasico(newCategoria.getBasico());
            categoria.setDocente(newCategoria.getDocente());
            categoria.setNoDocente(newCategoria.getNoDocente());
            categoria.setLiquidaPorHora(newCategoria.getLiquidaPorHora());
            categoria.setEstadoDocente(newCategoria.getEstadoDocente());
            if (anho > 0 && mes > 0) {
                categoriaPeriodoRepository.upsert(categoria, anho, mes);
            }
            return categoriaRepository.save(categoria);
        });
    }
}
