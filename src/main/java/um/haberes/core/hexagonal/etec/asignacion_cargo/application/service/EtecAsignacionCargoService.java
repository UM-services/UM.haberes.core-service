package um.haberes.core.hexagonal.etec.asignacion_cargo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.application.exception.EtecAsignacionCargoException;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.CreateEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.DeleteEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.GetEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.UpdateEtecAsignacionCargoUseCase;

@Service
@RequiredArgsConstructor
public class EtecAsignacionCargoService {

    private final GetEtecAsignacionCargoUseCase getUseCase;
    private final CreateEtecAsignacionCargoUseCase createUseCase;
    private final UpdateEtecAsignacionCargoUseCase updateUseCase;
    private final DeleteEtecAsignacionCargoUseCase deleteUseCase;

    public EtecAsignacionCargo findById(Long asignacionCargoId) {
        return getUseCase.getById(asignacionCargoId)
                .orElseThrow(() -> new EtecAsignacionCargoException(asignacionCargoId));
    }

    public List<EtecAsignacionCargo> findAllByLegajo(Long legajoId) {
        return getUseCase.getByLegajo(legajoId);
    }

    public List<EtecAsignacionCargo> findAllVigentesByPeriodo(Integer anho, Integer mes) {
        return getUseCase.getVigentesByPeriodo(anho, mes);
    }

    public List<EtecAsignacionCargo> findAllVigentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return getUseCase.getVigentesByLegajoAndPeriodo(legajoId, anho, mes);
    }

    public List<Integer> findAllCategoriasAsignadas() {
        return getUseCase.getCategoriasAsignadas();
    }

    public boolean existsByCategoria(Integer categoriaId) {
        return getUseCase.existsByCategoria(categoriaId);
    }

    public EtecAsignacionCargo add(EtecAsignacionCargo asignacion) {
        return createUseCase.createEtecAsignacionCargo(asignacion);
    }

    public EtecAsignacionCargo update(Long asignacionCargoId, EtecAsignacionCargo asignacion) {
        return updateUseCase.updateEtecAsignacionCargo(asignacionCargoId, asignacion)
                .orElseThrow(() -> new EtecAsignacionCargoException(asignacionCargoId));
    }

    public void delete(Long asignacionCargoId) {
        deleteUseCase.deleteEtecAsignacionCargo(asignacionCargoId);
    }
}
