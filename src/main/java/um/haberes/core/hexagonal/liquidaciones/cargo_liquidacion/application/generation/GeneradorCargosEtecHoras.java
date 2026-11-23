package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.AgrupadorHorasEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CatalogoUniversosCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

/**
 * Cargos por horas de clase ETEC. Suma las horas semanales de los cursos de la facultad 15 por
 * sede, busca el tramo de la escala del período que contiene ese total y genera un cargo con la
 * categoría configurada en el tramo ({@code etec_horas = 1}) por
 * {@code monto fijo + horas × valor hora}.
 *
 * <p>Los generadores de cursos excluyen la facultad 15 para que estas horas no se liquiden dos
 * veces. El cargo entra al acumulador ETEC como cualquier cargo con puntos, así que también
 * liquida presentismo, adicional de carrera y arraigo.</p>
 */
@Component
@Order(70)
@RequiredArgsConstructor
public class GeneradorCargosEtecHoras implements GeneradorCargosLiquidacion {

    static final Integer FACULTAD_ETEC = AgrupadorHorasEtec.FACULTAD_ETEC;

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.ETEC;
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        List<EscalaHoraEtec> escala = datos.escalasHoraEtec(contexto.anho(), contexto.mes());
        if (escala.isEmpty()) {
            return List.of();
        }
        Map<Long, Map<Integer, BigDecimal>> horas = AgrupadorHorasEtec.porLegajoYSede(
                contexto.legajoId() == null
                        ? datos.cursosCargoPeriodo(contexto.anho(), contexto.mes())
                        : datos.cursosCargo(contexto.legajoId(), contexto.anho(), contexto.mes()),
                contexto.legajoId());
        if (horas.isEmpty()) {
            return List.of();
        }

        Map<Integer, Categoria> categorias = new HashMap<>();
        List<CargoLiquidacion> cargos = new ArrayList<>();
        horas.forEach((legajoId, sedes) -> sedes.forEach((geograficaId, cantidad) -> {
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }
            EscalaHoraEtec tramo = tramo(escala, cantidad, legajoId, geograficaId, contexto);
            Categoria categoria = categorias.computeIfAbsent(tramo.getCategoriaId(),
                    categoriaId -> categoria(tramo, contexto));
            cargos.add(CargoLiquidacionFactory.deEtecHoras(contexto, legajoId,
                    dependencia(contexto, geograficaId), categoria, tramo.importe(cantidad)));
        }));
        return cargos;
    }

    private Categoria categoria(EscalaHoraEtec tramo, ContextoGeneracionCargos contexto) {
        Categoria categoria = datos.categoria(tramo.getCategoriaId(), contexto.anho(), contexto.mes());
        if (categoria == null) {
            throw new CargoLiquidacionException("No existe la categoría "
                    + tramo.getCategoriaId() + " del tramo de horas ETEC del período "
                    + contexto.anho() + "/" + contexto.mes());
        }
        if (!CatalogoUniversosCargos.esCategoriaHorasEtec(categoria)) {
            throw new CargoLiquidacionException("La categoría " + categoria.getCategoriaId()
                    + " (" + categoria.getNombre()
                    + ") del tramo que empieza en "
                    + tramo.getHorasDesde().stripTrailingZeros().toPlainString()
                    + " hora(s) no está marcada como horas ETEC");
        }
        return categoria;
    }

    private EscalaHoraEtec tramo(List<EscalaHoraEtec> escala, BigDecimal horas, Long legajoId,
                                 Integer geograficaId, ContextoGeneracionCargos contexto) {
        return escala.stream()
                .filter(tramo -> tramo.contiene(horas))
                .findFirst()
                .orElseThrow(() -> new CargoLiquidacionException("Las "
                        + horas.stripTrailingZeros().toPlainString() + " horas semanales del legajo "
                        + legajoId + " en la sede " + geograficaId
                        + " no caen en ningún tramo de la escala de horas ETEC del período "
                        + contexto.anho() + "/" + contexto.mes()));
    }

    private Dependencia dependencia(ContextoGeneracionCargos contexto, Integer geograficaId) {
        Dependencia dependencia = contexto.dependenciasPorSede()
                .get(FACULTAD_ETEC + "." + geograficaId);
        if (dependencia == null) {
            throw new CargoLiquidacionException("No existe la dependencia de la facultad "
                    + FACULTAD_ETEC + " y geográfica " + geograficaId);
        }
        return dependencia;
    }
}
