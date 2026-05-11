package um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;

@Component
public class FacultadMapper {

    public FacultadEntity toEntity(Facultad facultad) {
        if (facultad == null) {
            return null;
        }
        return FacultadEntity.builder()
                .facultadId(facultad.getFacultadId())
                .nombre(facultad.getNombre())
                .reducido(facultad.getReducido())
                .server(facultad.getServer())
                .backendServer(facultad.getBackendServer())
                .backendPort(facultad.getBackendPort())
                .dbName(facultad.getDbName())
                .dsn(facultad.getDsn())
                .build();
    }

    public Facultad toDomainModel(FacultadEntity entity) {
        if (entity == null) {
            return null;
        }
        return Facultad.builder()
                .facultadId(entity.getFacultadId())
                .nombre(entity.getNombre())
                .reducido(entity.getReducido())
                .server(entity.getServer())
                .backendServer(entity.getBackendServer())
                .backendPort(entity.getBackendPort())
                .dbName(entity.getDbName())
                .dsn(entity.getDsn())
                .build();
    }
}
