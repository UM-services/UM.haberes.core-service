package um.haberes.core.hexagonal.etec.control_etec.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.CopiarControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.GetControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.SaveControlEtecUseCase;

/**
 * Punto de acceso al control ETEC para el resto de core (generación de cargos y motor de
 * liquidación) y para el controller del slice.
 */
@Service
@RequiredArgsConstructor
public class ControlEtecService {

    private final GetControlEtecUseCase getControlEtecUseCase;

    private final SaveControlEtecUseCase saveControlEtecUseCase;

    private final CopiarControlEtecUseCase copiarControlEtecUseCase;

    public Optional<ControlEtec> findByPeriodo(Integer anho, Integer mes) {
        return getControlEtecUseCase.findByPeriodo(anho, mes);
    }

    public ControlEtec save(Integer anho, Integer mes, ControlEtec datos) {
        return saveControlEtecUseCase.save(anho, mes, datos);
    }

    public ControlEtec copiar(Integer anho, Integer mes) {
        return copiarControlEtecUseCase.copiar(anho, mes);
    }
}
