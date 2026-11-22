package um.haberes.core.hexagonal.etec.presentismo.application.service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoConflictoException;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

/**
 * Reglas de la pérdida de presentismo ETEC: período abierto, asignación vigente en el
 * período, motivo obligatorio y una sola pérdida por asignación/período.
 */
@Component
@RequiredArgsConstructor
public class EtecPresentismoValidator {

    private final EtecPresentismoRepository presentismoRepository;

    private final EtecAsignacionCargoRepository asignacionRepository;

    private final AcreditacionService acreditacionService;

    public EtecAsignacionCargo validarAlta(EtecPresentismo presentismo) {
        validarMotivo(presentismo.getMotivo());
        validarPeriodo(presentismo.getAnho(), presentismo.getMes());
        validarPeriodoAbierto(presentismo.getAnho(), presentismo.getMes());

        EtecAsignacionCargo asignacion = validarAsignacionVigente(
                presentismo.getAsignacionCargoId(), presentismo.getAnho(), presentismo.getMes());

        if (presentismoRepository.existsByAsignacionCargoIdAndPeriodo(
                presentismo.getAsignacionCargoId(), presentismo.getAnho(), presentismo.getMes())) {
            throw new EtecPresentismoConflictoException(
                    "El cargo ETEC ya tiene registrada la pérdida de presentismo para ese período");
        }
        return asignacion;
    }

    public void validarMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new EtecPresentismoException("El motivo es obligatorio para registrar la pérdida de presentismo");
        }
        if (motivo.trim().length() > 255) {
            throw new EtecPresentismoException("El motivo no puede superar los 255 caracteres");
        }
    }

    public void validarPeriodo(Integer anho, Integer mes) {
        if (anho == null || mes == null || anho < 2000 || anho > 2100 || mes < 1 || mes > 12) {
            throw new EtecPresentismoException("El período informado no es válido");
        }
    }

    /**
     * Un período acreditado queda cerrado para altas, cambios y bajas, igual que las
     * novedades. Si todavía no hay registro de acreditación, el período está abierto.
     */
    public void validarPeriodoAbierto(Integer anho, Integer mes) {
        try {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, mes);
            if (acreditacion.getAcreditado() != null && acreditacion.getAcreditado() == 1) {
                throw new EtecPresentismoConflictoException(
                        "El período " + mes + "/" + anho + " está acreditado: no se puede modificar el presentismo");
            }
        } catch (AcreditacionException e) {
            // Sin registro de acreditación el período está abierto.
        }
    }

    /**
     * La asignación debe existir y estar vigente durante todo el período, con la misma
     * regla que usa la generación de cargos ETEC. Si no, la pérdida no tendría efecto en
     * la liquidación.
     */
    private EtecAsignacionCargo validarAsignacionVigente(Long asignacionCargoId, Integer anho, Integer mes) {
        if (asignacionCargoId == null) {
            throw new EtecPresentismoException("La asignación de cargo ETEC es obligatoria");
        }
        EtecAsignacionCargo asignacion = asignacionRepository.findById(asignacionCargoId)
                .orElseThrow(() -> new EtecPresentismoException(
                        "No existe la asignación de cargo ETEC " + asignacionCargoId));

        boolean vigente = asignacionRepository
                .findAllVigentesByLegajoAndPeriodo(asignacion.getLegajoId(), anho, mes).stream()
                .anyMatch(item -> asignacionCargoId.equals(item.getAsignacionCargoId()));
        if (!vigente) {
            throw new EtecPresentismoException(
                    "El cargo ETEC no está vigente en el período " + mes + "/" + anho
                            + ": no se puede registrar la pérdida de presentismo");
        }
        return asignacion;
    }
}
