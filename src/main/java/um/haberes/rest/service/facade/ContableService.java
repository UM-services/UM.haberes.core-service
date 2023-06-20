/**
 *
 */
package um.haberes.rest.service.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import um.haberes.rest.exception.LiquidacionAdicionalException;
import um.haberes.rest.kotlin.model.*;
import um.haberes.rest.kotlin.model.extern.CuentaMovimiento;
import um.haberes.rest.service.*;
import um.haberes.rest.service.extern.CuentaMovimientoService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ItemException;
import um.haberes.rest.exception.LegajoContabilidadException;
import um.haberes.rest.exception.LiquidacionException;
import um.haberes.rest.model.Actividad;
import um.haberes.rest.model.CargoClaseDetalle;
import um.haberes.rest.model.CargoClaseImputacion;
import um.haberes.rest.model.CodigoImputacion;
import um.haberes.rest.model.Item;
import um.haberes.rest.model.LegajoCargoClaseImputacion;
import um.haberes.rest.model.LegajoCodigoImputacion;
import um.haberes.rest.model.LegajoContabilidad;
import um.haberes.rest.util.Proporcion;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 */

@Service
@Slf4j
public class ContableService {

    @Autowired
    private LegajoCargoClaseImputacionService legajoCargoClaseImputacionService;

    @Autowired
    private LegajoCategoriaImputacionService legajoCategoriaImputacionService;

    @Autowired
    private LegajoCodigoImputacionService legajoCodigoImputacionService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private DesignacionToolService designacionToolService;

    @Autowired
    private LiquidacionService liquidacionService;

    @Autowired
    private CargoLiquidacionService cargoLiquidacionService;

    @Autowired
    private CategoriaImputacionService categoriaImputacionService;

    @Autowired
    private CargoClaseDetalleService cargoClaseDetalleService;

    @Autowired
    private CargoClaseImputacionService cargoClaseImputacionService;

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private CodigoService codigoService;

    @Autowired
    private CodigoImputacionService codigoImputacionService;

    @Autowired
    private LegajoContabilidadService legajoContabilidadService;

    @Autowired
    private CuentaMovimientoService cuentaMovimientoService;

    @Autowired
    private CodigoGrupoService codigoGrupoService;

    @Autowired
    private LiquidacionAdicionalService liquidacionAdicionalService;

