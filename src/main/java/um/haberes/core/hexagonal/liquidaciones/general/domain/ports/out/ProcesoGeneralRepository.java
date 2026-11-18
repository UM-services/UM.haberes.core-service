package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;

/** Persistencia de los procesos masivos y su detalle. */
public interface ProcesoGeneralRepository {

    ProcesoGeneral save(ProcesoGeneral proceso);

    Optional<ProcesoGeneral> findById(String procesoId);

    List<ProcesoGeneral> findAllByAnhoAndMes(Integer anho, Integer mes);

    boolean existsEnCurso(Integer anho, Integer mes);

    void saveDetalle(String procesoId, ResultadoLegajoGeneral resultado);

    void saveDetalles(String procesoId, List<ResultadoLegajoGeneral> resultados);

    List<ResultadoLegajoGeneral> findDetalles(String procesoId, EstadoLegajoGeneral resultado);
}
