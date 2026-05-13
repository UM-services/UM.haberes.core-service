package um.haberes.core.hexagonal.geografica.infrastructure.persistence.mapper;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import org.springframework.stereotype.Component;

@Component
public class GeograficaMapper {

    public GeograficaEntity toEntity(Geografica geografica) {
        if (geografica == null) {
            return null;
        }
        return GeograficaEntity.builder()
                .geograficaId(geografica.getGeograficaId())
                .nombre(geografica.getNombre())
                .reducido(geografica.getReducido())
                .desarraigo(geografica.getDesarraigo())
                .geograficaIdReemplazo(geografica.getGeograficaIdReemplazo())
                .build();
    }

    public Geografica toDomainModel(GeograficaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Geografica.builder()
                .geograficaId(entity.getGeograficaId())
                .nombre(entity.getNombre())
                .reducido(entity.getReducido())
                .desarraigo(entity.getDesarraigo())
                .geograficaIdReemplazo(entity.getGeograficaIdReemplazo())
                .build();
    }
}
