package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionPeriodoRepository;
import um.haberes.core.model.view.LiquidacionPeriodo;
import um.haberes.core.repository.view.JpaLiquidacionPeriodoRepository;

@Component
@RequiredArgsConstructor
public class JpaLiquidacionPeriodoRepositoryAdapter implements LiquidacionPeriodoRepository {

    private final JpaLiquidacionPeriodoRepository jpaLiquidacionPeriodoRepository;

    @Override
    public List<LiquidacionPeriodoForward> findAllByLegajoIdForward(Long legajoId, Integer anho, Integer mes) {
        Long periodo = anho * 100L + mes;
        return jpaLiquidacionPeriodoRepository.findAllByLegajoIdAndPeriodoGreaterThanEqual(legajoId, periodo,
                Sort.by("anho").ascending().and(Sort.by("mes").ascending())).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private LiquidacionPeriodoForward toDomain(LiquidacionPeriodo view) {
        if (view == null) {
            return null;
        }
        LiquidacionPeriodoForward.LiquidacionPeriodoForwardBuilder builder = LiquidacionPeriodoForward.builder()
                .liquidacionId(view.getLiquidacionId())
                .legajoId(view.getLegajoId())
                .anho(view.getAnho())
                .mes(view.getMes())
                .fechaLiquidacion(view.getFechaLiquidacion())
                .dependenciaId(view.getDependenciaId())
                .salida(view.getSalida())
                .bloqueado(view.getBloqueado())
                .periodo(view.getPeriodo());
        if (view.getTotalRemunerativo() != null) {
            builder.totalRemunerativo(view.getTotalRemunerativo());
        }
        if (view.getTotalNoRemunerativo() != null) {
            builder.totalNoRemunerativo(view.getTotalNoRemunerativo());
        }
        if (view.getTotalDeduccion() != null) {
            builder.totalDeduccion(view.getTotalDeduccion());
        }
        if (view.getTotalNeto() != null) {
            builder.totalNeto(view.getTotalNeto());
        }
        return builder.build();
    }
}
