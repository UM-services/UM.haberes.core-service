package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.exception.ArchivoAcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.FilaAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.PreparacionAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.PrepararAcreditacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.application.exception.ItemException;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.service.LegajoBancoService;

/**
 * Réplica de {@code verifyCBU}/{@code verifyCBUall} y
 * {@code calculaImporteAcreditarAll}/{@code calculaImporteAcreditarCuenta} del legacy.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PrepararAcreditacionesUseCaseImpl implements PrepararAcreditacionesUseCase {

    private static final Integer CODIGO_NETO = 99;
    private static final BigDecimal CIEN = new BigDecimal(100);
    private static final byte RESTO_SI = (byte) 1;

    private final LegajoBancoService legajoBancoService;
    private final LiquidacionService liquidacionService;
    private final ItemService itemService;
    private final NovedadService novedadService;

    @Override
    @Transactional
    public PreparacionAcreditacion prepararPeriodo(Integer anho, Integer mes) {
        validarPeriodo(anho, mes);
        PreparacionAcreditacion resultado = base(anho, mes);
        for (Liquidacion liquidacion : liquidacionService.getLiquidacionesByPeriodo(anho, mes)) {
            prepararLegajo(liquidacion.getLegajoId(), anho, mes, importeItem(liquidacion.getLegajoId(), anho, mes),
                    resultado);
        }
        return resultado;
    }

    @Override
    @Transactional
    public PreparacionAcreditacion prepararPorCodigo(Integer anho, Integer mes, Integer codigoId) {
        validarPeriodo(anho, mes);
        if (codigoId == null || codigoId <= 0) {
            throw new IllegalArgumentException("Debe indicar el código de novedades");
        }
        List<Novedad> novedades = novedadService.findAllByCodigo(codigoId, anho, mes);
        if (novedades.isEmpty()) {
            throw new ArchivoAcreditacionException("Sin novedades para el código " + codigoId + " en el período");
        }
        PreparacionAcreditacion resultado = base(anho, mes);
        Map<Long, BigDecimal> importes = new LinkedHashMap<>();
        for (Novedad novedad : novedades) {
            importes.put(novedad.getLegajoId(),
                    novedad.getImporte() == null ? BigDecimal.ZERO : novedad.getImporte().abs());
        }
        for (Map.Entry<Long, BigDecimal> entrada : importes.entrySet()) {
            prepararLegajo(entrada.getKey(), anho, mes, entrada.getValue(), resultado);
        }
        return resultado;
    }

    @Override
    @Transactional
    public PreparacionAcreditacion prepararLegajo(Long legajoId, Integer anho, Integer mes) {
        validarPeriodo(anho, mes);
        PreparacionAcreditacion resultado = base(anho, mes);
        prepararLegajo(legajoId, anho, mes, importeItem(legajoId, anho, mes), resultado);
        resultado.setFilas(filas(legajoId, anho, mes));
        return resultado;
    }

    private void prepararLegajo(Long legajoId, Integer anho, Integer mes, BigDecimal importeTotal,
            PreparacionAcreditacion resultado) {
        List<LegajoBancoEntity> filas = verificarCbu(legajoId, anho, mes);
        if (filas.isEmpty()) {
            resultado.getSinCbu().add(legajoId);
            return;
        }
        if (importeTotal == null) {
            resultado.getSinLiquidacion().add(legajoId);
            return;
        }
        calcularYGuardar(filas, importeTotal);
    }

    /**
     * Si el legajo no tiene CBUs en el período, copia los del último período con datos.
     */
    List<LegajoBancoEntity> verificarCbu(Long legajoId, Integer anho, Integer mes) {
        List<LegajoBancoEntity> filas = legajoBancoService.findAllByLegajoPeriodo(legajoId, anho, mes);
        if (!filas.isEmpty()) {
            return filas;
        }
        LegajoBancoEntity ultimo;
        try {
            ultimo = legajoBancoService.findLastByLegajoId(legajoId);
        } catch (LegajoBancoException e) {
            return List.of();
        }
        List<LegajoBancoEntity> origen = legajoBancoService.findAllByLegajoPeriodo(legajoId, ultimo.getAnho(),
                ultimo.getMes());
        for (LegajoBancoEntity fila : origen) {
            legajoBancoService.add(new LegajoBancoEntity(null, legajoId, anho, mes, fila.getCbu(), fila.getFijo(),
                    fila.getPorcentaje(), fila.getResto(), BigDecimal.ZERO, null, null));
        }
        return legajoBancoService.findAllByLegajoPeriodo(legajoId, anho, mes);
    }

    /**
     * Reparte el importe del item 99: primero los fijos (topados al resto), después los porcentajes y,
     * si sobra, el saldo va a la fila marcada con {@code resto = 1}.
     */
    void calcularYGuardar(List<LegajoBancoEntity> filas, BigDecimal importeTotal) {
        BigDecimal resto = importeTotal;
        for (LegajoBancoEntity fila : filas) {
            BigDecimal acreditado = BigDecimal.ZERO;
            if (positivo(fila.getFijo())) {
                acreditado = fila.getFijo().min(resto);
                resto = resto.subtract(acreditado);
            }
            if (positivo(fila.getPorcentaje())) {
                acreditado = importeTotal.multiply(fila.getPorcentaje()).divide(CIEN, 2, RoundingMode.HALF_UP);
                resto = resto.subtract(acreditado);
            }
            fila.setAcreditado(acreditado);
        }
        if (positivo(resto)) {
            for (LegajoBancoEntity fila : filas) {
                if (Byte.valueOf(RESTO_SI).equals(fila.getResto())) {
                    fila.setAcreditado(resto);
                    break;
                }
            }
        }
        for (LegajoBancoEntity fila : filas) {
            legajoBancoService.update(fila, fila.getLegajoBancoId());
        }
    }

    private BigDecimal importeItem(Long legajoId, Integer anho, Integer mes) {
        try {
            return itemService.findByUnique(legajoId, anho, mes, CODIGO_NETO).getImporte();
        } catch (ItemException e) {
            log.debug("Sin item {} para legajo {} período {}/{}", CODIGO_NETO, legajoId, mes, anho);
            return null;
        }
    }

    private List<FilaAcreditacion> filas(Long legajoId, Integer anho, Integer mes) {
        List<FilaAcreditacion> filas = new ArrayList<>();
        for (LegajoBancoEntity fila : legajoBancoService.findAllByLegajoPeriodo(legajoId, anho, mes)) {
            filas.add(FilaAcreditacion.builder()
                    .legajoBancoId(fila.getLegajoBancoId())
                    .legajoId(fila.getLegajoId())
                    .cbu(fila.getCbu())
                    .fijo(fila.getFijo())
                    .porcentaje(fila.getPorcentaje())
                    .resto(fila.getResto())
                    .acreditado(fila.getAcreditado())
                    .apellidoNombre(fila.getPersona() == null ? "" : fila.getPersona().getApellidoNombre())
                    .cuil(fila.getPersona() == null ? null : fila.getPersona().getCuil())
                    .build());
        }
        return filas;
    }

    private static boolean positivo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) > 0;
    }

    private static PreparacionAcreditacion base(Integer anho, Integer mes) {
        return PreparacionAcreditacion.builder().anho(anho).mes(mes).build();
    }

    private static void validarPeriodo(Integer anho, Integer mes) {
        if (anho == null || mes == null || mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Período inválido");
        }
    }
}
