package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAllAcreditacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class GetAllAcreditacionesUseCaseImpl implements GetAllAcreditacionesUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public List<Acreditacion> getAllAcreditaciones() {
        return acreditacionRepository.findAll();
    }
}
