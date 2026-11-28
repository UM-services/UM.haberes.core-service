package um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto.ControlEtecRequest;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto.ControlEtecResponse;

@Component
public class ControlEtecDtoMapper {

    public ControlEtec toDomain(ControlEtecRequest request) {
        return ControlEtec.builder()
                .valorPuntoEtec(request.getValorPuntoEtec())
                .montoFijoCargo(request.getMontoFijoCargo())
                .horasReferenciaCargo(request.getHorasReferenciaCargo())
                .basicoMaestroGrado(request.getBasicoMaestroGrado())
                .codigoAdicionalDirectivo(request.getCodigoAdicionalDirectivo())
                .porcentajePresentismo(request.getPorcentajePresentismo())
                .porcentajeAdicionalCarrera(request.getPorcentajeAdicionalCarrera())
                .garantia(request.getGarantia())
                .horasAdicionalCargaHoraria(request.getHorasAdicionalCargaHoraria())
                .valorHoraAdicionalCargaHoraria(request.getValorHoraAdicionalCargaHoraria())
                .build();
    }

    public ControlEtecResponse toResponse(ControlEtec domain) {
        return new ControlEtecResponse(domain.getControlEtecId(), domain.getAnho(), domain.getMes(),
                domain.getValorPuntoEtec(), domain.getMontoFijoCargo(), domain.getHorasReferenciaCargo(),
                domain.getBasicoMaestroGrado(), domain.getCodigoAdicionalDirectivo(),
                domain.getPorcentajePresentismo(), domain.getPorcentajeAdicionalCarrera(),
                domain.getGarantia(), domain.getHorasAdicionalCargaHoraria(),
                domain.getValorHoraAdicionalCargaHoraria());
    }
}
