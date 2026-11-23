package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasConPuntosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasConPuntosByPeriodoUseCaseImpl implements GetCategoriasConPuntosByPeriodoUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAllByPeriodo(Integer anho, Integer mes) {
        return categoriaRepository.findAllByPeriodo(anho, mes);
    }

    @Override
    public List<Categoria> getCategoriasConPuntos(Integer anho, Integer mes) {
        return categoriaRepository.findAllByPeriodo(anho, mes).stream()
                .filter(categoria -> categoria.getPuntos() != null
                        && categoria.getPuntos().compareTo(BigDecimal.ZERO) > 0)
                .toList();
    }

    @Override
    public Optional<Categoria> findByCategoriaIdAndPeriodo(Integer categoriaId, Integer anho, Integer mes) {
        return categoriaRepository.findByCategoriaIdAndPeriodo(categoriaId, anho, mes);
    }
}
