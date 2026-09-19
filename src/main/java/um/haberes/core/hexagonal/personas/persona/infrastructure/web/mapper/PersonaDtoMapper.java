package um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaRequest;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaSearchResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaUploadRequest;

@Component
public class PersonaDtoMapper {

    public Persona toDomain(PersonaRequest request) {
        if (request == null) {
            return null;
        }
        Persona.PersonaBuilder builder = Persona.builder()
                .legajoId(request.getLegajoId())
                .nacimiento(request.getNacimiento())
                .altaDocente(request.getAltaDocente())
                .altaAdministrativa(request.getAltaAdministrativa())
                .situacionId(request.getSituacionId())
                .dependenciaId(request.getDependenciaId())
                .salida(request.getSalida())
                .obraSocial(request.getObraSocial())
                .actividadAfip(request.getActividadAfip())
                .localidadAfip(request.getLocalidadAfip())
                .modeloContratacionAfip(request.getModeloContratacionAfip());
        if (request.getDocumento() != null) {
            builder.documento(request.getDocumento());
        }
        if (request.getApellido() != null) {
            builder.apellido(request.getApellido());
        }
        if (request.getNombre() != null) {
            builder.nombre(request.getNombre());
        }
        if (request.getAjusteDocente() != null) {
            builder.ajusteDocente(request.getAjusteDocente());
        }
        if (request.getAjusteAdministrativo() != null) {
            builder.ajusteAdministrativo(request.getAjusteAdministrativo());
        }
        if (request.getEstadoCivil() != null) {
            builder.estadoCivil(request.getEstadoCivil());
        }
        if (request.getReemplazoDesarraigo() != null) {
            builder.reemplazoDesarraigo(request.getReemplazoDesarraigo());
        }
        if (request.getMitadDesarraigo() != null) {
            builder.mitadDesarraigo(request.getMitadDesarraigo());
        }
        if (request.getCuil() != null) {
            builder.cuil(request.getCuil());
        }
        if (request.getPosgrado() != null) {
            builder.posgrado(request.getPosgrado());
        }
        if (request.getEstado() != null) {
            builder.estado(request.getEstado());
        }
        if (request.getLiquida() != null) {
            builder.liquida(request.getLiquida());
        }
        if (request.getEstadoAfip() != null) {
            builder.estadoAfip(request.getEstadoAfip());
        }
        if (request.getSituacionAfip() != null) {
            builder.situacionAfip(request.getSituacionAfip());
        }
        if (request.getDirectivoEtec() != null) {
            builder.directivoEtec(request.getDirectivoEtec());
        }
        return builder.build();
    }

    public PersonaResponse toResponse(Persona domain) {
        if (domain == null) {
            return null;
        }
        return PersonaResponse.builder()
                .legajoId(domain.getLegajoId())
                .documento(domain.getDocumento())
                .apellido(domain.getApellido())
                .nombre(domain.getNombre())
                .nacimiento(domain.getNacimiento())
                .altaDocente(domain.getAltaDocente())
                .ajusteDocente(domain.getAjusteDocente())
                .altaAdministrativa(domain.getAltaAdministrativa())
                .ajusteAdministrativo(domain.getAjusteAdministrativo())
                .estadoCivil(domain.getEstadoCivil())
                .situacionId(domain.getSituacionId())
                .reemplazoDesarraigo(domain.getReemplazoDesarraigo())
                .mitadDesarraigo(domain.getMitadDesarraigo())
                .cuil(domain.getCuil())
                .posgrado(domain.getPosgrado())
                .estado(domain.getEstado())
                .liquida(domain.getLiquida())
                .estadoAfip(domain.getEstadoAfip())
                .dependenciaId(domain.getDependenciaId())
                .salida(domain.getSalida())
                .obraSocial(domain.getObraSocial())
                .actividadAfip(domain.getActividadAfip())
                .localidadAfip(domain.getLocalidadAfip())
                .situacionAfip(domain.getSituacionAfip())
                .modeloContratacionAfip(domain.getModeloContratacionAfip())
                .directivoEtec(domain.getDirectivoEtec())
                .build();
    }

    public PersonaSearchResponse toSearchResponse(PersonaSearch domain) {
        if (domain == null) {
            return null;
        }
        return PersonaSearchResponse.builder()
                .legajoId(domain.getLegajoId())
                .documento(domain.getDocumento())
                .apellido(domain.getApellido())
                .nombre(domain.getNombre())
                .nacimiento(domain.getNacimiento())
                .altaDocente(domain.getAltaDocente())
                .ajusteDocente(domain.getAjusteDocente())
                .altaAdministrativa(domain.getAltaAdministrativa())
                .ajusteAdministrativo(domain.getAjusteAdministrativo())
                .estadoCivil(domain.getEstadoCivil())
                .situacionId(domain.getSituacionId())
                .reemplazoDesarraigo(domain.getReemplazoDesarraigo())
                .mitadDesarraigo(domain.getMitadDesarraigo())
                .cuil(domain.getCuil())
                .posgrado(domain.getPosgrado())
                .estado(domain.getEstado())
                .liquida(domain.getLiquida())
                .estadoAfip(domain.getEstadoAfip())
                .dependenciaId(domain.getDependenciaId())
                .salida(domain.getSalida())
                .obraSocial(domain.getObraSocial())
                .actividadAfip(domain.getActividadAfip())
                .localidadAfip(domain.getLocalidadAfip())
                .situacionAfip(domain.getSituacionAfip())
                .modeloContratacionAfip(domain.getModeloContratacionAfip())
                .search(domain.getSearch())
                .build();
    }

    public UploadedFile toUploadedFile(PersonaUploadRequest request) {
        if (request == null) {
            return null;
        }
        return UploadedFile.builder()
                .filename(request.getFilename())
                .base64(request.getBase64())
                .build();
    }
}
