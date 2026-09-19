package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosNoDocentesByLegajoAndFacultadUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargosNoDocentesByLegajoAndFacultadUseCaseImpl implements GetCargosNoDocentesByLegajoAndFacultadUseCase {

    private static final Byte NO_DOCENTE = 1;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosNoDocentesByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return cargoLiquidacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(legajoId, anho, mes,
                        facultadId, NO_DOCENTE);
    }
}
