package um.haberes.core.hexagonal.geografica.application.service;

import um.haberes.core.hexagonal.geografica.application.exception.GeograficaException;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.domain.ports.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeograficaService {

    private final CreateGeograficaUseCase createGeograficaUseCase;
    private final GetAllGeograficasUseCase getAllGeograficasUseCase;
    private final GetGeograficaByIdUseCase getGeograficaByIdUseCase;
    private final GetGeograficasByIdsUseCase getGeograficasByIdsUseCase;
    private final UpdateGeograficaUseCase updateGeograficaUseCase;
    private final DeleteGeograficaUseCase deleteGeograficaUseCase;

    public Geografica createGeografica(Geografica geografica) {
        return createGeograficaUseCase.createGeografica(geografica);
    }

    public List<Geografica> getAllGeograficas() {
        return getAllGeograficasUseCase.getAllGeograficas();
    }

    public Geografica getGeograficaById(Integer id) {
        return getGeograficaByIdUseCase.getGeograficaById(id)
                .orElseThrow(() -> new GeograficaException(id));
    }

    public List<Geografica> getGeograficasByIds(List<Integer> ids) {
        return getGeograficasByIdsUseCase.getGeograficasByIds(ids);
    }

    public Geografica updateGeografica(Integer id, Geografica geografica) {
        return updateGeograficaUseCase.updateGeografica(id, geografica)
                .orElseThrow(() -> new GeograficaException(id));
    }

    public boolean deleteGeografica(Integer id) {
        return deleteGeograficaUseCase.deleteGeografica(id);
    }
}
