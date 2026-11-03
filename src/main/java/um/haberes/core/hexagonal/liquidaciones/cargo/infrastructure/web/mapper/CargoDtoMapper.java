package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.dto.CargoRequest;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.dto.CargoResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

@Component
@RequiredArgsConstructor
public class CargoDtoMapper {

    private final PersonaDtoMapper personaDtoMapper;

    private final DependenciaDtoMapper dependenciaDtoMapper;

    private final CategoriaDtoMapper categoriaDtoMapper;

    public Cargo toDomain(CargoRequest request) {
        if (request == null) {
            return null;
        }
        Cargo.CargoBuilder builder = Cargo.builder()
                .legajoId(request.getLegajoId())
                .fechaAlta(request.getFechaAlta())
                .fechaBaja(request.getFechaBaja())
                .dependenciaId(request.getDependenciaId())
                .categoriaId(request.getCategoriaId())
                .jornada(request.getJornada())
                .presentismo(request.getPresentismo());
        if (request.getHorasJornada() != null) {
            builder.horasJornada(request.getHorasJornada());
        }
        return builder.build();
    }

    public CargoResponse toResponse(Cargo domain) {
        if (domain == null) {
            return null;
        }
        return CargoResponse.builder()
                .cargoId(domain.getCargoId())
                .legajoId(domain.getLegajoId())
                .fechaAlta(domain.getFechaAlta())
                .fechaBaja(domain.getFechaBaja())
                .dependenciaId(domain.getDependenciaId())
                .categoriaId(domain.getCategoriaId())
                .jornada(domain.getJornada())
                .presentismo(domain.getPresentismo())
                .horasJornada(domain.getHorasJornada())
                .persona(personaDtoMapper.toResponse(domain.getPersona()))
                .dependencia(dependenciaDtoMapper.toResponse(domain.getDependencia()))
                .categoria(categoriaDtoMapper.toResponse(domain.getCategoria()))
                .dependenciaNombre(domain.getDependencia() != null ? domain.getDependencia().getNombre() : null)
                .categoriaNombre(domain.getCategoria() != null ? domain.getCategoria().getNombre() : null)
                .personaApellido(domain.getPersona() != null ? domain.getPersona().getApellido() : null)
                .personaNombre(domain.getPersona() != null ? domain.getPersona().getNombre() : null)
                .build();
    }
}
