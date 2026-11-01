package um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper.DependenciaMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity.PersonaEntityBuilder;

@Component
@RequiredArgsConstructor
public class PersonaMapper {

    private final DependenciaMapper dependenciaMapper;

    public PersonaEntity toEntity(Persona domain) {
        if (domain == null) {
            return null;
        }
        PersonaEntityBuilder builder = PersonaEntity.builder()
                .legajoId(domain.getLegajoId())
                .nacimiento(domain.getNacimiento())
                .altaDocente(domain.getAltaDocente())
                .ajusteDocente(domain.getAjusteDocente())
                .altaAdministrativa(domain.getAltaAdministrativa())
                .ajusteAdministrativo(domain.getAjusteAdministrativo())
                .situacionId(domain.getSituacionId())
                .posgrado(domain.getPosgrado())
                .estado(domain.getEstado())
                .estadoAfip(domain.getEstadoAfip())
                .dependenciaId(domain.getDependenciaId())
                .salida(domain.getSalida())
                .obraSocial(domain.getObraSocial())
                .actividadAfip(domain.getActividadAfip())
                .localidadAfip(domain.getLocalidadAfip())
                .situacionAfip(domain.getSituacionAfip())
                .modeloContratacionAfip(domain.getModeloContratacionAfip());
        if (domain.getDocumento() != null) {
            builder.documento(domain.getDocumento());
        }
        if (domain.getApellido() != null) {
            builder.apellido(domain.getApellido());
        }
        if (domain.getNombre() != null) {
            builder.nombre(domain.getNombre());
        }
        if (domain.getEstadoCivil() != null) {
            builder.estadoCivil(domain.getEstadoCivil());
        }
        if (domain.getReemplazoDesarraigo() != null) {
            builder.reemplazoDesarraigo(domain.getReemplazoDesarraigo());
        }
        if (domain.getMitadDesarraigo() != null) {
            builder.mitadDesarraigo(domain.getMitadDesarraigo());
        }
        if (domain.getCuil() != null) {
            builder.cuil(domain.getCuil());
        }
        if (domain.getLiquida() != null) {
            builder.liquida(domain.getLiquida());
        }
        if (domain.getDirectivoEtec() != null) {
            builder.directivoEtec(domain.getDirectivoEtec());
        }
        return builder.build();
    }

    public Persona toDomain(PersonaEntity entity) {
        if (entity == null) {
            return null;
        }
        Persona.PersonaBuilder builder = Persona.builder()
                .legajoId(entity.getLegajoId())
                .nacimiento(entity.getNacimiento())
                .altaDocente(entity.getAltaDocente())
                .ajusteDocente(entity.getAjusteDocente())
                .altaAdministrativa(entity.getAltaAdministrativa())
                .ajusteAdministrativo(entity.getAjusteAdministrativo())
                .situacionId(entity.getSituacionId())
                .posgrado(entity.getPosgrado())
                .estado(entity.getEstado())
                .estadoAfip(entity.getEstadoAfip())
                .dependenciaId(entity.getDependenciaId())
                .dependencia(dependenciaMapper.toDomain(entity.getDependencia()))
                .salida(entity.getSalida())
                .obraSocial(entity.getObraSocial())
                .actividadAfip(entity.getActividadAfip())
                .localidadAfip(entity.getLocalidadAfip())
                .situacionAfip(entity.getSituacionAfip())
                .modeloContratacionAfip(entity.getModeloContratacionAfip());
        if (entity.getDocumento() != null) {
            builder.documento(entity.getDocumento());
        }
        if (entity.getApellido() != null) {
            builder.apellido(entity.getApellido());
        }
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        if (entity.getEstadoCivil() != null) {
            builder.estadoCivil(entity.getEstadoCivil());
        }
        if (entity.getReemplazoDesarraigo() != null) {
            builder.reemplazoDesarraigo(entity.getReemplazoDesarraigo());
        }
        if (entity.getMitadDesarraigo() != null) {
            builder.mitadDesarraigo(entity.getMitadDesarraigo());
        }
        if (entity.getCuil() != null) {
            builder.cuil(entity.getCuil());
        }
        if (entity.getLiquida() != null) {
            builder.liquida(entity.getLiquida());
        }
        if (entity.getDirectivoEtec() != null) {
            builder.directivoEtec(entity.getDirectivoEtec());
        }
        return builder.build();
    }
}
