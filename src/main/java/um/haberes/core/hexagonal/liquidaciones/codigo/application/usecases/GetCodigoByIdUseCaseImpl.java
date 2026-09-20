package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigoByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class GetCodigoByIdUseCaseImpl implements GetCodigoByIdUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public Optional<Codigo> getCodigoById(Integer codigoId) {
        return codigoRepository.findByCodigoId(codigoId);
    }
}
