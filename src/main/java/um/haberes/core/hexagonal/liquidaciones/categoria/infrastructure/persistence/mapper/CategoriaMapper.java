package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;

@Component
public class CategoriaMapper {

    public CategoriaEntity toEntity(Categoria domain) {
        if (domain == null) {
            return null;
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setCategoriaId(domain.getCategoriaId());
        if (domain.getNombre() != null) {
            entity.setNombre(domain.getNombre());
        }
        if (domain.getBasico() != null) {
            entity.setBasico(domain.getBasico());
        }
        if (domain.getDocente() != null) {
            entity.setDocente(domain.getDocente());
        }
        if (domain.getNoDocente() != null) {
            entity.setNoDocente(domain.getNoDocente());
        }
        if (domain.getLiquidaPorHora() != null) {
            entity.setLiquidaPorHora(domain.getLiquidaPorHora());
        }
        if (domain.getEstadoDocente() != null) {
            entity.setEstadoDocente(domain.getEstadoDocente());
        }
        return entity;
    }

    public Categoria toDomain(CategoriaEntity entity) {
        if (entity == null) {
            return null;
        }
        Categoria.CategoriaBuilder builder = Categoria.builder()
                .categoriaId(entity.getCategoriaId());
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        if (entity.getBasico() != null) {
            builder.basico(entity.getBasico());
        }
        if (entity.getDocente() != null) {
            builder.docente(entity.getDocente());
        }
        if (entity.getNoDocente() != null) {
            builder.noDocente(entity.getNoDocente());
        }
        if (entity.getLiquidaPorHora() != null) {
            builder.liquidaPorHora(entity.getLiquidaPorHora());
        }
        if (entity.getEstadoDocente() != null) {
            builder.estadoDocente(entity.getEstadoDocente());
        }
        return builder.build();
    }
}
