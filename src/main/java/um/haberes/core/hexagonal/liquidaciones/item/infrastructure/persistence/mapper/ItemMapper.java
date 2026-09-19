package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.entity.ItemEntity;

@Component
public class ItemMapper {

    public ItemEntity toEntity(Item domain) {
        if (domain == null) {
            return null;
        }
        ItemEntity entity = new ItemEntity();
        entity.setItemId(domain.getItemId());
        entity.setLegajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            entity.setAnho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            entity.setMes(domain.getMes());
        }
        entity.setCodigoId(domain.getCodigoId());
        if (domain.getCodigoNombre() != null) {
            entity.setCodigoNombre(domain.getCodigoNombre());
        }
        if (domain.getImporte() != null) {
            entity.setImporte(domain.getImporte());
        }
        return entity;
    }

    public Item toDomain(ItemEntity entity) {
        if (entity == null) {
            return null;
        }
        Item.ItemBuilder builder = Item.builder()
                .itemId(entity.getItemId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .codigoId(entity.getCodigoId())
                .codigoIncluidoEtec(entity.getCodigo() != null ? entity.getCodigo().getIncluidoEtec() : null);
        if (entity.getCodigo() != null) {
            builder.codigo(toDomainCodigo(entity.getCodigo()));
        }
        if (entity.getCodigoNombre() != null) {
            builder.codigoNombre(entity.getCodigoNombre());
        }
        if (entity.getImporte() != null) {
            builder.importe(entity.getImporte());
        }
        return builder.build();
    }

    private Codigo toDomainCodigo(CodigoEntity entity) {
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
