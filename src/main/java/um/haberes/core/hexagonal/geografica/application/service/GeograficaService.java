package um.haberes.core.hexagonal.geografica.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeograficaService {

    private final GetGeograficaByIdUseCase getGeograficaByIdUseCase;
    private final GetAllGeograficasUseCase getAllGeograficasUseCase;
    private final UpdateGeograficaUseCase updateGeograficaUseCase;

    public Geografica getGeograficaById(Integer id) {
        return getGeograficaByIdUseCase.getById(id);
    }

    public List<Geografica> getAllGeograficas() {
        return getAllGeograficasUseCase.getAll();
    }

    public Geografica updateGeografica(Integer id, Geografica geografica) {
        return updateGeograficaUseCase.update(id, geografica);
    }
}
