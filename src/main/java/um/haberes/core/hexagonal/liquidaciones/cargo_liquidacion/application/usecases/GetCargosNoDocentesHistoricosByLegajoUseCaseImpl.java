package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosNoDocentesHistoricosByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCargosNoDocentesHistoricosByLegajoUseCaseImpl implements GetCargosNoDocentesHistoricosByLegajoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CargoLiquidacion> getCargosNoDocentesHistoricosByLegajo(Long legajoId) {
        List<Integer> categoriaIds = categoriaRepository.findAllByNoDocente((byte) 1).stream()
                .map(Categoria::getCategoriaId)
                .collect(Collectors.toList());
        return cargoLiquidacionRepository.findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(legajoId,
                categoriaIds, BigDecimal.ZERO);
    }
}
