package um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper.FacultadMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.mapper.GeograficaMapper;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity.DependenciaEntityBuilder;

@Component
@RequiredArgsConstructor
public class DependenciaMapper {

    private final FacultadMapper facultadMapper;

    private final GeograficaMapper geograficaMapper;

    public DependenciaEntity toEntity(Dependencia domain) {
        if (domain == null) {
            return null;
        }
        DependenciaEntityBuilder builder = DependenciaEntity.builder()
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId());
        if (domain.getNombre() != null) {
            builder.nombre(domain.getNombre());
        }
        if (domain.getAcronimo() != null) {
            builder.acronimo(domain.getAcronimo());
        }
        return builder.build();
    }

    public Dependencia toDomain(DependenciaEntity entity) {
        if (entity == null) {
            return null;
        }
        Dependencia.DependenciaBuilder builder = Dependencia.builder()
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .facultad(facultadMapper.toDomainModel(entity.getFacultad()))
                .geografica(geograficaMapper.toDomainModel(entity.getGeografica()));
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        if (entity.getAcronimo() != null) {
            builder.acronimo(entity.getAcronimo());
        }
        return builder.build();
    }
}
