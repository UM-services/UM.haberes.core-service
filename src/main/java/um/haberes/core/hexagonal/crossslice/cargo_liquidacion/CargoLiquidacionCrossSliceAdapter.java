package um.haberes.core.hexagonal.crossslice.cargo_liquidacion;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CargoLiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

/** Adaptador cross-slice de cargos liquidados. */
@Component
@RequiredArgsConstructor
public class CargoLiquidacionCrossSliceAdapter implements CargoLiquidacionCrossSlice {

    private final CargoLiquidacionService cargoLiquidacionService;

    @Override
    public List<CargoLiquidacionView> porLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes).stream()
                .map(this::toView)
                .toList();
    }

    @Override
    public boolean tieneCargosActivos(Long legajoId, Integer anho, Integer mes) {
        return !cargoLiquidacionService.findAllActivosByLegajo(legajoId, anho, mes).isEmpty();
    }

    private CargoLiquidacionView toView(CargoLiquidacion cargo) {
        String acronimo = cargo.getDependencia() == null ? null : cargo.getDependencia().getAcronimo();
        boolean docente = cargo.getCategoria() != null && cargo.getCategoria().getDocente() != null
                && cargo.getCategoria().getDocente() == 1;
        return new CargoLiquidacionView(cargo.getCargoLiquidacionId(), cargo.getDependenciaId(), acronimo,
                cargo.getFechaDesde(), cargo.getFechaHasta(), cargo.getCategoriaId(),
                cargo.getCategoriaNombre(), cargo.getCategoriaBasico(), cargo.getEstadoDocente(),
                cargo.getHorasJornada(), cargo.getJornada(), cargo.getPresentismo(),
                cargo.getSituacion(), docente);
    }
}
