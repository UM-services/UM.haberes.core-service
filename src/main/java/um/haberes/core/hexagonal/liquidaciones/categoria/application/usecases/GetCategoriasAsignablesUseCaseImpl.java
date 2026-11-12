package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.math.BigDecimal;
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
        List<Categoria> categorias = asignadas.isEmpty()
                ? categoriaRepository.findAll()
                : categoriaRepository.findAllByCategoriaIdNotIn(asignadas);
        // Los cargos con puntos (ETEC) se asignan por su propio circuito, no por designaciones.
        return categorias.stream()
                .filter(categoria -> categoria.getPuntos() == null
                        || categoria.getPuntos().compareTo(BigDecimal.ZERO) == 0)
                .toList();
    }
}
