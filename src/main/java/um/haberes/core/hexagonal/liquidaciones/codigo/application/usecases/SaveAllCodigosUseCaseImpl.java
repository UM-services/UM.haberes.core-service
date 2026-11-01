package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.SaveAllCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCodigosUseCaseImpl implements SaveAllCodigosUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public List<Codigo> saveAllCodigos(List<Codigo> codigos) {
        return codigoRepository.saveAll(codigos);
    }
}
