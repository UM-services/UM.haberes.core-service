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
        FacultadResponse response = new FacultadResponse();
        response.setFacultadId(domain.getFacultadId());
        response.setNombre(domain.getNombre());
        response.setReducido(domain.getReducido());
        response.setServer(domain.getServer());
        response.setBackendServer(domain.getBackendServer());
        response.setBackendPort(domain.getBackendPort());
        response.setDbName(domain.getDbName());
        response.setDsn(domain.getDsn());
        return response;
    }
}
