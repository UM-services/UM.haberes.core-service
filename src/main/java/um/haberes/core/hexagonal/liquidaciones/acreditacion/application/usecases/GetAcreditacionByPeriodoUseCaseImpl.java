package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAcreditacionByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class GetAcreditacionByPeriodoUseCaseImpl implements GetAcreditacionByPeriodoUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public Optional<Acreditacion> getAcreditacionByPeriodo(Integer anho, Integer mes) {
        return acreditacionRepository.findByAnhoAndMes(anho, mes);
    }
}
