package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;

public interface ActividadRepository {

    Optional<Actividad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    Actividad save(Actividad actividad);
}
