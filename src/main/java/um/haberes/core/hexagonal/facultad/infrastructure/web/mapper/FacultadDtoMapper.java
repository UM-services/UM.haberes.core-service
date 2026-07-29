package um.haberes.core.hexagonal.facultad.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;

@Component
public class FacultadDtoMapper {

    public FacultadResponse toResponse(Facultad domain) {
        if (domain == null) {
            return null;
        }
        return FacultadResponse.builder()
                .facultadId(domain.getFacultadId())
                .nombre(domain.getNombre())
                .reducido(domain.getReducido())
                .server(domain.getServer())
                .backendServer(domain.getBackendServer())
                .backendPort(domain.getBackendPort())
                .dbName(domain.getDbName())
                .dsn(domain.getDsn())
                .build();
    }
}
