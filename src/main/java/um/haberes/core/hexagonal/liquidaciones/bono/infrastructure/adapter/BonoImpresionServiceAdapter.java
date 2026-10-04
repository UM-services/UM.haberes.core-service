package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoImpresionRepository;
import um.haberes.core.model.BonoImpresionEntity;
import um.haberes.core.service.BonoImpresionService;

@Component
@RequiredArgsConstructor
public class BonoImpresionServiceAdapter implements BonoImpresionRepository {

    private final BonoImpresionService bonoImpresionService;

    @Override
    public BonoImpresion save(BonoImpresion bonoImpresion) {
        return toDomain(bonoImpresionService.add(toEntity(bonoImpresion)));
    }

    @Override
    public List<BonoImpresion> findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(Long legajoId, Integer anho,
            Integer mes) {
        return bonoImpresionService.findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(legajoId, anho, mes).stream()
                .map(this::toDomain)
                .toList();
    }

    private BonoImpresionEntity toEntity(BonoImpresion domain) {
        if (domain == null) {
            return null;
        }
        BonoImpresionEntity entity = new BonoImpresionEntity();
        entity.setBonoImpresionId(domain.getBonoImpresionId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setLegajoIdSolicitud(domain.getLegajoIdSolicitud());
        entity.setFecha(domain.getFecha());
        entity.setIpAddress(domain.getIpAddress());
        return entity;
    }

    private BonoImpresion toDomain(BonoImpresionEntity entity) {
        if (entity == null) {
            return null;
        }
        return BonoImpresion.builder()
                .bonoImpresionId(entity.getBonoImpresionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .legajoIdSolicitud(entity.getLegajoIdSolicitud())
                .fecha(entity.getFecha())
                .ipAddress(entity.getIpAddress())
                .build();
    }
}
