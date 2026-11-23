package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.dto.EscalaHoraEtecRequest;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.dto.EscalaHoraEtecResponse;

@Component
public class EscalaHoraEtecDtoMapper {

    public EscalaHoraEtec toDomain(EscalaHoraEtecRequest request) {
        return EscalaHoraEtec.builder()
                .categoriaId(request.getCategoriaId())
                .horasDesde(request.getHorasDesde())
                .horasHasta(request.getHorasHasta())
                .montoFijo(request.getMontoFijo())
                .valorHora(request.getValorHora())
                .build();
    }

    public EscalaHoraEtecResponse toResponse(EscalaHoraEtec domain) {
        return new EscalaHoraEtecResponse(domain.getEscalaHoraEtecId(), domain.getControlEtecId(),
                domain.getCategoriaId(), domain.getHorasDesde(), domain.getHorasHasta(),
                domain.getMontoFijo(), domain.getValorHora());
    }
}
