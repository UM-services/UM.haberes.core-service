package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.dto.CargoTipoResponse;

@Component
public class CargoTipoDtoMapper {

    public CargoTipoResponse toResponse(CargoTipo domain) {
        if (domain == null) {
            return null;
        }
        return CargoTipoResponse.builder()
                .cargoTipoId(domain.getCargoTipoId())
                .aCargo(domain.getACargo())
                .nombre(domain.getNombre())
                .precedencia(domain.getPrecedencia())
                .build();
    }
}
