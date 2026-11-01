package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetLastCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class GetLastCodigoUseCaseImpl implements GetLastCodigoUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public Optional<Codigo> getLastCodigo() {
        return codigoRepository.findLast();
    }
}
