package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionPeriodoResponse;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionRequest;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionResponse;

@Component
public class LiquidacionDtoMapper {

    public Liquidacion toDomain(LiquidacionRequest request) {
        if (request == null) {
            return null;
        }
        Liquidacion.LiquidacionBuilder builder = Liquidacion.builder()
                .liquidacionId(request.getLiquidacionId())
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .fechaLiquidacion(request.getFechaLiquidacion())
                .fechaAcreditacion(request.getFechaAcreditacion())
                .dependenciaId(request.getDependenciaId())
                .salida(request.getSalida());
        if (request.getTotalRemunerativo() != null) {
            builder.totalRemunerativo(request.getTotalRemunerativo());
        }
        if (request.getTotalNoRemunerativo() != null) {
            builder.totalNoRemunerativo(request.getTotalNoRemunerativo());
        }
        if (request.getTotalDeduccion() != null) {
            builder.totalDeduccion(request.getTotalDeduccion());
        }
        if (request.getTotalNeto() != null) {
            builder.totalNeto(request.getTotalNeto());
        }
        if (request.getBloqueado() != null) {
            builder.bloqueado(request.getBloqueado());
        }
        if (request.getEstado() != null) {
            builder.estado(request.getEstado());
        }
        if (request.getLiquida() != null) {
            builder.liquida(request.getLiquida());
        }
        return builder.build();
    }

    public LiquidacionResponse toResponse(Liquidacion domain) {
        if (domain == null) {
            return null;
        }
        return LiquidacionResponse.builder()
                .liquidacionId(domain.getLiquidacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .fechaLiquidacion(domain.getFechaLiquidacion())
                .fechaAcreditacion(domain.getFechaAcreditacion())
                .dependenciaId(domain.getDependenciaId())
                .salida(domain.getSalida())
                .totalRemunerativo(domain.getTotalRemunerativo())
                .totalNoRemunerativo(domain.getTotalNoRemunerativo())
                .totalDeduccion(domain.getTotalDeduccion())
                .totalNeto(domain.getTotalNeto())
                .bloqueado(domain.getBloqueado())
                .estado(domain.getEstado())
                .liquida(domain.getLiquida())
                .key(domain.key())
                .build();
    }

    public LiquidacionPeriodoResponse toPeriodoResponse(LiquidacionPeriodoForward domain) {
        if (domain == null) {
            return null;
        }
        return LiquidacionPeriodoResponse.builder()
                .liquidacionId(domain.getLiquidacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .fechaLiquidacion(domain.getFechaLiquidacion())
                .dependenciaId(domain.getDependenciaId())
                .salida(domain.getSalida())
                .totalRemunerativo(domain.getTotalRemunerativo())
                .totalNoRemunerativo(domain.getTotalNoRemunerativo())
                .totalDeduccion(domain.getTotalDeduccion())
                .totalNeto(domain.getTotalNeto())
                .bloqueado(domain.getBloqueado())
                .periodo(domain.getPeriodo())
                .build();
    }
}
