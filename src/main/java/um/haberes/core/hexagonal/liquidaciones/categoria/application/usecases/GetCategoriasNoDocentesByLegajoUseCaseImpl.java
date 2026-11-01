package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class GetCategoriasNoDocentesByLegajoUseCaseImpl implements GetCategoriasNoDocentesByLegajoUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<Categoria> getCategoriasNoDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        List<Integer> noDocenteIds = categoriaRepository.findAllByNoDocente((byte) 1).stream()
                .map(Categoria::getCategoriaId)
                .collect(Collectors.toList());
        if (noDocenteIds.isEmpty()) {
            return List.of();
        }
        List<Integer> legajoIds = cargoLiquidacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes, noDocenteIds).stream()
                .map(CargoLiquidacion::getCategoriaId)
                .collect(Collectors.toList());
        if (legajoIds.isEmpty()) {
            return List.of();
        }
        return categoriaRepository.findAllByCategoriaIdIn(legajoIds);
    }
}
