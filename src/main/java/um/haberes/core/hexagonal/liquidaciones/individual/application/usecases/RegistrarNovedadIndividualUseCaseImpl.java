package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.RegistroNovedadIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.RegistrarNovedadIndividualUseCase;

@Component
@RequiredArgsConstructor
public class RegistrarNovedadIndividualUseCaseImpl implements RegistrarNovedadIndividualUseCase {

    private static final String VALOR_SI = "S";
    private static final String VALOR_NO = "N";

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public NovedadRegistradaIndividual registrar(Long legajoId, Integer anho, Integer mes,
                                                 RegistroNovedadIndividual registro) {
        if (registro == null || registro.codigoId() == null) {
            return new NovedadRegistradaIndividual(EstadoProcesoIndividual.DATOS_INVALIDOS,
                    "Falta el código de la novedad.", null);
        }
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);
        Optional<ResultadoProcesoIndividual> bloqueo = reglas.bloquearSiCorresponde(liquidacion,
                registro.forzar());
        if (bloqueo.isPresent()) {
            return new NovedadRegistradaIndividual(bloqueo.get().resultado(), bloqueo.get().mensaje(), null);
        }

        // RN-06: si el texto es S/N, el importe va a cero y el texto a `value`.
        String valor = normalizarValor(registro.valor());
        boolean esValorSino = VALOR_SI.equals(valor) || VALOR_NO.equals(valor);
        BigDecimal importe = esValorSino
                ? BigDecimal.ZERO
                : (registro.importe() == null ? BigDecimal.ZERO : registro.importe());
        String value = esValorSino ? valor : "";

        // RN-05: upsert por clave única sin dependencia.
        Optional<NovedadView> existente = reglas.buscarNovedad(legajoId, anho, mes, registro.codigoId());
        NovedadView novedad = existente
                .map(actual -> novedadCrossSlice.actualizar(actual.novedadId(), importe, value,
                        registro.observaciones()))
                .orElseGet(() -> novedadCrossSlice.crear(legajoId, anho, mes, registro.codigoId(), importe,
                        value, registro.observaciones()));

        return new NovedadRegistradaIndividual(EstadoProcesoIndividual.GUARDADA, "Novedad guardada.", novedad);
    }

    private String normalizarValor(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim().toUpperCase(Locale.ROOT);
    }
}
