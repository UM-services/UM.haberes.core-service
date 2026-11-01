package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoRequest;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoResponse;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoSearchResponse;

@Component
public class CodigoDtoMapper {

    public Codigo toDomain(CodigoRequest request) {
        if (request == null) {
            return null;
        }
        Codigo.CodigoBuilder builder = Codigo.builder()
                .codigoId(request.getCodigoId());
        if (request.getNombre() != null) {
            builder.nombre(request.getNombre());
        }
        if (request.getDocente() != null) {
            builder.docente(request.getDocente());
        }
        if (request.getNoDocente() != null) {
            builder.noDocente(request.getNoDocente());
        }
        if (request.getTransferible() != null) {
            builder.transferible(request.getTransferible());
        }
        if (request.getIncluidoEtec() != null) {
            builder.incluidoEtec(request.getIncluidoEtec());
        }
        builder.afipConceptoSueldoIdPrimerSemestre(request.getAfipConceptoSueldoIdPrimerSemestre());
        builder.afipConceptoSueldoIdSegundoSemestre(request.getAfipConceptoSueldoIdSegundoSemestre());
        return builder.build();
    }

    public CodigoResponse toResponse(Codigo domain) {
        if (domain == null) {
            return null;
        }
        return CodigoResponse.builder()
                .codigoId(domain.getCodigoId())
                .nombre(domain.getNombre())
                .docente(domain.getDocente())
                .noDocente(domain.getNoDocente())
                .transferible(domain.getTransferible())
                .incluidoEtec(domain.getIncluidoEtec())
                .afipConceptoSueldoIdPrimerSemestre(domain.getAfipConceptoSueldoIdPrimerSemestre())
                .afipConceptoSueldoIdSegundoSemestre(domain.getAfipConceptoSueldoIdSegundoSemestre())
                .build();
    }

    public CodigoSearchResponse toSearchResponse(CodigoSearchResult domain) {
        if (domain == null) {
            return null;
        }
        return CodigoSearchResponse.builder()
                .codigoId(domain.getCodigoId())
                .nombre(domain.getNombre())
                .docente(domain.getDocente())
                .noDocente(domain.getNoDocente())
                .search(domain.getSearch())
                .build();
    }
}
