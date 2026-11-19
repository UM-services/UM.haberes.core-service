package um.haberes.core.hexagonal.liquidaciones.sijp.application.usecases;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpSinControlException;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.RegistroSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.layout.SijpLayout;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.ports.in.GenerarArchivoSijpUseCase;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.CodigoGrupoService;
import um.haberes.core.service.ControlService;
import um.haberes.core.service.LegajoControlService;

/**
 * Genera el archivo SIJP de un período replicando {@code frmGenSIJP} del legacy.
 *
 * <p>
 * Se diferencia del legacy en tres puntos deliberados (ver {@code PLANIFICACION_SIJP_HABERES.md}):
 * la novedad de días no trabajados se busca bien (el legacy nunca la encontraba), la variante se
 * elige por parámetro y los legajos sin CUIL/persona bloquean la generación en vez de producir
 * una línea inválida.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class GenerarArchivoSijpUseCaseImpl implements GenerarArchivoSijpUseCase {

    private static final int CODIGO_TOTAL_REMUNERATIVO = 96;
    private static final int CODIGO_TOTAL_NO_REMUNERATIVO = 97;
    private static final int CODIGO_SAC = 3;
    private static final int CODIGO_INCREMENTO_SALARIAL = 51;
    private static final int CODIGO_NOVEDAD_DIAS_NO_TRABAJADOS = 18;

    private static final int TIPO_EMPLEADO_NORMAL = 1;
    private static final int TIPO_EMPLEADO_ETEC = 7;
    private static final int ACTIVIDAD_ETEC = 38;

    private static final BigDecimal DIAS_BASE = new BigDecimal("30");
    private static final BigDecimal DETRACCION_MAXIMA_NORMAL = new BigDecimal("7003.68");
    private static final String PROVINCIA_LOCALIDAD = "Mendoza-Gran Mendoza";
    private static final int MAX_LEGAJOS_EN_MENSAJE = 20;

    private static final byte REMUNERATIVO = (byte) 1;
    private static final byte INCLUIDO_ETEC_NO = (byte) 0;

    private final LegajoControlService legajoControlService;
    private final ControlService controlService;
    private final ItemService itemService;
    private final NovedadService novedadService;
    private final CodigoService codigoService;
    private final CodigoGrupoService codigoGrupoService;

    @Override
    public ArchivoSijp generar(Integer anho, Integer mes, VarianteSijp variante) {
        validarPeriodo(anho, mes);
        VarianteSijp varianteSegura = variante == null ? VarianteSijp.NORMAL : variante;

        ControlEntity control = controlService.findByPeriodoOpcional(anho, mes)
                .orElseThrow(() -> new SijpSinControlException(anho, mes));
        if (control.getFechaHasta() == null) {
            throw new SijpException("El control del período " + anho + "/" + mes + " no tiene fecha hasta");
        }

        List<LegajoControlEntity> legajos = legajoControlService.findAllLiquidadoByPeriodo(anho, mes);
        if (legajos.isEmpty()) {
            throw new SijpException("No hay legajos liquidados en el período " + anho + "/" + mes);
        }
        validarLegajos(legajos);

        Map<Long, Map<Integer, BigDecimal>> importesPorLegajo = importesPorLegajo(anho, mes);
        Map<Long, BigDecimal> novedades = novedadesDiasNoTrabajados(anho, mes);
        Set<Integer> codigosRemunerativos = codigosRemunerativos();
        Map<Integer, Byte> incluidoEtecPorCodigo = incluidoEtecPorCodigo();
        BigDecimal coeficiente = coeficiente(control);

        StringBuilder contenido = new StringBuilder(legajos.size() * (SijpLayout.LONGITUD_LINEA + 2));
        for (LegajoControlEntity legajo : legajos) {
            Long legajoId = legajo.getLegajoId();
            Map<Integer, BigDecimal> importes = importesPorLegajo.getOrDefault(legajoId, Map.of());
            boolean onlyEtec = esOnlyEtec(importes.keySet(), codigosRemunerativos, incluidoEtecPorCodigo);
            RegistroSijp registro = construirRegistro(legajo.getPersona(), importes,
                    novedades.getOrDefault(legajoId, BigDecimal.ZERO), onlyEtec, coeficiente, control,
                    varianteSegura);
            contenido.append(SijpLayout.render(registro)).append(SijpLayout.FIN_LINEA);
        }

        return ArchivoSijp.builder()
                .variante(varianteSegura)
                .nombreArchivo(nombreArchivo(anho, mes, varianteSegura))
                .contenido(contenido.toString().getBytes(SijpLayout.CHARSET_ANSI))
                .cantidad(legajos.size())
                .build();
    }

    private void validarPeriodo(Integer anho, Integer mes) {
        if (anho == null || anho < 2000 || anho > 2100) {
            throw new IllegalArgumentException("Año inválido: " + anho);
        }
        if (mes == null || mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mes inválido: " + mes);
        }
    }

    private void validarLegajos(List<LegajoControlEntity> legajos) {
        List<Long> sinPersona = legajos.stream()
                .filter(legajo -> legajo.getPersona() == null)
                .map(LegajoControlEntity::getLegajoId)
                .toList();
        if (!sinPersona.isEmpty()) {
            throw new SijpException("Legajos sin persona: " + detalleLegajos(sinPersona));
        }
        List<Long> sinCuil = legajos.stream()
                .filter(legajo -> normalizarCuil(legajo.getPersona()) == null)
                .map(LegajoControlEntity::getLegajoId)
                .toList();
        if (!sinCuil.isEmpty()) {
            throw new SijpException("Legajos sin CUIL válido (11 dígitos): " + detalleLegajos(sinCuil));
        }
    }

    private Map<Long, Map<Integer, BigDecimal>> importesPorLegajo(Integer anho, Integer mes) {
        Map<Long, Map<Integer, BigDecimal>> resultado = new HashMap<>();
        for (Item item : itemService.getItemsByPeriodo(anho, mes)) {
            if (item.getLegajoId() == null || item.getCodigoId() == null) {
                continue;
            }
            BigDecimal importe = item.getImporte() == null ? BigDecimal.ZERO : item.getImporte();
            resultado.computeIfAbsent(item.getLegajoId(), clave -> new HashMap<>())
                    .merge(item.getCodigoId(), importe, BigDecimal::add);
        }
        return resultado;
    }

    private Map<Long, BigDecimal> novedadesDiasNoTrabajados(Integer anho, Integer mes) {
        Map<Long, BigDecimal> resultado = new HashMap<>();
        for (Novedad novedad : novedadService.findAllByCodigo(CODIGO_NOVEDAD_DIAS_NO_TRABAJADOS, anho, mes)) {
            if (novedad.getLegajoId() == null || novedad.getDependenciaId() != null) {
                continue;
            }
            BigDecimal importe = novedad.getImporte() == null ? BigDecimal.ZERO : novedad.getImporte();
            resultado.merge(novedad.getLegajoId(), importe, BigDecimal::add);
        }
        return resultado;
    }

    private Set<Integer> codigosRemunerativos() {
        return codigoGrupoService.findAllByRemunerativo(REMUNERATIVO).stream()
                .map(CodigoGrupoEntity::getCodigoId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Map<Integer, Byte> incluidoEtecPorCodigo() {
        return codigoService.findAll().stream()
                .filter(codigo -> codigo.getCodigoId() != null)
                .collect(Collectors.toMap(Codigo::getCodigoId,
                        codigo -> codigo.getIncluidoEtec() == null ? (byte) 1 : codigo.getIncluidoEtec(),
                        (primero, segundo) -> primero));
    }

    private boolean esOnlyEtec(Set<Integer> codigosDelLegajo, Set<Integer> codigosRemunerativos,
            Map<Integer, Byte> incluidoEtecPorCodigo) {
        return codigosDelLegajo.stream()
                .filter(codigosRemunerativos::contains)
                .noneMatch(codigoId -> Byte.valueOf(INCLUIDO_ETEC_NO)
                        .equals(incluidoEtecPorCodigo.get(codigoId)));
    }

    private BigDecimal coeficiente(ControlEntity control) {
        int mesLiquidacion = control.getFechaHasta().getMonthValue();
        return mesLiquidacion == 6 || mesLiquidacion == 12 ? new BigDecimal("1.5") : BigDecimal.ONE;
    }

    private RegistroSijp construirRegistro(PersonaEntity persona, Map<Integer, BigDecimal> items,
            BigDecimal novedad18, boolean onlyEtec, BigDecimal coeficiente, ControlEntity control,
            VarianteSijp variante) {

        boolean normal = variante == VarianteSijp.NORMAL;

        BigDecimal totalRemunerativo = importe(items, CODIGO_TOTAL_REMUNERATIVO);
        BigDecimal totalNoRemunerativo = importe(items, CODIGO_TOTAL_NO_REMUNERATIVO);
        BigDecimal sac = importe(items, CODIGO_SAC);
        BigDecimal incremento = importe(items, CODIGO_INCREMENTO_SALARIAL);

        BigDecimal total = normal
                ? totalRemunerativo.add(totalNoRemunerativo)
                : totalRemunerativo.subtract(incremento);

        BigDecimal maximo1 = control.getMaximo1sijp().multiply(coeficiente);
        BigDecimal maximo4 = control.getMaximo4sijp().multiply(coeficiente);
        BigDecimal maximo5 = control.getMaximo5sijp().multiply(coeficiente);
        BigDecimal minimo = control.getMinimoAporte() == null ? BigDecimal.ZERO : control.getMinimoAporte();
        BigDecimal piso = minimo.multiply(coeficiente);
        BigDecimal mitadMinimo = minimo.movePointLeft(1);

        BigDecimal base = normal ? totalRemunerativo : total;

        BigDecimal imponible1 = base;
        if (total.compareTo(maximo1) > 0) {
            imponible1 = maximo1;
        }

        BigDecimal imponible4 = base;
        if (total.compareTo(maximo4) > 0) {
            imponible4 = maximo4;
        }
        if (total.compareTo(piso) < 0) {
            imponible4 = piso;
        }

        BigDecimal imponible5 = base;
        if (total.compareTo(maximo5) > 0) {
            imponible5 = maximo5;
        }

        BigDecimal imponible8 = base;
        if (total.compareTo(piso) < 0) {
            imponible8 = piso;
        }

        BigDecimal detraccion;
        if (normal) {
            detraccion = totalRemunerativo.subtract(DETRACCION_MAXIMA_NORMAL).compareTo(mitadMinimo) > 0
                    ? DETRACCION_MAXIMA_NORMAL
                    : totalRemunerativo.subtract(mitadMinimo);
        } else {
            detraccion = total.subtract(minimo).compareTo(mitadMinimo) < 0
                    ? total.subtract(mitadMinimo)
                    : minimo;
        }
        if (detraccion.compareTo(BigDecimal.ZERO) < 0) {
            detraccion = BigDecimal.ZERO;
        }

        BigDecimal imponible3 = base;
        BigDecimal imponible6 = BigDecimal.ZERO;
        BigDecimal imponible7 = BigDecimal.ZERO;
        BigDecimal incrementoSalarial = normal ? incremento : BigDecimal.ZERO;
        BigDecimal conceptosNoRemunerativos = normal
                ? totalNoRemunerativo.subtract(incremento)
                : BigDecimal.ZERO;
        int tipoEmpleado = TIPO_EMPLEADO_NORMAL;
        int actividad = valor(persona.getActividadAfip());

        if (!normal && onlyEtec) {
            tipoEmpleado = TIPO_EMPLEADO_ETEC;
            actividad = ACTIVIDAD_ETEC;
            imponible1 = BigDecimal.ZERO;
            imponible3 = BigDecimal.ZERO;
            imponible6 = base;
            imponible7 = base;
            detraccion = BigDecimal.ZERO;
            incrementoSalarial = BigDecimal.ZERO;
        }

        int situacion = persona.getSituacionAfip();
        if (persona.getEstado() == 9) {
            situacion = 1;
        }

        return RegistroSijp.builder()
                .cuil(normalizarCuil(persona))
                .apellidoNombre(persona.getApellidoNombre())
                .conyuge(0)
                .cantidadHijos(0)
                .codigoSituacion(situacion)
                .codigoCondicion(persona.getEstadoAfip())
                .codigoActividad(actividad)
                .codigoZona(valor(persona.getLocalidadAfip()))
                .porcentajeAporteAdicionalSs(BigDecimal.ZERO)
                .codigoModalidadContratacion(valor(persona.getModeloContratacionAfip()))
                .codigoObraSocial(persona.getObraSocial() == null ? 0L : persona.getObraSocial())
                .cantidadAdherentes(0)
                .remuneracionTotal(total)
                .remuneracionImponible1(imponible1)
                .asignacionesFamiliaresPagadas(BigDecimal.ZERO)
                .importeAporteVoluntario(BigDecimal.ZERO)
                .importeAdicionalOs(BigDecimal.ZERO)
                .importeExcedenteAportesSs(BigDecimal.ZERO)
                .importeExcedenteAportesOs(BigDecimal.ZERO)
                .provinciaLocalidad(PROVINCIA_LOCALIDAD)
                .remuneracionImponible2(base)
                .remuneracionImponible3(imponible3)
                .remuneracionImponible4(imponible4)
                .codigoSiniestrado(0)
                .marcaCorrespondeReduccion(" ")
                .capitalRecomposicionLrt(BigDecimal.ZERO)
                .tipoEmpleado(tipoEmpleado)
                .aporteAdicionalObraSocial(BigDecimal.ZERO)
                .regimen(1)
                .situacionRevista1(situacion)
                .diaInicioSituacionRevista1(1)
                .situacionRevista2(1)
                .diaInicioSituacionRevista2(0)
                .situacionRevista3(1)
                .diaInicioSituacionRevista3(0)
                .sueldoAdicionales(base.subtract(sac))
                .sac(sac)
                .horasExtras(BigDecimal.ZERO)
                .zonaDesfavorable(BigDecimal.ZERO)
                .vacaciones(BigDecimal.ZERO)
                .cantidadDiasTrabajados(DIAS_BASE.subtract(novedad18))
                .remuneracionImponible5(imponible5)
                .trabajadorConvencionado(0)
                .remuneracionImponible6(imponible6)
                .tipoOperacion(0)
                .adicionales(BigDecimal.ZERO)
                .premios(BigDecimal.ZERO)
                .remuneracion8(imponible8)
                .remuneracionImponible7(imponible7)
                .cantidadHorasExtras(0)
                .conceptosNoRemunerativos(conceptosNoRemunerativos)
                .maternidad(BigDecimal.ZERO)
                .rectificacionRemuneracion(BigDecimal.ZERO)
                .remuneracionImponible9(total)
                .contribucionTareaDiferencial(BigDecimal.ZERO)
                .horasTrabajadas(0)
                .seguroColectivoVidaObligatorio(1)
                .importeDetraccion(detraccion)
                .incrementoSalarial(incrementoSalarial)
                .remuneracionImponible11(BigDecimal.ZERO)
                .build();
    }

    private BigDecimal importe(Map<Integer, BigDecimal> items, int codigoId) {
        return items.getOrDefault(codigoId, BigDecimal.ZERO);
    }

    private String normalizarCuil(PersonaEntity persona) {
        if (persona == null || persona.getCuil() == null) {
            return null;
        }
        String digitos = persona.getCuil().replaceAll("\\D", "");
        return digitos.length() == 11 ? digitos : null;
    }

    private int valor(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private String detalleLegajos(List<Long> legajos) {
        String detalle = legajos.stream()
                .limit(MAX_LEGAJOS_EN_MENSAJE)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return legajos.size() > MAX_LEGAJOS_EN_MENSAJE ? detalle + ", ..." : detalle;
    }

    private String nombreArchivo(Integer anho, Integer mes, VarianteSijp variante) {
        String sufijo = variante == VarianteSijp.ETEC ? "-etec" : "";
        return "sijp-" + anho + "-" + mes + sufijo + ".txt";
    }

}
