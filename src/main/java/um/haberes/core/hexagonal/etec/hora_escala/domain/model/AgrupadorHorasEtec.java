package um.haberes.core.hexagonal.etec.hora_escala.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;

/**
 * Regla única de las horas de clase ETEC: suma las horas semanales de los cursos de la facultad 15
 * agrupadas por legajo y sede (geográfica).
 *
 * <p>La usan los dos lados del circuito para que no existan dos criterios distintos: la generación
 * de cargos, para ubicar el tramo de la escala que define el básico, y la liquidación, para
 * calcular el adicional de carga horaria sobre el cargo por horas.</p>
 */
public final class AgrupadorHorasEtec {

    public static final Integer FACULTAD_ETEC = 15;

    private AgrupadorHorasEtec() {
    }

    /**
     * Horas semanales por legajo y sede. Cuando {@code legajoId} no es nulo todas las horas se
     * imputan a ese legajo (generación de un legajo puntual); si es nulo se usa el legajo de cada
     * curso (generación del período completo).
     */
    public static Map<Long, Map<Integer, BigDecimal>> porLegajoYSede(List<CursoCargo> cursos,
                                                                    Long legajoId) {
        Map<Long, Map<Integer, BigDecimal>> horas = new TreeMap<>();
        if (cursos == null) {
            return horas;
        }
        for (CursoCargo cursoCargo : cursos) {
            Curso curso = cursoCargo.getCurso();
            if (curso == null || !FACULTAD_ETEC.equals(curso.getFacultadId())) {
                continue;
            }
            Long legajo = legajoId == null ? cursoCargo.getLegajoId() : legajoId;
            if (legajo == null) {
                throw new EscalaHoraEtecException(
                        "Un curso ETEC del período no tiene legajo asignado");
            }
            if (curso.getGeograficaId() == null) {
                throw new EscalaHoraEtecException("El curso " + curso.getCursoId()
                        + " de la ETEC no tiene geográfica");
            }
            BigDecimal cantidad = cursoCargo.getHorasSemanales() == null ? BigDecimal.ZERO
                    : cursoCargo.getHorasSemanales();
            horas.computeIfAbsent(legajo, clave -> new TreeMap<>())
                    .merge(curso.getGeograficaId(), cantidad, BigDecimal::add);
        }
        return horas;
    }

    /**
     * Horas del legajo en una sede. Con {@code geograficaId} nulo devuelve la suma de todas las
     * sedes del legajo.
     */
    public static BigDecimal horasEnSede(List<CursoCargo> cursos, Long legajoId,
                                         Integer geograficaId) {
        Map<Integer, BigDecimal> sedes = porLegajoYSede(cursos, legajoId)
                .getOrDefault(legajoId, Map.of());
        if (geograficaId == null) {
            return sedes.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return sedes.getOrDefault(geograficaId, BigDecimal.ZERO);
    }
}
