package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetAllCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCodigosUseCaseImpl implements GetAllCodigosUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public List<Codigo> getAllCodigos() {
        return codigoRepository.findAll();
    }
}
