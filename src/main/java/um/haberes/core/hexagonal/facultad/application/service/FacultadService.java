package um.haberes.core.hexagonal.facultad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.domain.ports.in.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultadService {

    private final GetFacultadByIdUseCase getFacultadByIdUseCase;
    private final GetAllFacultadesUseCase getAllFacultadesUseCase;
    private final GetFacultadesFiltradasUseCase getFacultadesFiltradasUseCase;

    public Facultad getFacultadById(Integer id) {
        return getFacultadByIdUseCase.getById(id);
    }

    public List<Facultad> getAllFacultades() {
        return getAllFacultadesUseCase.getAll();
    }

    public List<Facultad> getFacultadesEstaticas() {
        return getFacultadesFiltradasUseCase.getFacultadesFiltradas();
    }
}
