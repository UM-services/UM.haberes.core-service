package um.haberes.core.hexagonal.geografica.application.service;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.*;
import um.haberes.core.hexagonal.geografica.domain.ports.out.GeograficaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeograficaService {

    private final GeograficaRepository geograficaRepository;

    private final GetAllGeograficasUseCase getAllGeograficasUseCase;
    private final GetGeograficaByIdUseCase getGeograficaByIdUseCase;
    private final GetGeograficasByIdsUseCase getGeograficasByIdsUseCase;
    private final UpdateGeograficaUseCase updateGeograficaUseCase;

    public List<Geografica> getAllGeograficas() {
        return getAllGeograficasUseCase.getAllGeograficas();
    }

    public Optional<Geografica> getGeograficaById(Integer id) {
        return getGeograficaByIdUseCase.getGeograficaById(id);
    }

    public List<Geografica> getGeograficasByIds(List<Integer> ids) {
        return getGeograficasByIdsUseCase.getGeograficasByIds(ids);
    }

    public Optional<Geografica> updateGeografica(Integer id, Geografica geografica) {
        return updateGeograficaUseCase.updateGeografica(id, geografica);
    }
}
