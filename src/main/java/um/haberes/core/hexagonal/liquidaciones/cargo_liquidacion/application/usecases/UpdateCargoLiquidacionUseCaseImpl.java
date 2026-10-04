package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.UpdateCargoLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class UpdateCargoLiquidacionUseCaseImpl implements UpdateCargoLiquidacionUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public Optional<CargoLiquidacion> updateCargoLiquidacion(Long cargoLiquidacionId,
            CargoLiquidacion cargoLiquidacion) {
        return cargoLiquidacionRepository.findByCargoLiquidacionId(cargoLiquidacionId)
                .map(existing -> cargoLiquidacionRepository.save(withoutIdentity(cargoLiquidacion)));
    }

    private CargoLiquidacion withoutIdentity(CargoLiquidacion cargo) {
        CargoLiquidacion.CargoLiquidacionBuilder builder = CargoLiquidacion.builder()
                .legajoId(cargo.getLegajoId())
                .anho(cargo.getAnho())
                .mes(cargo.getMes())
                .dependenciaId(cargo.getDependenciaId())
                .fechaDesde(cargo.getFechaDesde())
                .fechaHasta(cargo.getFechaHasta())
                .categoriaId(cargo.getCategoriaId())
                .jornada(cargo.getJornada())
                .presentismo(cargo.getPresentismo())
                .situacion(cargo.getSituacion());
        if (cargo.getCategoriaNombre() != null) {
            builder.categoriaNombre(cargo.getCategoriaNombre());
        }
        if (cargo.getCategoriaBasico() != null) {
            builder.categoriaBasico(cargo.getCategoriaBasico());
        }
        if (cargo.getEstadoDocente() != null) {
            builder.estadoDocente(cargo.getEstadoDocente());
        }
        if (cargo.getHorasJornada() != null) {
            builder.horasJornada(cargo.getHorasJornada());
        }
        return builder.build();
    }
}
