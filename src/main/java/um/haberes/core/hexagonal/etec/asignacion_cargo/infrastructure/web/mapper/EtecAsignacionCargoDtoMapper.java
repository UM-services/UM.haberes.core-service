package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.dto.EtecAsignacionCargoRequest;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.dto.EtecAsignacionCargoResponse;

@Component
public class EtecAsignacionCargoDtoMapper {

    public EtecAsignacionCargo toDomain(EtecAsignacionCargoRequest request) {
        if (request == null) {
            return null;
        }
        return EtecAsignacionCargo.builder()
                .legajoId(request.getLegajoId())
                .categoriaId(request.getCategoriaId())
                .dependenciaId(request.getDependenciaId())
                .fechaAlta(request.getFechaAlta())
                .fechaBaja(request.getFechaBaja())
                .observaciones(request.getObservaciones())
                .build();
    }

    public EtecAsignacionCargoResponse toResponse(EtecAsignacionCargo domain) {
        if (domain == null) {
            return null;
        }
        return EtecAsignacionCargoResponse.builder()
                .asignacionCargoId(domain.getAsignacionCargoId())
                .legajoId(domain.getLegajoId())
                .categoriaId(domain.getCategoriaId())
                .dependenciaId(domain.getDependenciaId())
                .fechaAlta(domain.getFechaAlta())
                .fechaBaja(domain.getFechaBaja())
                .observaciones(domain.getObservaciones())
                .categoriaNombre(domain.getCategoriaNombre())
                .dependenciaNombre(domain.getDependenciaNombre())
                .personaApellido(domain.getPersonaApellido())
                .personaNombre(domain.getPersonaNombre())
                .build();
    }
}
