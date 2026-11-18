package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.exception.ArchivoAcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacionFiltro;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.LegajoAcreditar;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.RechazoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.layout.SantanderLayout;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.GenerarArchivoAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.PrepararAcreditacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.mapper.LiquidacionMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.model.ExcluidoEntity;
import um.haberes.core.model.internal.OrdenPagoRequest;
import um.haberes.core.service.ExcluidoService;
import um.haberes.core.service.LegajoBancoService;
import um.haberes.core.service.facade.OrdenPagoService;

/**
 * Réplica de {@code generateFileSantanderAll}/{@code generateFileOtrosBancosAll} y de las
 * variantes {@code Collection} (individuales) del legacy.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GenerarArchivoAcreditacionUseCaseImpl implements GenerarArchivoAcreditacionUseCase {

    private static final String MOTIVO_EXCLUIDO = "Excluido";
    private static final String MOTIVO_CBU = "Error de CBU";
    private static final String MOTIVO_IMPORTE = "Sin importe a PAGAR";
    private static final String MOTIVO_CUIL = "Error de CUIL";
    private static final String MOTIVO_SANTANDER = "No corresponde SANTANDER";
    private static final String MOTIVO_OTROS = "No Corresponde OTROS BANCOS";
    private static final String MOTIVO_SIN_LIQUIDACION = "Sin liquidación";
    private static final String MOTIVO_SIN_PERSONA = "Sin persona";

    private static final String PREFIJO_CBU_SANTANDER = "072";

    private final LegajoBancoService legajoBancoService;
    private final ExcluidoService excluidoService;
    private final LiquidacionService liquidacionService;
    private final LiquidacionMapper liquidacionMapper;
    private final AcreditacionService acreditacionService;
    private final OrdenPagoService ordenPagoService;
    private final PrepararAcreditacionesUseCase prepararAcreditacionesUseCase;

    private enum Banco {
        SANTANDER, OTROS_BANCOS
    }

    @Override
    @Transactional
    public ArchivoAcreditacion generarSantander(ArchivoAcreditacionFiltro filtro) {
        return generar(filtro, Banco.SANTANDER);
    }

    @Override
    @Transactional
    public ArchivoAcreditacion generarOtrosBancos(ArchivoAcreditacionFiltro filtro) {
        return generar(filtro, Banco.OTROS_BANCOS);
    }

    private ArchivoAcreditacion generar(ArchivoAcreditacionFiltro filtro, Banco banco) {
        validar(filtro);
        Integer anho = filtro.getAnho();
        Integer mes = filtro.getMes();
        LocalDate fechaPago = filtro.getFechaPago();
        boolean individual = Boolean.TRUE.equals(filtro.getIndividual());
        boolean cci = Boolean.TRUE.equals(filtro.getCci()) && banco == Banco.OTROS_BANCOS;
        int numEnvio = filtro.getNumEnvio() == null || filtro.getNumEnvio() <= 0 ? 1 : filtro.getNumEnvio();
        int acuerdo = switch (banco) {
            case SANTANDER -> SantanderLayout.ACUERDO_SANTANDER;
            case OTROS_BANCOS -> cci ? SantanderLayout.ACUERDO_OTROS_BANCOS_CCI
                    : SantanderLayout.ACUERDO_OTROS_BANCOS;
        };
        int formaPago = switch (banco) {
            case SANTANDER -> SantanderLayout.FORMA_PAGO_SANTANDER;
            case OTROS_BANCOS -> cci ? SantanderLayout.FORMA_PAGO_OTROS_BANCOS_CCI
                    : SantanderLayout.FORMA_PAGO_OTROS_BANCOS;
        };

        List<Candidato> candidatos;
        if (individual) {
            candidatos = candidatosIndividuales(filtro);
        } else {
            prepararPeriodo(filtro);
            candidatos = candidatosMasivo(filtro, banco);
        }
        if (candidatos.isEmpty()) {
            throw new ArchivoAcreditacionException("No hay legajos para acreditar en el período");
        }

        Set<Long> excluidos = individual ? Set.of()
                : excluidoService.findAllByPeriodo(anho, mes).stream().map(ExcluidoEntity::getLegajoId)
                        .collect(Collectors.toSet());

        List<String> lineas = new ArrayList<>();
        lineas.add(SantanderLayout.header(acuerdo, numEnvio));
        List<RechazoAcreditacion> rechazos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalRechazado = BigDecimal.ZERO;
        int cantidad = 0;

        for (Candidato candidato : candidatos) {
            if (individual) {
                excluidoService.deleteByUnique(candidato.legajoId(), anho, mes);
            }
            BigDecimal importe = candidato.acreditado() == null ? BigDecimal.ZERO : candidato.acreditado();
            String apellidoNombre = candidato.persona() == null ? "" : candidato.persona().getApellidoNombre();

            List<String> motivos = motivos(candidato, banco, excluidos);
            if (!motivos.isEmpty()) {
                totalRechazado = totalRechazado.add(importe);
                rechazos.add(RechazoAcreditacion.builder()
                        .legajoId(candidato.legajoId())
                        .apellidoNombre(apellidoNombre)
                        .motivo(String.join("; ", motivos))
                        .importe(importe)
                        .subtotal(totalRechazado)
                        .build());
                continue;
            }

            lineas.add(SantanderLayout.detalle(candidato.legajoId(), anho, mes, apellidoNombre,
                    candidato.persona().getCuil(), candidato.cbu(), fechaPago, importe, formaPago));
            total = total.add(importe);
            cantidad++;
            liquidacionService.acreditado(liquidacionMapper.toDomain(candidato.liquidacion()),
                    fechaPago.atStartOfDay().atOffset(ZoneOffset.UTC));
        }

        if (cantidad == 0) {
            throw new ArchivoAcreditacionException(
                    "Ningún legajo del período pudo acreditarse. Revise la grilla de rechazos.");
        }

        lineas.add(SantanderLayout.trailer(total, cantidad));
        boolean periodoAcreditado = false;
        if (!individual) {
            marcarPeriodo(anho, mes);
            periodoAcreditado = true;
            generarOrdenPago(filtro, banco, total);
        }

        return ArchivoAcreditacion.builder()
                .nombreArchivo(nombreArchivo(banco, individual, anho, mes, cci))
                .contenido(SantanderLayout.serializar(lineas))
                .cantidad(cantidad)
                .total(total)
                .periodoAcreditado(periodoAcreditado)
                .rechazos(rechazos)
                .build();
    }

    private List<String> motivos(Candidato candidato, Banco banco, Set<Long> excluidos) {
        List<String> motivos = new ArrayList<>();
        if (candidato.motivoForzado() != null) {
            motivos.add(candidato.motivoForzado());
            return motivos;
        }
        if (excluidos.contains(candidato.legajoId())) {
            motivos.add(MOTIVO_EXCLUIDO);
            return motivos;
        }
        if (candidato.persona() == null) {
            motivos.add(MOTIVO_SIN_PERSONA);
            return motivos;
        }
        String cbu = candidato.cbu() == null ? "" : candidato.cbu();
        if (banco == Banco.SANTANDER && !cbu.startsWith(PREFIJO_CBU_SANTANDER)) {
            motivos.add(MOTIVO_SANTANDER);
            return motivos;
        }
        if (banco == Banco.OTROS_BANCOS && cbu.startsWith(PREFIJO_CBU_SANTANDER)) {
            motivos.add(MOTIVO_OTROS);
            return motivos;
        }
        boolean cbuOk = cbu.length() == 22;
        BigDecimal importe = candidato.acreditado() == null ? BigDecimal.ZERO : candidato.acreditado();
        boolean importeOk = importe.compareTo(BigDecimal.ZERO) > 0;
        if (banco == Banco.SANTANDER) {
            if (!cbuOk) {
                motivos.add(MOTIVO_CBU);
            }
            if (!importeOk) {
                motivos.add(MOTIVO_IMPORTE);
            }
        } else if (!importeOk) {
            // El legacy de Otros Bancos deja un solo motivo: el último pisa al anterior.
            motivos.add(MOTIVO_IMPORTE);
        } else if (!cbuOk) {
            motivos.add(MOTIVO_CBU);
        }
        if (importeOk && !cuilValido(candidato.persona().getCuil())) {
            motivos.add(MOTIVO_CUIL);
        }
        if (motivos.isEmpty() && candidato.liquidacion() == null) {
            motivos.add(MOTIVO_SIN_LIQUIDACION);
        }
        return motivos;
    }

    private void prepararPeriodo(ArchivoAcreditacionFiltro filtro) {
        Integer codigoId = filtro.getCodigoId() == null ? 0 : filtro.getCodigoId();
        if (codigoId > 0) {
            prepararAcreditacionesUseCase.prepararPorCodigo(filtro.getAnho(), filtro.getMes(), codigoId);
        } else {
            prepararAcreditacionesUseCase.prepararPeriodo(filtro.getAnho(), filtro.getMes());
        }
    }

    private List<Candidato> candidatosMasivo(ArchivoAcreditacionFiltro filtro, Banco banco) {
        String salida = normalizarSalida(filtro.getSalida());
        int dependenciaId = filtro.getDependenciaId() == null ? 0 : filtro.getDependenciaId();
        int codigoId = filtro.getCodigoId() == null ? 0 : filtro.getCodigoId();
        List<LegajoBancoEntity> legajoBancos;
        if (banco == Banco.SANTANDER) {
            legajoBancos = codigoId > 0
                    ? legajoBancoService.findAllSantanderConCodigo(salida, filtro.getAnho(), filtro.getMes(),
                            dependenciaId, codigoId)
                    : legajoBancoService.findAllSantander(salida, filtro.getAnho(), filtro.getMes(), dependenciaId);
        } else {
            legajoBancos = codigoId > 0
                    ? legajoBancoService.findAllOtrosBancosConCodigo(salida, filtro.getAnho(), filtro.getMes(),
                            dependenciaId, codigoId)
                    : legajoBancoService.findAllOtrosBancos(salida, filtro.getAnho(), filtro.getMes(), dependenciaId);
        }
        return legajoBancos.stream()
                .map(legajoBanco -> new Candidato(legajoBanco.getLegajoId(), legajoBanco.getCbu(),
                        legajoBanco.getAcreditado(), legajoBanco.getPersona(), legajoBanco.getLiquidacion(), null))
                .toList();
    }

    private List<Candidato> candidatosIndividuales(ArchivoAcreditacionFiltro filtro) {
        List<Candidato> candidatos = new ArrayList<>();
        Set<String> vistos = new HashSet<>();
        for (LegajoAcreditar legajo : filtro.getLegajos()) {
            if (legajo.getLegajoId() == null) {
                continue;
            }
            String cbu = legajo.getCbu() == null ? "" : legajo.getCbu();
            if (!vistos.add(legajo.getLegajoId() + ":" + cbu)) {
                continue;
            }
            LegajoBancoEntity legajoBanco;
            try {
                legajoBanco = legajoBancoService.findByUnique(legajo.getLegajoId(), filtro.getAnho(),
                        filtro.getMes(), cbu);
            } catch (LegajoBancoException e) {
                legajoBanco = null;
            }
            if (legajoBanco == null) {
                candidatos.add(new Candidato(legajo.getLegajoId(), cbu, legajo.getAcreditado(), null, null,
                        MOTIVO_CBU));
            } else {
                BigDecimal acreditado = legajo.getAcreditado() == null ? legajoBanco.getAcreditado()
                        : legajo.getAcreditado();
                candidatos.add(new Candidato(legajo.getLegajoId(), cbu, acreditado, legajoBanco.getPersona(),
                        legajoBanco.getLiquidacion(), null));
            }
        }
        return candidatos;
    }

    private void marcarPeriodo(Integer anho, Integer mes) {
        try {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, mes);
            acreditacion.setAcreditado((byte) 1);
            acreditacionService.update(acreditacion, acreditacion.getAcreditacionId());
        } catch (AcreditacionException e) {
            acreditacionService.add(Acreditacion.builder()
                    .anho(anho)
                    .mes(mes)
                    .acreditado((byte) 1)
                    .ordenContable(0)
                    .sueldosOriginal(BigDecimal.ZERO)
                    .sueldosAjustados(BigDecimal.ZERO)
                    .contribucionesPatronales(BigDecimal.ZERO)
                    .build());
        }
    }

    private void generarOrdenPago(ArchivoAcreditacionFiltro filtro, Banco banco, BigDecimal total) {
        OrdenPagoRequest request = new OrdenPagoRequest();
        request.setAnho(filtro.getAnho());
        request.setMes(filtro.getMes());
        request.setFechaPago(filtro.getFechaPago().atStartOfDay().atOffset(ZoneOffset.UTC));
        request.setTotalSantander(banco == Banco.SANTANDER ? total : BigDecimal.ZERO);
        request.setTotalOtrosBancos(banco == Banco.OTROS_BANCOS ? total : BigDecimal.ZERO);
        request.setIndividual(false);
        ordenPagoService.generateOrdenPago(request);
    }

    private static String nombreArchivo(Banco banco, boolean individual, Integer anho, Integer mes, boolean cci) {
        String base = banco == Banco.SANTANDER ? "santander" : "otrosbancos";
        String sufijo = individual ? "-individual" : "";
        return String.format("%s%s-%d-%02d%s.txt", base, sufijo, anho, mes, cci && !individual ? "-cci" : "");
    }

    private static String normalizarSalida(String salida) {
        if (salida == null) {
            return "T";
        }
        return switch (salida.toUpperCase()) {
            case "A", "D" -> salida.toUpperCase();
            default -> "T";
        };
    }

    private static boolean cuilValido(String cuil) {
        return cuil != null && cuil.matches("\\d{11}");
    }

    private static void validar(ArchivoAcreditacionFiltro filtro) {
        if (filtro == null || filtro.getAnho() == null || filtro.getMes() == null) {
            throw new ArchivoAcreditacionException("Debe indicar año y mes");
        }
        if (filtro.getMes() < 1 || filtro.getMes() > 12) {
            throw new ArchivoAcreditacionException("Mes inválido");
        }
        if (filtro.getFechaPago() == null) {
            throw new ArchivoAcreditacionException("Debe indicar la fecha de pago");
        }
        if (Boolean.TRUE.equals(filtro.getIndividual())
                && (filtro.getLegajos() == null || filtro.getLegajos().isEmpty())) {
            throw new ArchivoAcreditacionException("Debe indicar al menos un legajo a acreditar");
        }
    }

    private record Candidato(Long legajoId, String cbu, BigDecimal acreditado, PersonaEntity persona,
            LiquidacionEntity liquidacion, String motivoForzado) {
    }
}
