package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out.ProcesoGeneralRepository;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity.LiquidacionGeneralDetalleEntity;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.mapper.LiquidacionGeneralMapper;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.repository.JpaLiquidacionGeneralDetalleRepository;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.repository.JpaLiquidacionGeneralProcesoRepository;

/** Adaptador JPA del repositorio de procesos masivos. */
@Component
@RequiredArgsConstructor
public class JpaProcesoGeneralRepositoryAdapter implements ProcesoGeneralRepository {

    private final JpaLiquidacionGeneralProcesoRepository procesoRepository;

    private final JpaLiquidacionGeneralDetalleRepository detalleRepository;

    private final LiquidacionGeneralMapper mapper;

    @Override
    public ProcesoGeneral save(ProcesoGeneral proceso) {
        return mapper.toDomain(procesoRepository.save(mapper.toEntity(proceso)));
    }

    @Override
    public Optional<ProcesoGeneral> findById(String procesoId) {
        return procesoRepository.findById(procesoId).map(mapper::toDomain);
    }

    @Override
    public List<ProcesoGeneral> findAllByAnhoAndMes(Integer anho, Integer mes) {
        return procesoRepository.findAllByAnhoAndMesOrderByIniciadoEnDesc(anho, mes).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsEnCurso(Integer anho, Integer mes) {
        return procesoRepository.existsByAnhoAndMesAndEstado(anho, mes, EstadoProcesoGeneral.EN_CURSO);
    }

    @Override
    public void saveDetalle(String procesoId, ResultadoLegajoGeneral resultado) {
        detalleRepository.save(mapper.toEntity(procesoId, resultado));
    }

    @Override
    public void saveDetalles(String procesoId, List<ResultadoLegajoGeneral> resultados) {
        List<LiquidacionGeneralDetalleEntity> entities = resultados.stream()
                .map(resultado -> mapper.toEntity(procesoId, resultado))
                .toList();
        detalleRepository.saveAll(entities);
    }

    @Override
    public List<ResultadoLegajoGeneral> findDetalles(String procesoId, EstadoLegajoGeneral resultado) {
        List<LiquidacionGeneralDetalleEntity> entities = resultado == null
                ? detalleRepository.findAllByProcesoIdOrderByLegajoId(procesoId)
                : detalleRepository.findAllByProcesoIdAndResultadoOrderByLegajoId(procesoId, resultado);
        return entities.stream().map(mapper::toDomain).toList();
    }
}
