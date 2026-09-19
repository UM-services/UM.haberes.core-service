package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class GetCodigosByPeriodoUseCaseImpl implements GetCodigosByPeriodoUseCase {

    private final CodigoRepository codigoRepository;
    private final CodigoPeriodoRepository codigoPeriodoRepository;

    @Override
    public List<Codigo> getCodigosByPeriodo(Integer anho, Integer mes) {
        List<Integer> codigoIds = codigoPeriodoRepository.findCodigoIdsByPeriodo(anho, mes);
        if (codigoIds.isEmpty()) {
            return List.of();
        }
        return codigoRepository.findAllByCodigoIdIn(codigoIds);
    }
}
