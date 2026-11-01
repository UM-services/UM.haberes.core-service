package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.ActividadResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.BonoImpresionResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.IntegridadBonoResponse;

@Component
public class BonoDtoMapper {

    public IntegridadBonoResponse toResponse(IntegridadBono domain) {
        if (domain == null) {
            return null;
        }
        return IntegridadBonoResponse.builder()
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .ok(domain.isOk())
                .faltantes(domain.getFaltantes().stream()
                        .map(Enum::name)
                        .toList())
                .build();
    }

    public ActividadResponse toResponse(Actividad domain) {
        if (domain == null) {
            return null;
        }
        return ActividadResponse.builder()
                .actividadId(domain.getActividadId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .docente(domain.getDocente())
                .otras(domain.getOtras())
                .clases(domain.getClases())
                .dependenciaId(domain.getDependenciaId())
                .build();
    }

    public BonoImpresionResponse toResponse(BonoImpresion domain) {
        if (domain == null) {
            return null;
        }
        return BonoImpresionResponse.builder()
                .bonoImpresionId(domain.getBonoImpresionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .legajoIdSolicitud(domain.getLegajoIdSolicitud())
                .fecha(domain.getFecha())
                .ipAddress(domain.getIpAddress())
                .build();
    }

    public List<BonoImpresionResponse> toImpresionResponseList(List<BonoImpresion> dominios) {
        if (dominios == null) {
            return List.of();
        }
        return dominios.stream()
                .map(this::toResponse)
                .toList();
    }
}
