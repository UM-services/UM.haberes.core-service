package um.haberes.core.hexagonal.geografica.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaRequest;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;

@Component
public class GeograficaDtoMapper {

    public Geografica toDomain(GeograficaRequest request) {
        if (request == null) {
            return null;
        }
        return Geografica.builder()
                .nombre(request.getNombre())
                .reducido(request.getReducido())
                .desarraigo(request.getDesarraigo())
                .geograficaIdReemplazo(request.getGeograficaIdReemplazo())
                .build();
    }

    public GeograficaResponse toResponse(Geografica domain) {
        if (domain == null) {
            return null;
        }
        return GeograficaResponse.builder()
                .geograficaId(domain.getGeograficaId())
                .nombre(domain.getNombre())
                .reducido(domain.getReducido())
                .desarraigo(domain.getDesarraigo())
                .geograficaIdReemplazo(domain.getGeograficaIdReemplazo())
                .build();
    }
}
