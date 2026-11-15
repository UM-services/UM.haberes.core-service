package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out;

import java.util.List;
import java.util.Map;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ControlGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

/**
 * Lecturas que necesitan los generadores de cargos. Aísla el cálculo de las fuentes de datos
 * para que cada generador sea una función pura y testeable.
 */
public interface GeneracionCargosDatosPort {

    ControlGeneracionCargos control(Integer anho, Integer mes);

    Map<String, Dependencia> dependenciasPorSede();

    List<Cargo> cargosDocentes(Long legajoId, Integer anho, Integer mes);

    List<Cargo> cargosNoDocentes(Long legajoId, Integer anho, Integer mes);

    List<CursoFusion> cursosFusion(Long legajoId, Integer anho, Integer mes);

    List<CursoCargo> cursosCargo(Long legajoId, Integer anho, Integer mes);

    List<Novedad> novedades(Long legajoId, Integer anho, Integer mes, Integer codigoId);

    Categoria categoria(Integer categoriaId);

    List<Categoria> categoriasConPuntos();

    /**
     * Asignaciones ETEC vigentes en el período. Con {@code legajoId} nulo devuelve las de todo
     * el período.
     */
    List<EtecAsignacionCargo> asignacionesEtec(Long legajoId, Integer anho, Integer mes);

    /**
     * Categorías con asignaciones ETEC registradas, aunque hoy ya no tengan puntos. Completa el
     * alcance de borrado para no dejar filas huérfanas al regenerar.
     */
    List<Integer> categoriasEtecAsignadas();
}
