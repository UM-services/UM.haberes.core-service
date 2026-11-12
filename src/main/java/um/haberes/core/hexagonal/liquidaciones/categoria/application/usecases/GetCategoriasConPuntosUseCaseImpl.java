package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasConPuntosUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasConPuntosUseCaseImpl implements GetCategoriasConPuntosUseCase {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getCategoriasConPuntos() {
        return categoriaRepository.findAllByPuntosGreaterThan(BigDecimal.ZERO);
    }
}
