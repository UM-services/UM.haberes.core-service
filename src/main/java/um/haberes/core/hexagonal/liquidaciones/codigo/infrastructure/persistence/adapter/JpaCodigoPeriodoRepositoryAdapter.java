package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoPeriodoRepository;
import um.haberes.core.model.view.CodigoPeriodo;
import um.haberes.core.repository.view.JpaCodigoPeriodoRepository;

@Component
@RequiredArgsConstructor
public class JpaCodigoPeriodoRepositoryAdapter implements CodigoPeriodoRepository {

    private final JpaCodigoPeriodoRepository jpaCodigoPeriodoRepository;

    @Override
    public List<Integer> findCodigoIdsByPeriodo(Integer anho, Integer mes) {
        return jpaCodigoPeriodoRepository.findAllByAnhoAndMes(anho, mes).stream()
                .map(CodigoPeriodo::getCodigoId)
                .collect(Collectors.toList());
    }
}
