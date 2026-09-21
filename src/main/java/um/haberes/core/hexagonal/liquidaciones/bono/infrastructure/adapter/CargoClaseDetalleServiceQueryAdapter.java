package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoClaseDetalleQueryPort;
import um.haberes.core.service.CargoClaseDetalleService;

@Component
@RequiredArgsConstructor
public class CargoClaseDetalleServiceQueryAdapter implements CargoClaseDetalleQueryPort {

    private final CargoClaseDetalleService cargoClaseDetalleService;

    @Override
    public boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return cargoClaseDetalleService.existsByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
