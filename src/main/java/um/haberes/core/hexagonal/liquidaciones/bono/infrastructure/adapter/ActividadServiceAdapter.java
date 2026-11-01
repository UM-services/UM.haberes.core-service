package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.ActividadException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ActividadRepository;
import um.haberes.core.model.ActividadEntity;
import um.haberes.core.service.ActividadService;

@Component
@RequiredArgsConstructor
public class ActividadServiceAdapter implements ActividadRepository {

    private final ActividadService actividadService;

    @Override
    public Optional<Actividad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        try {
            return Optional.ofNullable(toDomain(actividadService.findByUnique(legajoId, anho, mes)));
        } catch (ActividadException e) {
            return Optional.empty();
        }
    }

    @Override
    public Actividad save(Actividad actividad) {
        ActividadEntity saved;
        if (actividad.getActividadId() == null) {
            saved = actividadService.add(toEntity(actividad));
        } else {
            saved = actividadService.update(toEntity(actividad), actividad.getActividadId());
        }
        return toDomain(saved);
    }

    private ActividadEntity toEntity(Actividad domain) {
        if (domain == null) {
            return null;
        }
        ActividadEntity entity = new ActividadEntity();
        entity.setActividadId(domain.getActividadId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setDocente(domain.getDocente());
        entity.setOtras(domain.getOtras());
        entity.setClases(domain.getClases());
        entity.setDependenciaId(domain.getDependenciaId());
        return entity;
    }

    private Actividad toDomain(ActividadEntity entity) {
        if (entity == null) {
            return null;
        }
        return Actividad.builder()
                .actividadId(entity.getActividadId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .docente(entity.getDocente())
                .otras(entity.getOtras())
                .clases(entity.getClases())
                .dependenciaId(entity.getDependenciaId())
                .build();
    }
}