    @Transactional
    public void generateByLegajo(Long legajoId, Integer anho, Integer mes) {
        Liquidacion liquidacion = null;
        try {
            liquidacion = liquidacionService.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
        } catch (LiquidacionException e) {
            liquidacion = new Liquidacion();
        }
        Map<String, CategoriaImputacion> categoriaImputaciones = categoriaImputacionService.findAll().stream()
                .collect(Collectors.toMap(CategoriaImputacion::key, imputacion -> imputacion));
        Map<String, CargoClaseImputacion> cargoClaseImputaciones = cargoClaseImputacionService.findAll().stream()
                .collect(Collectors.toMap(CargoClaseImputacion::key, imputacion -> imputacion));
        Map<String, CodigoImputacion> codigoImputaciones = codigoImputacionService.findAll().stream()
                .collect(Collectors.toMap(CodigoImputacion::key, imputacion -> imputacion));
        Map<Integer, Codigo> codigos = codigoService.findAll().stream()
                .collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo));

        Boolean docenteEtec = false;
        try {
            itemService.findByUnique(legajoId, anho, mes, 29);
            docenteEtec = true;
        } catch (ItemException e) {
            log.debug("Sin ETEC");
        }

        List<BigDecimal> indices = designacionToolService.indiceAntiguedad(legajoId, anho, mes);
        BigDecimal indiceAntiguedad = indices.get(0);
        BigDecimal anhos = indices.get(1);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalAdministrativo = BigDecimal.ZERO;
        BigDecimal totalDocente = BigDecimal.ZERO;
        BigDecimal totalRemunerativo = BigDecimal.ZERO;
        BigDecimal totalNoRemunerativo = BigDecimal.ZERO;
        Boolean sinBasico = true;

        Map<String, Proporcion> proporciones = new HashMap<String, Proporcion>();
        Map<String, Proporcion> proporcionesAdministrativo = new HashMap<String, Proporcion>();
        Map<String, Proporcion> proporcionesDocente = new HashMap<String, Proporcion>();

        BigDecimal remunerativo = liquidacion.getTotalRemunerativo();

        deleteAllByLegajo(legajoId, anho, mes);

        if (remunerativo.compareTo(BigDecimal.ZERO) == 0)
            return;

        // Control de adicional horario para aplicar una sola vez
        Map<String, LiquidacionAdicional> liquidacionAdicionalMap = new HashMap<>();

        for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes)) {
            Dependencia dependencia = cargoLiquidacion.getDependencia();
            Byte docente = cargoLiquidacion.getCategoria().getDocente();
            String key = dependencia.getDependenciaId() + "." + dependencia.getFacultadId() + "."
                    + dependencia.getGeograficaId() + "." + cargoLiquidacion.getCategoriaId();
            if (!categoriaImputaciones.containsKey(key)) {
                CategoriaImputacion imputacion = categoriaImputacionService
                        .add(new CategoriaImputacion(null, dependencia.getDependenciaId(), dependencia.getFacultadId(),
                                dependencia.getGeograficaId(), cargoLiquidacion.getCategoriaId(), null, null));
                categoriaImputaciones.put(key,
                        categoriaImputacionService.update(
                                new CategoriaImputacion(imputacion.getCategoriaImputacionId(),
                                        dependencia.getDependenciaId(), dependencia.getFacultadId(),
                                        dependencia.getGeograficaId(), cargoLiquidacion.getCategoriaId(),
                                        new BigDecimal(imputacion.getCategoriaImputacionId()),
                                        new BigDecimal(imputacion.getCategoriaImputacionId())),
                                imputacion.getCategoriaImputacionId()));
            }
            CategoriaImputacion categoriaImputacion = categoriaImputaciones.get(key);

            Proporcion proporcion = null;
            Proporcion proporcionDocente = null;
            Proporcion proporcionAdministrativo = null;

            key = dependencia.getDependenciaId() + "." + dependencia.getFacultadId() + "."
                    + dependencia.getGeograficaId() + "." + docente;
            // Proporción General
            if (!proporciones.containsKey(key)) {
                proporciones.put(key, new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                        dependencia.getGeograficaId(), docente, BigDecimal.ZERO, BigDecimal.ZERO));
            }
            proporcion = proporciones.get(key);

            // Proporción Docente
            if (docente == 1) {
                if (!proporcionesDocente.containsKey(key)) {
                    proporcionesDocente.put(key,
                            new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                                    dependencia.getGeograficaId(), docente, BigDecimal.ZERO, BigDecimal.ZERO));
                }
                proporcionDocente = proporcionesDocente.get(key);
            }
            // Proporción No Docente
            if (docente == 0) {
                if (!proporcionesAdministrativo.containsKey(key)) {
                    proporcionesAdministrativo.put(key,
                            new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                                    dependencia.getGeograficaId(), docente, BigDecimal.ZERO, BigDecimal.ZERO));
                }
                proporcionAdministrativo = proporcionesAdministrativo.get(key);
            }

            BigDecimal multiplicador = new BigDecimal(cargoLiquidacion.getJornada());
            if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) != 0) {
                multiplicador = cargoLiquidacion.getHorasJornada();
            }
            log.debug("Multiplicador={}", multiplicador);

            LegajoCategoriaImputacion imputacion = new LegajoCategoriaImputacion(null, cargoLiquidacion.getLegajoId(),
                    cargoLiquidacion.getAnho(), cargoLiquidacion.getMes(), dependencia.getDependenciaId(),
                    dependencia.getFacultadId(), dependencia.getGeograficaId(), cargoLiquidacion.getCategoriaId(),
                    categoriaImputacion.getCuentaSueldos(),
                    cargoLiquidacion.getCategoriaBasico().multiply(multiplicador)
                            .setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.ZERO, categoriaImputacion.getCuentaAportes(), null, null, null, null, null);

            BigDecimal basico = imputacion.getBasico();
            if (cargoLiquidacion.getCategoria().getDocente() == 1) {
                imputacion.setAntiguedad(basico.multiply(indiceAntiguedad).setScale(2, RoundingMode.HALF_UP));
            } else {
                imputacion.setAntiguedad(basico.multiply(anhos).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
            }
            // Toma el adicional de carga horaria si está disponible
            String keyAdicional = legajoId + "." + anho + "." + mes + "." + dependencia.getDependenciaId();
            try {
                if (!liquidacionAdicionalMap.containsKey(keyAdicional)) {
                    LiquidacionAdicional liquidacionAdicional = liquidacionAdicionalService.findByDependencia(legajoId, anho, mes, dependencia.getDependenciaId());
                    liquidacionAdicionalMap.put(keyAdicional, liquidacionAdicional);
                    basico = basico.add(liquidacionAdicional.getAdicional()).setScale(2, RoundingMode.HALF_UP);
                    imputacion.setBasico(basico);
                }
            } catch (LiquidacionAdicionalException e) {
            }

            // registra imputacion
            if (basico.compareTo(BigDecimal.ZERO) != 0) {
                imputacion = legajoCategoriaImputacionService.add(imputacion);
            }

            proporcion.setTotal(proporcion.getTotal().add(basico));
            total = total.add(basico);
            if (docente == 1) {
                proporcionDocente.setTotal(proporcionDocente.getTotal().add(basico));
                totalDocente = totalDocente.add(basico);
            }
            if (docente == 0) {
                proporcionAdministrativo.setTotal(proporcionAdministrativo.getTotal().add(basico));
                totalAdministrativo = totalAdministrativo.add(basico);
            }
            totalRemunerativo = totalRemunerativo.add(basico).add(imputacion.getAntiguedad());
        }

        for (CargoClaseDetalle detalle : cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes)) {
            CargoClaseImputacion cargoClaseImputacion = null;
            String key = detalle.getDependenciaId() + "." + detalle.getFacultadId() + "." + detalle.getGeograficaId()
                    + "." + detalle.getCargoClaseId();
            if (!cargoClaseImputaciones.containsKey(key)) {
                CargoClaseImputacion imputacion = cargoClaseImputacionService
                        .add(new CargoClaseImputacion(null, detalle.getDependenciaId(), detalle.getFacultadId(),
                                detalle.getGeograficaId(), detalle.getCargoClaseId(), null, null));
                cargoClaseImputaciones.put(key, cargoClaseImputacionService.update(
                        new CargoClaseImputacion(imputacion.getCargoClaseImputacionId(), detalle.getDependenciaId(),
                                detalle.getFacultadId(), detalle.getGeograficaId(), detalle.getCargoClaseId(),
                                new BigDecimal(imputacion.getCargoClaseImputacionId()),
                                new BigDecimal(imputacion.getCargoClaseImputacionId())),
                        imputacion.getCargoClaseImputacionId()));
            }
            cargoClaseImputacion = cargoClaseImputaciones.get(key);

            Proporcion proporcion = null;
            Proporcion proporcionDocente = null;

            Byte docente = 1;
            key = detalle.getDependenciaId() + "." + detalle.getFacultadId() + "." + detalle.getGeograficaId() + "."
                    + docente;
            // Proporción General
            if (!proporciones.containsKey(key)) {
                proporciones.put(key, new Proporcion(detalle.getDependenciaId(), detalle.getFacultadId(),
                        detalle.getGeograficaId(), docente, BigDecimal.ZERO, BigDecimal.ZERO));
            }
            proporcion = proporciones.get(key);

            // Proporción Docente
            if (!proporcionesDocente.containsKey(key)) {
                proporcionesDocente.put(key, new Proporcion(detalle.getDependenciaId(), detalle.getFacultadId(),
                        detalle.getGeograficaId(), docente, BigDecimal.ZERO, BigDecimal.ZERO));
            }
            proporcionDocente = proporcionesDocente.get(key);

            BigDecimal basico = detalle.getValorHora()
                    .multiply(new BigDecimal(detalle.getHoras()).setScale(2, RoundingMode.HALF_UP));
            LegajoCargoClaseImputacion imputacion = new LegajoCargoClaseImputacion(null, detalle.getLegajoId(),
                    detalle.getAnho(), detalle.getMes(), detalle.getDependenciaId(), detalle.getFacultadId(),
                    detalle.getGeograficaId(), detalle.getCargoClaseId(), cargoClaseImputacion.getCuentaSueldos(),
                    basico, basico.multiply(indiceAntiguedad).setScale(2, RoundingMode.HALF_UP),
                    cargoClaseImputacion.getCuentaAportes());
            if (basico.compareTo(BigDecimal.ZERO) != 0) {
                legajoCargoClaseImputacionService.add(imputacion);
                proporcion.setTotal(proporcion.getTotal().add(imputacion.getBasico()));
                total = total.add(imputacion.getBasico());
                proporcionDocente.setTotal(proporcionDocente.getTotal().add(imputacion.getBasico()));
                totalDocente = totalDocente.add(imputacion.getBasico());
                totalRemunerativo = totalRemunerativo.add(imputacion.getBasico()).add(imputacion.getAntiguedad());
            }
        }
        if (proporciones.size() == 0 && proporcionesDocente.size() == 0 && proporcionesAdministrativo.size() == 0) {
            Actividad actividad = actividadService.findByUnique(legajoId, anho, mes);
            if (actividad.getDependenciaId() == null) {
                if (docenteEtec) {
                    actividad.setDependenciaId(46);
                } else {
                    actividad.setDependenciaId(10);
                }
                actividad = actividadService.update(actividad, actividad.getActividadId());
            }
            Dependencia dependencia = actividad.getDependencia();
            if (dependencia.getFacultadId() > 0) {
                String key = dependencia.getDependenciaId() + "." + dependencia.getFacultadId() + "."
                        + dependencia.getGeograficaId() + ".0";
                proporciones.put(key, new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                        dependencia.getGeograficaId(), (byte) 0, BigDecimal.ZERO, BigDecimal.ZERO));
                proporcionesAdministrativo.put(key,
                        new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                                dependencia.getGeograficaId(), (byte) 0, BigDecimal.ZERO, BigDecimal.ZERO));
                key = dependencia.getDependenciaId() + "." + dependencia.getFacultadId() + "."
                        + dependencia.getGeograficaId() + ".1";
                proporcionesDocente.put(key, new Proporcion(dependencia.getDependenciaId(), dependencia.getFacultadId(),
                        dependencia.getGeograficaId(), (byte) 1, BigDecimal.ZERO, BigDecimal.ZERO));
            }
            sinBasico = false;
        }
        for (Proporcion proporcion : proporciones.values()) {
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                proporcion.setPorcentaje(BigDecimal.ONE);
            } else {
                proporcion.setPorcentaje(proporcion.getTotal().divide(total, 5, RoundingMode.HALF_UP));
            }
        }
        for (Proporcion proporcionDocente : proporcionesDocente.values()) {
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                proporcionDocente.setPorcentaje(BigDecimal.ONE);
            } else {
                proporcionDocente
                        .setPorcentaje(proporcionDocente.getTotal().divide(totalDocente, 5, RoundingMode.HALF_UP));
            }
        }
        for (Proporcion proporcionAdministrativo : proporcionesAdministrativo.values()) {
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                proporcionAdministrativo.setPorcentaje(BigDecimal.ONE);
            } else {
                proporcionAdministrativo.setPorcentaje(
                        proporcionAdministrativo.getTotal().divide(totalAdministrativo, 5, RoundingMode.HALF_UP));
            }
        }

        List<Integer> codigoIdRemunerativos = codigoGrupoService.findAllByRemunerativo((byte) 1).stream().map(codigo -> codigo.getCodigoId()).collect(Collectors.toList());
        List<Integer> codigoIdNoRemunerativos = codigoGrupoService.findAllByNoRemunerativo((byte) 1).stream().map(codigo -> codigo.getCodigoId()).collect(Collectors.toList());
        List<Integer> codigoIds = Stream.concat(codigoIdRemunerativos.stream(), codigoIdNoRemunerativos.stream()).collect(Collectors.toList());
        if (sinBasico) {
            codigoIds.removeIf(codigoId -> codigoId < 3);
        }

        for (Item item : itemService.findAllCodigosByLegajo(legajoId, anho, mes, codigoIds)) {
            Codigo codigo = new Codigo();
            if (codigos.containsKey(item.getCodigoId())) {
                codigo = codigos.get(item.getCodigoId());
            }

            // Si el código es docente y no docente
            if ((codigo.getDocente() == 0 && codigo.getNoDocente() == 0)
                    || (codigo.getDocente() == 1 && codigo.getNoDocente() == 1)) {
                log.debug("Proporciones={}", proporciones);
                for (Proporcion proporcion : proporciones.values()) {
                    String key = proporcion.getDependenciaId() + "." + proporcion.getFacultadId() + "."
                            + proporcion.getGeograficaId() + "." + item.getCodigoId();
                    if (!codigoImputaciones.containsKey(key)) {
                        CodigoImputacion imputacion = codigoImputacionService.add(
                                new CodigoImputacion(null, proporcion.getDependenciaId(), proporcion.getFacultadId(),
                                        proporcion.getGeograficaId(), item.getCodigoId(), null, null, null, null));
                        codigoImputaciones.put(key, codigoImputacionService.update(
                                new CodigoImputacion(imputacion.getCodigoImputacionId(), imputacion.getDependenciaId(),
                                        imputacion.getFacultadId(), imputacion.getGeograficaId(),
                                        imputacion.getCodigoId(), new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId())),
                                imputacion.getCodigoImputacionId()));

                    }

                    CodigoImputacion codigoImputacion = codigoImputaciones.get(key);

                    LegajoCodigoImputacion legajoCodigoImputacion = new LegajoCodigoImputacion(null, item.getLegajoId(),
                            item.getAnho(), item.getMes(), proporcion.getDependenciaId(), proporcion.getFacultadId(),
                            proporcion.getGeograficaId(), item.getCodigoId(), null,
                            item.getImporte().multiply(proporcion.getPorcentaje()).setScale(2, RoundingMode.HALF_UP),
                            null);
                    if (proporcion.getDocente() == 1) {
                        legajoCodigoImputacion.setCuentaSueldos(codigoImputacion.getCuentaSueldosDocente());
                        legajoCodigoImputacion.setCuentaAportes(codigoImputacion.getCuentaAportesDocente());
                    } else {
                        legajoCodigoImputacion.setCuentaSueldos(codigoImputacion.getCuentaSueldosNoDocente());
                        legajoCodigoImputacion.setCuentaAportes(codigoImputacion.getCuentaAportesNoDocente());
                    }
                    if (legajoCodigoImputacion.getImporte().compareTo(BigDecimal.ZERO) != 0) {
                        legajoCodigoImputacion = legajoCodigoImputacionService.add(legajoCodigoImputacion);
                        if (codigoIdRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalRemunerativo = totalRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                        if (codigoIdNoRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalNoRemunerativo = totalNoRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                    }
                }
            }
            // si el código es docente
            if (codigo.getDocente() == 1 && codigo.getNoDocente() == 0) {
                for (Proporcion proporcion : proporcionesDocente.values()) {
                    String key = proporcion.getDependenciaId() + "." + proporcion.getFacultadId() + "."
                            + proporcion.getGeograficaId() + "." + item.getCodigoId();
                    if (!codigoImputaciones.containsKey(key)) {
                        CodigoImputacion imputacion = codigoImputacionService.add(
                                new CodigoImputacion(null, proporcion.getDependenciaId(), proporcion.getFacultadId(),
                                        proporcion.getGeograficaId(), item.getCodigoId(), null, null, null, null));
                        codigoImputaciones.put(key, codigoImputacionService.update(
                                new CodigoImputacion(imputacion.getCodigoImputacionId(), imputacion.getDependenciaId(),
                                        imputacion.getFacultadId(), imputacion.getGeograficaId(),
                                        imputacion.getCodigoId(), new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId())),
                                imputacion.getCodigoImputacionId()));

                    }

                    CodigoImputacion codigoImputacion = codigoImputaciones.get(key);

                    LegajoCodigoImputacion legajoCodigoImputacion = new LegajoCodigoImputacion(null, item.getLegajoId(),
                            item.getAnho(), item.getMes(), proporcion.getDependenciaId(), proporcion.getFacultadId(),
                            proporcion.getGeograficaId(), item.getCodigoId(),
                            codigoImputacion.getCuentaSueldosDocente(),
                            item.getImporte().multiply(proporcion.getPorcentaje()).setScale(2, RoundingMode.HALF_UP),
                            codigoImputacion.getCuentaAportesDocente());
                    if (legajoCodigoImputacion.getImporte().compareTo(BigDecimal.ZERO) != 0) {
                        legajoCodigoImputacion = legajoCodigoImputacionService.add(legajoCodigoImputacion);
                        if (codigoIdRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalRemunerativo = totalRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                        if (codigoIdNoRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalNoRemunerativo = totalNoRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                    }
                }
            }
            // si el código es no docente
            if (codigo.getDocente() == 0 && codigo.getNoDocente() == 1) {
                for (Proporcion proporcion : proporcionesAdministrativo.values()) {
                    String key = proporcion.getDependenciaId() + "." + proporcion.getFacultadId() + "."
                            + proporcion.getGeograficaId() + "." + item.getCodigoId();
                    if (!codigoImputaciones.containsKey(key)) {
                        CodigoImputacion imputacion = codigoImputacionService.add(
                                new CodigoImputacion(null, proporcion.getDependenciaId(), proporcion.getFacultadId(),
                                        proporcion.getGeograficaId(), item.getCodigoId(), null, null, null, null));
                        codigoImputaciones.put(key, codigoImputacionService.update(
                                new CodigoImputacion(imputacion.getCodigoImputacionId(), imputacion.getDependenciaId(),
                                        imputacion.getFacultadId(), imputacion.getGeograficaId(),
                                        imputacion.getCodigoId(), new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId()),
                                        new BigDecimal(imputacion.getCodigoImputacionId())),
                                imputacion.getCodigoImputacionId()));

                    }

                    CodigoImputacion codigoImputacion = codigoImputaciones.get(key);

                    LegajoCodigoImputacion legajoCodigoImputacion = new LegajoCodigoImputacion(null, item.getLegajoId(),
                            item.getAnho(), item.getMes(), proporcion.getDependenciaId(), proporcion.getFacultadId(),
                            proporcion.getGeograficaId(), item.getCodigoId(),
                            codigoImputacion.getCuentaSueldosNoDocente(),
                            item.getImporte().multiply(proporcion.getPorcentaje()).setScale(2, RoundingMode.HALF_UP),
                            codigoImputacion.getCuentaAportesNoDocente());
                    if (legajoCodigoImputacion.getImporte().compareTo(BigDecimal.ZERO) != 0) {
                        legajoCodigoImputacion = legajoCodigoImputacionService.add(legajoCodigoImputacion);
                        if (codigoIdRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalRemunerativo = totalRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                        if (codigoIdNoRemunerativos.contains(legajoCodigoImputacion.getCodigoId())) {
                            totalNoRemunerativo = totalNoRemunerativo.add(legajoCodigoImputacion.getImporte());
                        }
                    }
                }
            }
        }

        if (totalRemunerativo.add(totalNoRemunerativo).subtract(liquidacion.getTotalRemunerativo())
                .subtract(liquidacion.getTotalNoRemunerativo()).compareTo(BigDecimal.ONE) > 0) {
            Long legajoContabilidadId = null;
            try {
                legajoContabilidadId = legajoContabilidadService
                        .findByUnique(liquidacion.getLegajoId(), liquidacion.getAnho(), liquidacion.getMes())
                        .getLegajoContabilidadId();
            } catch (LegajoContabilidadException e) {
                legajoContabilidadId = null;
            }
            legajoContabilidadService.save(new LegajoContabilidad(legajoContabilidadId, liquidacion.getLegajoId(),
                    liquidacion.getAnho(), liquidacion.getMes(), (byte) 1, totalRemunerativo, totalNoRemunerativo));
        }

    }

    @Transactional
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        legajoCargoClaseImputacionService.deleteAllByLegajo(legajoId, anho, mes);
        legajoCategoriaImputacionService.deleteAllByLegajo(legajoId, anho, mes);
        legajoCodigoImputacionService.deleteAllByLegajo(legajoId, anho, mes);
    }

    @Transactional
    public void deleteAllByPeriodo(Integer anho, Integer mes) {
        legajoCargoClaseImputacionService.deleteAllByPeriodo(anho, mes);
        legajoCategoriaImputacionService.deleteAllByPeriodo(anho, mes);
        legajoCodigoImputacionService.deleteAllByPeriodo(anho, mes);
    }

    public List<CuentaMovimiento> findAllByAsiento(OffsetDateTime fechaContable, Integer ordenContable) {
        return cuentaMovimientoService.findAllByAsiento(fechaContable, ordenContable);
    }

}
