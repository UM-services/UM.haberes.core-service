package um.haberes.core.service.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.LegajoControlException;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.exception.CursoCargoException;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.model.CursoCargoNovedadEntity;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.CursoCargoNovedadService;
import um.haberes.core.service.LegajoControlService;

/**
 * Resolución de las novedades de cursos (altas, bajas y cambios) que envían las
 * facultades.
 * <p>
 * Absorbe en el servidor la cascada que el cliente legacy ejecutaba paso a paso
 * (novedad + legajo_control + curso_cargo + depuración de la fusión) para que la
 * autorización sea atómica: o queda aplicada completa, o no queda nada.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CursoCargoNovedadToolService {

    private final CursoCargoNovedadService novedadService;
    private final CursoCargoService cursoCargoService;
    private final LegajoControlService legajoControlService;
    private final DesignacionToolService designacionToolService;

    /**
     * Autoriza todas las novedades pendientes del mismo legajo/curso/período:
     * aplica el alta, la baja o el cambio y deja el legajo marcado para re-fusionar.
     */
    @Transactional
    public void autorizar(Long cursoCargoNovedadId, String respuesta) {
        CursoCargoNovedadEntity referencia = novedadService.findByCursoCargoNovedadId(cursoCargoNovedadId);
        log.debug("Autorizando novedades -> legajoId={} cursoId={} periodo={}/{}",
                referencia.getLegajoId(), referencia.getCursoId(), referencia.getMes(), referencia.getAnho());

        for (CursoCargoNovedadEntity novedad : novedadService.findAllPendientesLegajo(referencia.getLegajoId(),
                referencia.getCursoId(), referencia.getAnho(), referencia.getMes())) {
            novedad.setAutorizado((byte) 1);
            novedad.setRespuesta(respuesta);
            novedad = novedadService.update(novedad, novedad.getCursoCargoNovedadId());

            marcarNoFusionado(novedad.getLegajoId(), novedad.getAnho(), novedad.getMes());

            if (novedad.getBaja() == 1) {
                eliminarCargo(novedad);
            }
            if (novedad.getCambio() == 1) {
                aplicarCambio(novedad);
            }
            if (novedad.getAlta() == 1) {
                crearCargo(novedad);
            }

            designacionToolService.depurarFusionByLegajo(novedad.getLegajoId(), novedad.getAnho(), novedad.getMes());
        }
    }

    /** Rechaza todas las novedades pendientes del mismo legajo/curso/período. */
    @Transactional
    public void rechazar(Long cursoCargoNovedadId, String respuesta) {
        CursoCargoNovedadEntity referencia = novedadService.findByCursoCargoNovedadId(cursoCargoNovedadId);
        log.debug("Rechazando novedades -> legajoId={} cursoId={} periodo={}/{}",
                referencia.getLegajoId(), referencia.getCursoId(), referencia.getMes(), referencia.getAnho());

        for (CursoCargoNovedadEntity novedad : novedadService.findAllPendientesLegajo(referencia.getLegajoId(),
                referencia.getCursoId(), referencia.getAnho(), referencia.getMes())) {
            novedad.setRechazado((byte) 1);
            novedad.setRespuesta(respuesta);
            novedadService.update(novedad, novedad.getCursoCargoNovedadId());
        }
    }

    /**
     * Revierte lo autorizado o limpia el rechazo de una novedad, deshaciendo el
     * efecto sobre curso_cargo (el cambio vuelve a alternar el desarraigo, como el
     * cliente legacy).
     */
    @Transactional
    public void anular(Long cursoCargoNovedadId) {
        CursoCargoNovedadEntity referencia = novedadService.findByCursoCargoNovedadId(cursoCargoNovedadId);
        log.debug("Anulando novedades -> legajoId={} cursoId={} periodo={}/{}",
                referencia.getLegajoId(), referencia.getCursoId(), referencia.getMes(), referencia.getAnho());

        if (referencia.getAutorizado() == 1) {
            for (CursoCargoNovedadEntity novedad : novedadService.findAllAutorizadosLegajo(referencia.getLegajoId(),
                    referencia.getCursoId(), referencia.getAnho(), referencia.getMes())) {
                novedad.setAutorizado((byte) 0);
                novedad.setRespuesta("");
                novedadService.update(novedad, novedad.getCursoCargoNovedadId());

                if (novedad.getAlta() == 1) {
                    eliminarCargo(novedad);
                }
                if (novedad.getCambio() == 1) {
                    revertirCambio(novedad);
                }
                if (novedad.getBaja() == 1) {
                    crearCargo(novedad);
                }
            }
        }

        if (referencia.getRechazado() == 1) {
            for (CursoCargoNovedadEntity novedad : novedadService.findAllRechazadosLegajo(referencia.getLegajoId(),
                    referencia.getCursoId(), referencia.getAnho(), referencia.getMes())) {
                novedad.setRechazado((byte) 0);
                novedad.setRespuesta("");
                novedadService.update(novedad, novedad.getCursoCargoNovedadId());
            }
        }
    }

    // ------------------------------------------------------------------
    // Efectos sobre curso_cargo
    // ------------------------------------------------------------------

    private void crearCargo(CursoCargoNovedadEntity novedad) {
        try {
            CursoCargo existente = cursoCargoService.findByUnique(novedad.getCursoId(), novedad.getAnho(),
                    novedad.getMes(), novedad.getCargoTipoId(), novedad.getLegajoId());
            log.debug("El cargo ya existe, se ajusta -> {}", existente.getCursoCargoId());
            existente.setHorasSemanales(novedad.getHorasSemanales());
            existente.setDesarraigo(novedad.getDesarraigo());
            existente.setCursoCargoNovedadId(novedad.getCursoCargoNovedadId());
            cursoCargoService.update(existente, existente.getCursoCargoId());
            return;
        } catch (CursoCargoException e) {
            log.debug("Alta de cargo nuevo -> {}", e.getMessage());
        }

        CursoCargo cursoCargo = CursoCargo.builder()
                .cursoId(novedad.getCursoId())
                .anho(novedad.getAnho())
                .mes(novedad.getMes())
                .cargoTipoId(novedad.getCargoTipoId())
                .legajoId(novedad.getLegajoId())
                .horasSemanales(novedad.getHorasSemanales())
                .desarraigo(novedad.getDesarraigo())
                .cursoCargoNovedadId(novedad.getCursoCargoNovedadId())
                .build();
        cursoCargoService.add(cursoCargo);
    }

    private void aplicarCambio(CursoCargoNovedadEntity novedad) {
        try {
            CursoCargo cursoCargo = cursoCargoService.findByUnique(novedad.getCursoId(), novedad.getAnho(),
                    novedad.getMes(), novedad.getCargoTipoId(), novedad.getLegajoId());
            cursoCargo.setDesarraigo(novedad.getDesarraigo());
            cursoCargo.setHorasSemanales(novedad.getHorasSemanales());
            cursoCargoService.update(cursoCargo, cursoCargo.getCursoCargoId());
        } catch (CursoCargoException e) {
            log.warn("No se pudo aplicar el cambio de la novedad {}: {}", novedad.getCursoCargoNovedadId(),
                    e.getMessage());
        }
    }

    /** Reversión del cambio: el legacy vuelve a alternar el desarraigo. */
    private void revertirCambio(CursoCargoNovedadEntity novedad) {
        try {
            CursoCargo cursoCargo = cursoCargoService.findByUnique(novedad.getCursoId(), novedad.getAnho(),
                    novedad.getMes(), novedad.getCargoTipoId(), novedad.getLegajoId());
            byte desarraigo = cursoCargo.getDesarraigo() == null ? 0 : cursoCargo.getDesarraigo();
            cursoCargo.setDesarraigo((byte) (1 - desarraigo));
            cursoCargoService.update(cursoCargo, cursoCargo.getCursoCargoId());
        } catch (CursoCargoException e) {
            log.warn("No se pudo revertir el cambio de la novedad {}: {}", novedad.getCursoCargoNovedadId(),
                    e.getMessage());
        }
    }

    private void eliminarCargo(CursoCargoNovedadEntity novedad) {
        cursoCargoService.deleteByUnique(novedad.getCursoId(), novedad.getAnho(), novedad.getMes(),
                novedad.getCargoTipoId(), novedad.getLegajoId());
    }

    /**
     * Deja el legajo marcado para volver a fusionar (el conteo de fusiones cambió).
     * Crea la fila de control del período si todavía no existe.
     */
    private void marcarNoFusionado(Long legajoId, Integer anho, Integer mes) {
        LegajoControlEntity legajoControl;
        try {
            legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControl = new LegajoControlEntity();
            legajoControl.setLegajoId(legajoId);
            legajoControl.setAnho(anho);
            legajoControl.setMes(mes);
        }
        legajoControl.setFusionado((byte) 0);
        legajoControlService.save(legajoControl);
    }

}
