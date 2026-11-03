package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.persistence.mapper.AfipConceptoSueldoMapper;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;

@Component
@RequiredArgsConstructor
public class CodigoMapper {

    private final AfipConceptoSueldoMapper afipConceptoSueldoMapper;

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
                .afipConceptoSueldoIdSegundoSemestre(entity.getAfipConceptoSueldoIdSegundoSemestre())
                .afipConceptoSueldoPrimerSemestre(
                        afipConceptoSueldoMapper.toDomain(entity.getAfipConceptoSueldoPrimerSemestre()))
                .afipConceptoSueldoSegundoSemestre(
                        afipConceptoSueldoMapper.toDomain(entity.getAfipConceptoSueldoSegundoSemestre()));
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
