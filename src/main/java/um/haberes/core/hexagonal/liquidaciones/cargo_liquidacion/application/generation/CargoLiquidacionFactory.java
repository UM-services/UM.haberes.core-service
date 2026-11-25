package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

/**
 * Construcción de las filas de {@code cargo_liquidacion}. Replica exactamente los valores que
 * armaba el motor legacy para cada fuente, para que el resultado persistido no cambie.
 */
final class CargoLiquidacionFactory {

    private static final String SITUACION_ACTIVA = "A";

    private CargoLiquidacionFactory() {
    }

    static CargoLiquidacion deCargo(ContextoGeneracionCargos contexto, Cargo cargo) {
        Categoria categoria = cargo.getCategoria();
        return base(contexto, cargo.getDependenciaId(), categoria, categoria.getBasico(),
                categoria.getEstadoDocente(), cargo.getHorasJornada(), cargo.getJornada(),
                cargo.getPresentismo(), null);
    }

    static CargoLiquidacion deCurso(ContextoGeneracionCargos contexto, Dependencia dependencia,
                                    Categoria categoria) {
        return base(contexto, dependencia.getDependenciaId(), categoria, categoria.getBasico(),
                categoria.getEstadoDocente(), BigDecimal.ZERO, 1, 0, dependencia);
    }

    static CargoLiquidacion deNovedadAjusteBasico(ContextoGeneracionCargos contexto, Novedad novedad,
                                                  Categoria categoria) {
        return base(contexto, novedad.getDependenciaId(), categoria, novedad.getImporte(),
                BigDecimal.ZERO, BigDecimal.ZERO, 1, 0, novedad.getDependencia());
    }

    static CargoLiquidacion deEtec(ContextoGeneracionCargos contexto, EtecAsignacionCargo asignacion,
                                   Categoria categoria, BigDecimal valorPunto, BigDecimal montoFijo) {
        BigDecimal variable = categoria.getPuntos().multiply(valorPunto);
        BigDecimal basico = prorratearVariablePorHoras(variable, asignacion.getHorasCargo(),
                contexto.horasReferenciaCargo());
        BigDecimal fijo = montoFijo == null ? BigDecimal.ZERO : montoFijo;
        return base(contexto, asignacion.getLegajoId(), asignacion.getDependenciaId(), categoria,
                basico.add(fijo).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.ZERO, BigDecimal.ZERO, 1, 0, null);
    }

    /**
     * La parte variable del básico del cargo por puntos ({@code puntos × valor del punto}) se
     * expresa para las horas de referencia del período. Si el cargo tiene horas informadas, se
     * prorratea: {@code variable × horasCargo / horasReferencia}. Sin horas (datos previos o
     * control sin referencia) se conserva el importe completo. El monto fijo no se prorratea.
     */
    private static BigDecimal prorratearVariablePorHoras(BigDecimal importe, BigDecimal horasCargo,
                                                        BigDecimal horasReferencia) {
        BigDecimal resultado = importe;
        if (horasCargo != null && horasCargo.signum() > 0
                && horasReferencia != null && horasReferencia.signum() > 0) {
            resultado = importe.multiply(horasCargo).divide(horasReferencia, 2, RoundingMode.HALF_UP);
        }
        return resultado.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Cargo de horas de clase ETEC: el importe del tramo ya viene calculado. No lleva horas de
     * jornada (se multiplicaría de nuevo) ni presentismo no docente.
     */
    static CargoLiquidacion deEtecHoras(ContextoGeneracionCargos contexto, Long legajoId,
                                        Dependencia dependencia, Categoria categoria, BigDecimal importe) {
        return base(contexto, legajoId, dependencia.getDependenciaId(), categoria, importe,
                BigDecimal.ZERO, BigDecimal.ZERO, 1, 0, dependencia);
    }

    private static CargoLiquidacion base(ContextoGeneracionCargos contexto, Integer dependenciaId,
                                         Categoria categoria, BigDecimal categoriaBasico,
                                         BigDecimal estadoDocente, BigDecimal horasJornada, int jornada,
                                         int presentismo, Dependencia dependencia) {
        return base(contexto, contexto.legajoId(), dependenciaId, categoria, categoriaBasico, estadoDocente,
                horasJornada, jornada, presentismo, dependencia);
    }

    private static CargoLiquidacion base(ContextoGeneracionCargos contexto, Long legajoId,
                                         Integer dependenciaId, Categoria categoria,
                                         BigDecimal categoriaBasico, BigDecimal estadoDocente,
                                         BigDecimal horasJornada, int jornada, int presentismo,
                                         Dependencia dependencia) {
        return CargoLiquidacion.builder()
                .legajoId(legajoId)
                .anho(contexto.anho())
                .mes(contexto.mes())
                .dependenciaId(dependenciaId)
                .fechaDesde(contexto.fechaDesde())
                .fechaHasta(contexto.fechaHasta())
                .categoriaId(categoria.getCategoriaId())
                .categoriaNombre(categoria.getNombre())
                .categoriaBasico(categoriaBasico)
                .estadoDocente(estadoDocente)
                .horasJornada(horasJornada)
                .jornada(jornada)
                .presentismo(presentismo)
                .situacion(SITUACION_ACTIVA)
                .dependencia(dependencia)
                .categoria(categoria)
                .build();
    }
}
