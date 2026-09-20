package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;

@Component
public class CodigoMapper {

    public CodigoEntity toEntity(Codigo domain) {
        if (domain == null) {
            return null;
        }
        CodigoEntity entity = new CodigoEntity();
        entity.setCodigoId(domain.getCodigoId());
        if (domain.getNombre() != null) {
            entity.setNombre(domain.getNombre());
        }
        if (domain.getDocente() != null) {
            entity.setDocente(domain.getDocente());
        }
        if (domain.getNoDocente() != null) {
            entity.setNoDocente(domain.getNoDocente());
        }
        if (domain.getTransferible() != null) {
            entity.setTransferible(domain.getTransferible());
        }
        if (domain.getIncluidoEtec() != null) {
            entity.setIncluidoEtec(domain.getIncluidoEtec());
        }
        entity.setAfipConceptoSueldoIdPrimerSemestre(domain.getAfipConceptoSueldoIdPrimerSemestre());
        entity.setAfipConceptoSueldoIdSegundoSemestre(domain.getAfipConceptoSueldoIdSegundoSemestre());
        return entity;
    }

    public Codigo toDomain(CodigoEntity entity) {
        if (entity == null) {
            return null;
        }
        Codigo.CodigoBuilder builder = Codigo.builder()
                .codigoId(entity.getCodigoId())
                .afipConceptoSueldoIdPrimerSemestre(entity.getAfipConceptoSueldoIdPrimerSemestre())
                .afipConceptoSueldoIdSegundoSemestre(entity.getAfipConceptoSueldoIdSegundoSemestre());
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        if (entity.getDocente() != null) {
            builder.docente(entity.getDocente());
        }
        if (entity.getNoDocente() != null) {
            builder.noDocente(entity.getNoDocente());
        }
        if (entity.getTransferible() != null) {
            builder.transferible(entity.getTransferible());
        }
        if (entity.getIncluidoEtec() != null) {
            builder.incluidoEtec(entity.getIncluidoEtec());
        }
        return builder.build();
    }
}
