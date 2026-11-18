package um.haberes.core.hexagonal.etec.arraigo_escala.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.GetEscalaArraigoEtecUseCase;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.out.EscalaArraigoEtecRepository;

@Component
@RequiredArgsConstructor
public class GetEscalaArraigoEtecUseCaseImpl implements GetEscalaArraigoEtecUseCase {

    private final EscalaArraigoEtecRepository repository;

    @Override
    public List<EscalaArraigoEtec> findAll() {
        return repository.findAll();
    }
}
