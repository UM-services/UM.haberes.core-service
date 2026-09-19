package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionPeriodoResponse;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionRequest;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionResponse;
import um.haberes.core.model.view.CargoLiquidacionPeriodo;

@Component
public class CargoLiquidacionDtoMapper {

    public CargoLiquidacion toDomain(CargoLiquidacionRequest request) {
        if (request == null) {
            return null;
        }
        CargoLiquidacion.CargoLiquidacionBuilder builder = CargoLiquidacion.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .dependenciaId(request.getDependenciaId())
                .fechaDesde(request.getFechaDesde())
                .fechaHasta(request.getFechaHasta())
                .categoriaId(request.getCategoriaId())
                .jornada(request.getJornada())
                .presentismo(request.getPresentismo())
                .situacion(request.getSituacion());
        if (request.getCategoriaNombre() != null) {
            builder.categoriaNombre(request.getCategoriaNombre());
        }
        if (request.getCategoriaBasico() != null) {
            builder.categoriaBasico(request.getCategoriaBasico());
        }
        if (request.getEstadoDocente() != null) {
            builder.estadoDocente(request.getEstadoDocente());
        }
        if (request.getHorasJornada() != null) {
            builder.horasJornada(request.getHorasJornada());
        }
        return builder.build();
    }

    public CargoLiquidacionResponse toResponse(CargoLiquidacion domain) {
        if (domain == null) {
            return null;
        }
        return CargoLiquidacionResponse.builder()
                .cargoLiquidacionId(domain.getCargoLiquidacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .dependenciaId(domain.getDependenciaId())
                .fechaDesde(domain.getFechaDesde())
                .fechaHasta(domain.getFechaHasta())
                .categoriaId(domain.getCategoriaId())
                .categoriaNombre(domain.getCategoriaNombre())
                .categoriaBasico(domain.getCategoriaBasico())
                .estadoDocente(domain.getEstadoDocente())
                .horasJornada(domain.getHorasJornada())
                .jornada(domain.getJornada())
                .presentismo(domain.getPresentismo())
                .situacion(domain.getSituacion())
                .build();
    }

    public CargoLiquidacionPeriodoResponse toPeriodoResponse(CargoLiquidacionPeriodo view) {
        if (view == null) {
            return null;
        }
        return CargoLiquidacionPeriodoResponse.builder()
                .cargoLiquidacionId(view.getCargoLiquidacionId())
                .legajoId(view.getLegajoId())
                .anho(view.getAnho())
                .mes(view.getMes())
                .dependenciaId(view.getDependenciaId())
                .fechaDesde(view.getFechaDesde())
                .fechaHasta(view.getFechaHasta())
                .categoriaId(view.getCategoriaId())
                .categoriaNombre(view.getCategoriaNombre())
                .categoriaBasico(view.getCategoriaBasico())
                .jornada(view.getJornada())
                .presentismo(view.getPresentismo())
                .asignacionEspecialPermanente(view.getAsignacionEspecialPermanente())
                .situacion(view.getSituacion())
                .periodo(view.getPeriodo())
                .build();
    }
}
