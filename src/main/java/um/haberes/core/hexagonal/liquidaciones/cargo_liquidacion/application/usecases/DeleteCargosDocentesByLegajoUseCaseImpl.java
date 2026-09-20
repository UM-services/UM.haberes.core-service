package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargosDocentesByLegajoUseCaseImpl implements DeleteCargosDocentesByLegajoUseCase {

    private static final String SITUACION_ACTIVA = "A";

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    @Override
    public void deleteCargosDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        List<Integer> categoriaIds = categoriaRepository.findAllByDocente((byte) 1).stream()
                .map(Categoria::getCategoriaId)
                .collect(Collectors.toList());
        cargoLiquidacionRepository.deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(legajoId, anho, mes,
                SITUACION_ACTIVA, categoriaIds);
    }
}
