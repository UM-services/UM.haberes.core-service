package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.CreateCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class CreateCodigoUseCaseImpl implements CreateCodigoUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public Codigo createCodigo(Codigo codigo) {
        return codigoRepository.save(codigo);
    }
}
