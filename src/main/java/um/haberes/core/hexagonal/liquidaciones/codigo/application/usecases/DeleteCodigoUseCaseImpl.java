package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.DeleteCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCodigoUseCaseImpl implements DeleteCodigoUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public void deleteCodigo(Integer codigoId) {
        codigoRepository.deleteByCodigoId(codigoId);
    }
}
