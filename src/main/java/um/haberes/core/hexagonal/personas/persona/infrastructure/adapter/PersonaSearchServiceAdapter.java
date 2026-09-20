package um.haberes.core.hexagonal.personas.persona.infrastructure.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaSearchRepository;
import um.haberes.core.service.view.PersonaSearchService;

@Component
@RequiredArgsConstructor
public class PersonaSearchServiceAdapter implements PersonaSearchRepository {

    private final PersonaSearchService personaSearchService;

    @Override
    public List<PersonaSearch> findMatching(List<String> conditions) {
        return personaSearchService.findAllByStrings(conditions).stream()
                .map(this::toDomain)
                .toList();
    }

    private PersonaSearch toDomain(um.haberes.core.model.view.PersonaSearch view) {
        if (view == null) {
            return null;
        }
        PersonaSearch.PersonaSearchBuilder builder = PersonaSearch.builder()
                .legajoId(view.getLegajoId())
                .nacimiento(view.getNacimiento())
                .altaDocente(view.getAltaDocente())
                .ajusteDocente(view.getAjusteDocente())
                .altaAdministrativa(view.getAltaAdministrativa())
                .ajusteAdministrativo(view.getAjusteAdministrativo())
                .situacionId(view.getSituacionId())
                .posgrado(view.getPosgrado())
                .estado(view.getEstado())
                .estadoAfip(view.getEstadoAfip())
                .dependenciaId(view.getDependenciaId())
                .salida(view.getSalida())
                .obraSocial(view.getObraSocial())
                .actividadAfip(view.getActividadAfip())
                .localidadAfip(view.getLocalidadAfip())
                .situacionAfip(view.getSituacionAfip())
                .modeloContratacionAfip(view.getModeloContratacionAfip())
                .search(view.getSearch());
        if (view.getDocumento() != null) {
            builder.documento(view.getDocumento());
        }
        if (view.getApellido() != null) {
            builder.apellido(view.getApellido());
        }
        if (view.getNombre() != null) {
            builder.nombre(view.getNombre());
        }
        if (view.getEstadoCivil() != null) {
            builder.estadoCivil(view.getEstadoCivil());
        }
        if (view.getReemplazoDesarraigo() != null) {
            builder.reemplazoDesarraigo(view.getReemplazoDesarraigo());
        }
        if (view.getMitadDesarraigo() != null) {
            builder.mitadDesarraigo(view.getMitadDesarraigo());
        }
        if (view.getCuil() != null) {
            builder.cuil(view.getCuil());
        }
        if (view.getLiquida() != null) {
            builder.liquida(view.getLiquida());
        }
        return builder.build();
    }
}
