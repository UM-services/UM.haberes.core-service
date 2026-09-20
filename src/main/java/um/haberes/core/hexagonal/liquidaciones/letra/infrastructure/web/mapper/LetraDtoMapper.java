package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto.LetraRequest;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto.LetraResponse;

@Component
public class LetraDtoMapper {

    public Letra toDomain(LetraRequest request) {
        if (request == null) {
            return null;
        }
        Letra.LetraBuilder builder = Letra.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes());
        if (request.getNeto() != null) {
            builder.neto(request.getNeto());
        }
        if (request.getCadena() != null) {
            builder.cadena(request.getCadena());
        }
        return builder.build();
    }

    public LetraResponse toResponse(Letra domain) {
        if (domain == null) {
            return null;
        }
        return LetraResponse.builder()
                .letraId(domain.getLetraId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .neto(domain.getNeto())
                .cadena(domain.getCadena())
                .build();
    }
}
