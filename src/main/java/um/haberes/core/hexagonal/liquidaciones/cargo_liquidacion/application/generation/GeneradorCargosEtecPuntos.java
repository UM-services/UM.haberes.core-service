package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

/**
 * Cargos ETEC por puntos: {@code categoriaBasico = puntos del cargo × valor del punto del
 * período}, tomando las asignaciones vigentes de {@code etec_asignacion_cargo}.
 *
 * <p>Con {@code legajoId} nulo genera el período completo (lo que antes hacía
 * {@code haberes-etec} vía Feign). Si hay asignaciones vigentes y el período no tiene un valor
 * de punto mayor a cero, falla en lugar de generar importes en cero.</p>
 */
@Component
@Order(60)
@RequiredArgsConstructor
public class GeneradorCargosEtecPuntos implements GeneradorCargosLiquidacion {

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.ETEC;
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        Map<Integer, Categoria> categorias = categoriasConPuntos();
        if (categorias.isEmpty()) {
            return List.of();
        }

        List<EtecAsignacionCargo> vigentes = datos
                .asignacionesEtec(contexto.legajoId(), contexto.anho(), contexto.mes()).stream()
                .filter(asignacion -> categorias.containsKey(asignacion.getCategoriaId()))
                .toList();
        if (vigentes.isEmpty()) {
            return List.of();
        }

        BigDecimal valorPunto = valorPunto(contexto);
        return vigentes.stream()
                .map(asignacion -> CargoLiquidacionFactory.deEtec(contexto, asignacion,
                        categorias.get(asignacion.getCategoriaId()), valorPunto,
                        contexto.montoFijoCargo()))
                .toList();
    }

    private Map<Integer, Categoria> categoriasConPuntos() {
        Map<Integer, Categoria> categorias = new LinkedHashMap<>();
        for (Categoria categoria : datos.categoriasConPuntos()) {
            if (categoria.getCategoriaId() != null) {
                categorias.putIfAbsent(categoria.getCategoriaId(), categoria);
            }
        }
        return categorias;
    }

    private BigDecimal valorPunto(ContextoGeneracionCargos contexto) {
        BigDecimal valorPunto = contexto.valorPuntoEtec();
        if (valorPunto == null || valorPunto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CargoLiquidacionException("El período " + contexto.anho() + "/" + contexto.mes()
                    + " no tiene un valor de punto ETEC mayor a cero en control_etec");
        }
        return valorPunto;
    }
}
