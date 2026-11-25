package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_fusion.application.service.CursoFusionService;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;
import um.haberes.core.hexagonal.etec.control_etec.application.service.ControlEtecService;
import um.haberes.core.hexagonal.etec.hora_escala.application.service.EscalaHoraEtecService;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.service.CargoService;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ControlGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.service.ControlService;

/**
 * Adaptador del puerto de datos de generación contra los slices existentes y el servicio legacy
 * de control. Es el único punto que conoce de dónde sale cada dato.
 */
@Component
@RequiredArgsConstructor
public class LegacyGeneracionCargosAdapter implements GeneracionCargosDatosPort {

    private final CargoService cargoService;

    private final CursoFusionService cursoFusionService;

    private final CursoCargoService cursoCargoService;

    private final NovedadService novedadService;

    private final DependenciaService dependenciaService;

    private final CategoriaService categoriaService;

    private final EtecAsignacionCargoRepository etecAsignacionCargoRepository;

    private final ControlService controlService;

    private final ControlEtecService controlEtecService;

    private final EscalaHoraEtecService escalaHoraEtecService;

    @Override
    public ControlGeneracionCargos control(Integer anho, Integer mes) {
        ControlEntity control = controlService.findByPeriodo(anho, mes);
        return controlEtecService.findByPeriodo(anho, mes)
                .map(controlEtec -> new ControlGeneracionCargos(
                        control.getModoLiquidacionId() == null ? 0 : control.getModoLiquidacionId(),
                        controlEtec.getValorPuntoEtec(), controlEtec.getMontoFijoCargo(),
                        controlEtec.getHorasReferenciaCargo()))
                .orElseGet(() -> new ControlGeneracionCargos(
                        control.getModoLiquidacionId() == null ? 0 : control.getModoLiquidacionId(),
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Override
    public Map<String, Dependencia> dependenciasPorSede() {
        return dependenciaService.findAll().stream()
                .collect(Collectors.toMap(Dependencia::getSedeKey, Function.identity(),
                        (existente, reemplazo) -> existente, LinkedHashMap::new));
    }

    @Override
    public List<Cargo> cargosDocentes(Long legajoId, Integer anho, Integer mes) {
        return cargoService.findAllDocenteByPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<Cargo> cargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
        return cargoService.findAllNoDocenteByPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<CursoFusion> cursosFusion(Long legajoId, Integer anho, Integer mes) {
        return cursoFusionService.findAllByLegajoId(legajoId, anho, mes);
    }

    @Override
    public List<CursoCargo> cursosCargo(Long legajoId, Integer anho, Integer mes) {
        return cursoCargoService.findAllByLegajo(legajoId, anho, mes);
    }

    @Override
    public List<CursoCargo> cursosCargoPeriodo(Integer anho, Integer mes) {
        return cursoCargoService.findAllByAnhoAndMes(anho, mes);
    }

    @Override
    public List<Novedad> novedades(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return novedadService.findAllByLegajoAndCodigo(legajoId, anho, mes, codigoId);
    }

    @Override
    public Categoria categoria(Integer categoriaId, Integer anho, Integer mes) {
        return categoriaService.findByCategoriaIdAndPeriodo(categoriaId, anho, mes);
    }

    @Override
    public List<Categoria> categoriasConPuntos(Integer anho, Integer mes) {
        return categoriaService.findAllConPuntos(anho, mes);
    }

    @Override
    public List<EtecAsignacionCargo> asignacionesEtec(Long legajoId, Integer anho, Integer mes) {
        return legajoId == null
                ? etecAsignacionCargoRepository.findAllVigentesByPeriodo(anho, mes)
                : etecAsignacionCargoRepository.findAllVigentesByLegajoAndPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<Integer> categoriasEtecAsignadas() {
        return etecAsignacionCargoRepository.findDistinctCategoriaIds();
    }

    @Override
    public List<EscalaHoraEtec> escalasHoraEtec(Integer anho, Integer mes) {
        return escalaHoraEtecService.findByPeriodo(anho, mes);
    }
}
