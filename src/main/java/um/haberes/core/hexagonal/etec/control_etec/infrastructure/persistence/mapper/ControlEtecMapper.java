package um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.persistence.entity.ControlEtecEntity;

@Component
public class ControlEtecMapper {

    public ControlEtec toDomain(ControlEtecEntity entity) {
        return ControlEtec.builder()
                .controlEtecId(entity.getControlEtecId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .valorPuntoEtec(entity.getValorPuntoEtec())
                .montoFijoCargo(entity.getMontoFijoCargo())
                .horasReferenciaCargo(entity.getHorasReferenciaCargo())
                .basicoMaestroGrado(entity.getBasicoMaestroGrado())
                .codigoAdicionalDirectivo(entity.getCodigoAdicionalDirectivo())
                .porcentajePresentismo(entity.getPorcentajePresentismo())
                .porcentajeAdicionalCarrera(entity.getPorcentajeAdicionalCarrera())
                .garantia(entity.getGarantia())
                .horasAdicionalCargaHoraria(entity.getHorasAdicionalCargaHoraria())
                .valorHoraAdicionalCargaHoraria(entity.getValorHoraAdicionalCargaHoraria())
                .build();
    }

    public ControlEtecEntity toEntity(ControlEtec domain) {
        return new ControlEtecEntity(domain.getControlEtecId(), domain.getAnho(), domain.getMes(),
                domain.getValorPuntoEtec(), domain.getMontoFijoCargo(), domain.getHorasReferenciaCargo(),
                domain.getBasicoMaestroGrado(), domain.getCodigoAdicionalDirectivo(),
                domain.getPorcentajePresentismo(), domain.getPorcentajeAdicionalCarrera(),
                domain.getGarantia(), domain.getHorasAdicionalCargaHoraria(),
                domain.getValorHoraAdicionalCargaHoraria());
    }
}
