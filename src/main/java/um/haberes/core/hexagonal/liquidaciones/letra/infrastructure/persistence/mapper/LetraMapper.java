package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.entity.LetraEntity;

@Component
public class LetraMapper {

    public LetraEntity toEntity(Letra domain) {
        if (domain == null) {
            return null;
        }
        LetraEntity entity = new LetraEntity();
        entity.setLetraId(domain.getLetraId());
        entity.setLegajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            entity.setAnho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            entity.setMes(domain.getMes());
        }
        if (domain.getNeto() != null) {
            entity.setNeto(domain.getNeto());
        }
        if (domain.getCadena() != null) {
            entity.setCadena(domain.getCadena());
        }
        return entity;
    }

    public Letra toDomain(LetraEntity entity) {
        if (entity == null) {
            return null;
        }
        Letra.LetraBuilder builder = Letra.builder()
                .letraId(entity.getLetraId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes());
        if (entity.getNeto() != null) {
            builder.neto(entity.getNeto());
        }
        if (entity.getCadena() != null) {
            builder.cadena(entity.getCadena());
        }
        return builder.build();
    }
}
