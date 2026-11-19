package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.GetControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;

@Component
@RequiredArgsConstructor
public class GetControlEtecUseCaseImpl implements GetControlEtecUseCase {

    private final ControlEtecRepository repository;

    @Override
    public Optional<ControlEtec> findByPeriodo(Integer anho, Integer mes) {
        return repository.findByPeriodo(anho, mes);
    }
}
