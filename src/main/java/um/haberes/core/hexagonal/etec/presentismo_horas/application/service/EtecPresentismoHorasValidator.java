package um.haberes.core.hexagonal.etec.presentismo_horas.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.AgrupadorHorasEtec;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasConflictoException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

/**
 * Reglas de la pérdida de presentismo ETEC por horas: período abierto, motivo obligatorio,
 * cursos ETEC con horas en el período y una sola pérdida por legajo/período.
 */
@Component
@RequiredArgsConstructor
public class EtecPresentismoHorasValidator {

    private final EtecPresentismoHorasRepository repository;

    private final CursoCargoService cursoCargoService;

    private final AcreditacionService acreditacionService;

    public void validarAlta(EtecPresentismoHoras presentismo) {
        if (presentismo.getLegajoId() == null) {
            throw new EtecPresentismoHorasException("El legajo es obligatorio");
        }
        validarMotivo(presentismo.getMotivo());
        validarPeriodo(presentismo.getAnho(), presentismo.getMes());
        validarPeriodoAbierto(presentismo.getAnho(), presentismo.getMes());
        if (!tieneCargosHoras(presentismo.getLegajoId(), presentismo.getAnho(), presentismo.getMes())) {
            throw new EtecPresentismoHorasException(
                    "El legajo no tiene cargos ETEC por horas con horas en el período "
                            + presentismo.getMes() + "/" + presentismo.getAnho());
        }
        if (repository.existsByLegajoIdAndPeriodo(
                presentismo.getLegajoId(), presentismo.getAnho(), presentismo.getMes())) {
            throw new EtecPresentismoHorasConflictoException(
                    "El legajo ya tiene registrada la pérdida de presentismo por horas para ese período");
        }
    }

    public void validarMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new EtecPresentismoHorasException(
                    "El motivo es obligatorio para registrar la pérdida de presentismo por horas");
        }
        if (motivo.trim().length() > 255) {
            throw new EtecPresentismoHorasException("El motivo no puede superar los 255 caracteres");
        }
    }

    public void validarPeriodo(Integer anho, Integer mes) {
        if (anho == null || mes == null || anho < 2000 || anho > 2100 || mes < 1 || mes > 12) {
            throw new EtecPresentismoHorasException("El período informado no es válido");
        }
    }

    public void validarPeriodoAbierto(Integer anho, Integer mes) {
        try {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, mes);
            if (acreditacion.getAcreditado() != null && acreditacion.getAcreditado() == 1) {
                throw new EtecPresentismoHorasConflictoException(
                        "El período " + mes + "/" + anho
                                + " está acreditado: no se puede modificar el presentismo");
            }
        } catch (AcreditacionException e) {
            // Sin registro de acreditación el período está abierto.
        }
    }

    /**
     * Reutiliza la regla de {@link AgrupadorHorasEtec}: el legajo tiene cargos por horas si
     * sus cursos de la facultad 15 suman horas semanales mayores a cero en alguna sede.
     */
    public boolean tieneCargosHoras(Long legajoId, Integer anho, Integer mes) {
        if (legajoId == null) {
            return false;
        }
        try {
            List<CursoCargo> cursos = cursoCargoService.findAllByLegajo(legajoId, anho, mes);
            Map<Integer, BigDecimal> sedes = AgrupadorHorasEtec
                    .porLegajoYSede(cursos, legajoId)
                    .getOrDefault(legajoId, Map.of());
            return sedes.values().stream()
                    .anyMatch(horas -> horas != null && horas.compareTo(BigDecimal.ZERO) > 0);
        } catch (EscalaHoraEtecException e) {
            throw new EtecPresentismoHorasException(
                    "No se pudieron leer las horas ETEC del legajo: " + e.getMessage());
        }
    }
}
