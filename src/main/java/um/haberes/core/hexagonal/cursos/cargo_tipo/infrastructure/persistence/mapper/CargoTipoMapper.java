package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;

@Component
public class CargoTipoMapper {

    public CargoTipoEntity toEntity(CargoTipo domain) {
        if (domain == null) {
            return null;
        }
        CargoTipoEntity entity = new CargoTipoEntity();
        entity.setCargoTipoId(domain.getCargoTipoId());
        entity.setPrecedencia(domain.getPrecedencia());
        if (domain.getACargo() != null) {
            entity.setACargo(domain.getACargo());
        }
        if (domain.getNombre() != null) {
            entity.setNombre(domain.getNombre());
        }
        return entity;
    }

    public CargoTipo toDomain(CargoTipoEntity entity) {
        if (entity == null) {
            return null;
        }
        CargoTipo.CargoTipoBuilder builder = CargoTipo.builder()
                .cargoTipoId(entity.getCargoTipoId())
                .precedencia(entity.getPrecedencia());
        if (entity.getACargo() != null) {
            builder.aCargo(entity.getACargo());
        }
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        return builder.build();
    }
}
