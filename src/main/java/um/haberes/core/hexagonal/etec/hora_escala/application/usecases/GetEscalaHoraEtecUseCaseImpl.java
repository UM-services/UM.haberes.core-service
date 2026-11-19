package um.haberes.core.hexagonal.etec.hora_escala.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in.GetEscalaHoraEtecUseCase;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;

@Component
@RequiredArgsConstructor
public class GetEscalaHoraEtecUseCaseImpl implements GetEscalaHoraEtecUseCase {

    private final ControlEtecRepository controlEtecRepository;

    private final EscalaHoraEtecRepository repository;

    @Override
    public List<EscalaHoraEtec> findByPeriodo(Integer anho, Integer mes) {
        return controlEtecRepository.findByPeriodo(anho, mes)
                .map(ControlEtec::getControlEtecId)
                .map(repository::findAllByControlEtecId)
                .orElseGet(List::of);
    }
}
