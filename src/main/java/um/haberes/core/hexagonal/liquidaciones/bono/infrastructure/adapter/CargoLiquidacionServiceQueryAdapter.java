package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoLiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CargoLiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

@Component
@RequiredArgsConstructor
public class CargoLiquidacionServiceQueryAdapter implements CargoLiquidacionQueryPort {

    private final CargoLiquidacionService cargoLiquidacionService;

    @Override
    public List<BigDecimal> findBasicoCargosDocentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return toBasicoList(cargoLiquidacionService.findAllDocenteByLegajo(legajoId, anho, mes));
    }

    @Override
    public List<BigDecimal> findBasicoCargosNoDocentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return toBasicoList(cargoLiquidacionService.findAllNoDocenteByLegajo(legajoId, anho, mes));
    }

    private List<BigDecimal> toBasicoList(List<CargoLiquidacion> cargos) {
        return cargos.stream()
                .map(CargoLiquidacion::getCategoriaBasico)
                .collect(Collectors.toList());
    }
}
