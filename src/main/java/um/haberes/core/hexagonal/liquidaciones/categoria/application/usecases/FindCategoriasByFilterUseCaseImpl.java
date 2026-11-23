package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CondicionCategoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.FindCategoriasByFilterUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaFilterRepository;

@Component
@RequiredArgsConstructor
public class FindCategoriasByFilterUseCaseImpl implements FindCategoriasByFilterUseCase {

    private final CategoriaFilterRepository categoriaFilterRepository;

    @Override
    public CategoriaPage findCategoriasByFilter(CategoriaFilter filter) {
        return categoriaFilterRepository.findByFilter(normalizar(filter));
    }

    private CategoriaFilter normalizar(CategoriaFilter filter) {
        CategoriaFilter base = filter == null ? CategoriaFilter.builder().build() : filter;
        return CategoriaFilter.builder()
                .texto(base.getTexto() == null ? "" : base.getTexto().trim())
                .condiciones(condicionesLimpias(base.getCondiciones()))
                .pagina(Math.max(base.getPagina(), 0))
                .tamano(tamanoValido(base.getTamano()))
                .build();
    }

    private List<CondicionCategoria> condicionesLimpias(List<CondicionCategoria> condiciones) {
        if (condiciones == null) {
            return List.of();
        }
        return condiciones.stream().filter(Objects::nonNull).distinct().toList();
    }

    private int tamanoValido(int tamano) {
        if (tamano <= 0) {
            return CategoriaFilter.TAMANO_POR_DEFECTO;
        }
        return Math.min(tamano, CategoriaFilter.TAMANO_MAXIMO);
    }
}
