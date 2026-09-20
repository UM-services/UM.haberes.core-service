package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.CreateAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.DeleteAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAllAcreditacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAcreditacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAcreditacionByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.UpdateAcreditacionUseCase;

@Service
@RequiredArgsConstructor
public class AcreditacionService {

    private final GetAllAcreditacionesUseCase getAllAcreditacionesUseCase;
    private final GetAcreditacionByIdUseCase getAcreditacionByIdUseCase;
    private final GetAcreditacionByPeriodoUseCase getAcreditacionByPeriodoUseCase;
    private final CreateAcreditacionUseCase createAcreditacionUseCase;
    private final UpdateAcreditacionUseCase updateAcreditacionUseCase;
    private final DeleteAcreditacionUseCase deleteAcreditacionUseCase;

    public List<Acreditacion> findAll() {
        return getAllAcreditacionesUseCase.getAllAcreditaciones();
    }

    public Acreditacion findByAcreditacionId(Long acreditacionId) {
        return getAcreditacionByIdUseCase.getAcreditacionById(acreditacionId)
                .orElseThrow(() -> new AcreditacionException(acreditacionId));
    }

    public Acreditacion findByPeriodo(Integer anho, Integer mes) {
        return getAcreditacionByPeriodoUseCase.getAcreditacionByPeriodo(anho, mes)
                .orElseThrow(() -> new AcreditacionException(anho, mes));
    }

    public Acreditacion add(Acreditacion acreditacion) {
        return createAcreditacionUseCase.createAcreditacion(acreditacion);
    }

    public Acreditacion update(Acreditacion acreditacion, Long acreditacionId) {
        return updateAcreditacionUseCase.updateAcreditacion(acreditacionId, acreditacion)
                .orElseThrow(() -> new AcreditacionException(acreditacionId));
    }

    public void delete(Long acreditacionId) {
        deleteAcreditacionUseCase.deleteAcreditacion(acreditacionId);
    }
}
