package um.haberes.rest.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.rest.kotlin.internal.AfipContext;
import um.haberes.rest.kotlin.model.*;
import um.haberes.rest.model.LegajoBanco;
import um.haberes.rest.service.*;
import um.haberes.rest.service.internal.AfipContextService;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class LibroSueldoService {

    private List<Liquidacion> empleadosLiquidados;

    Map<Long, LegajoControl> legajoControls;

    private Control control;

    private final Environment environment;

    private final LiquidacionService liquidacionService;

    private final LegajoBancoService legajoBancoservice;

    private final ControlService controlService;

    private final ItemService itemService;

    private final LegajoControlService legajoControlService;

    private final AfipContextService afipContextService;

    private final CodigoGrupoService codigoGrupoService;

    @Autowired
    public LibroSueldoService(Environment environment, LiquidacionService liquidacionService, LegajoBancoService legajoBancoService,
                              ControlService controlService, ItemService itemService, LegajoControlService legajoControlService,
                              AfipContextService afipContextService, CodigoGrupoService codigoGrupoService) {
        this.environment = environment;
        this.liquidacionService = liquidacionService;
        this.legajoBancoservice = legajoBancoService;
        this.controlService = controlService;
        this.itemService = itemService;
        this.legajoControlService = legajoControlService;
        this.afipContextService = afipContextService;
        this.codigoGrupoService = codigoGrupoService;
    }

    public String generate(Integer anho, Integer mes) throws IOException {
        String path = environment.getProperty("path.files");
        String outputFilename = path + "LSD.zip";

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilename));

        String filename = path + "libro.sueldos." + anho + "." + mes;
        log.debug("Filename={}", filename);

        // pull empleados liquidados
        this.legajoControls = legajoControlService.findAllByPeriodo(anho, mes).stream().collect(Collectors.toMap(LegajoControl::getLegajoId, legajoControl -> legajoControl));

//        this.empleados = liquidacionService.findAllByPeriodo(anho, mes, 0);
        this.empleadosLiquidados = liquidacionService.findAllTestingWith3(anho, mes).stream().filter(empleado -> {
            boolean liquidado = false;
            if (this.legajoControls.containsKey(empleado.getLegajoId()))
                liquidado = this.legajoControls.get(empleado.getLegajoId()).getLiquidado() == 1;
            return liquidado;
        }).toList();

        // leyendo control del mes
        this.control = controlService.findByPeriodo(anho, mes);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        writeRegistrosTipo01(bufferedWriter, anho, mes);
        writeRegistrosTipo02(bufferedWriter);
        writeRegistrosTipo03(bufferedWriter, anho, mes);
        writeRegistrosTipo04(bufferedWriter, anho, mes);
        bufferedWriter.close();

        zipOutputStream.close();
        log.debug("Generado");
        return filename;
    }

    private void writeRegistrosTipo01(BufferedWriter bufferedWriter, Integer anho, Integer mes) throws IOException {
        // Registro tipo 01
        String line = "01";
        line += "30518594466";  // CUIT
        line += "SJ"; // DDJJ
        line += new DecimalFormat("0000").format(anho);
        line += new DecimalFormat("00").format(mes);
        line += "M"; // Mensual
        int numeroLiquidacion = 1;
        line += new DecimalFormat("00000").format(numeroLiquidacion); // Numero Liquidacion
        line += "30"; // Dias base
        line += new DecimalFormat("000000").format(this.empleadosLiquidados.size());
        bufferedWriter.write(line);
    }

    private void writeRegistrosTipo02(BufferedWriter bufferedWriter) throws IOException {
        // Registro tipo 02
        for (Liquidacion liquidacion : empleadosLiquidados) {
            bufferedWriter.write("\r\n");
            String line = "02";
            Long legajoId = liquidacion.getPersona().getLegajoId();
            String cuil = liquidacion.getPersona().getCuil();
            line += cuil;  // CUIL
            line += String.format("%-10s", legajoId); // legajo
            line += String.format("%50s", ""); // dependencia
            LegajoBanco legajoBanco = legajoBancoservice.findLegajoCbuPrincipal(legajoId, liquidacion.getAnho(), liquidacion.getMes());
            line += legajoBanco.getCbu(); // cbu
            line += new DecimalFormat("000").format(0); // tope
            line += control.getFechaPago().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // fecha pago
            line += String.format("%8s", ""); // fecha rubrica
            line += "3"; // acreditacion
            bufferedWriter.write(line);
        }
    }

    private void writeRegistrosTipo03(BufferedWriter bufferedWriter, Integer anho, Integer mes) throws IOException {
        // Registro tipo 03
        int semestre = 1;
        if (mes > 6) {
            semestre = 2;
        }
        Map<Integer, CodigoGrupo> grupos = codigoGrupoService.findAll().stream().collect(Collectors.toMap(CodigoGrupo::getCodigoId, codigo -> codigo));
        for (Liquidacion liquidacion : empleadosLiquidados) {
            Long legajoId = liquidacion.getPersona().getLegajoId();
            String cuil = liquidacion.getPersona().getCuil();
            for (Item item : itemService.findAllByLegajo(legajoId, liquidacion.getAnho(), liquidacion.getMes())) {
                log.debug("Item={}", item);
                if (item.getCodigo().getAfipConceptoSueldoIdPrimerSemestre() != null) {
                    bufferedWriter.write("\r\n");
                    String line = "03";
                    line += cuil; // cuil
                    Long afipConceptoSueldoId = semestre == 1 ? item.getCodigo().getAfipConceptoSueldoIdPrimerSemestre() : item.getCodigo().getAfipConceptoSueldoIdSegundoSemestre();
                    line += String.format("%-10s", afipConceptoSueldoId); // afip concepto sueldo
                    int cantidad = 1;
                    line += new DecimalFormat("00000").format(cantidad * 100); //cantidad
                    line += "M"; // unidades
                    line += new DecimalFormat("000000000000000").format(item.getImporte().multiply(new BigDecimal(100)).abs());
                    // normal behavior
                    String tipoMovimiento = "C";
                    if (item.getImporte().compareTo(BigDecimal.ZERO) < 0) {
                        tipoMovimiento = "D";
                    }
                    // if item is deduction
                    if (grupos.containsKey(item.getCodigoId())) {
                        CodigoGrupo codigoGrupo = grupos.get(item.getCodigoId());
                        if (codigoGrupo.getDeduccion() == 1) {
                            tipoMovimiento = "D";
                            if (item.getImporte().compareTo(BigDecimal.ZERO) < 0) {
                                tipoMovimiento = "C";
                            }
                        }
                    }
                    line += tipoMovimiento; // credito/debito
                    line += "      "; // Periodo ajuste
                    bufferedWriter.write(line);
                }
            }
        }
    }

    private void writeRegistrosTipo04(BufferedWriter bufferedWriter, Integer anho, Integer mes) throws IOException {
        // Registro tipo 04
        for (Liquidacion liquidacion : empleadosLiquidados) {
            AfipContext afipContext = afipContextService.makeByLegajo(liquidacion, this.control);
            bufferedWriter.write("\r\n");
            String line = "04";
            line += afipContext.getCuil();
            line += afipContext.getConyuge();
            line += new DecimalFormat("00").format(afipContext.getCantidadHijos());
            line += afipContext.getMarcaCCT();
            line += afipContext.getMarcaSCVO();
            line += afipContext.getMarcaCorrespondeReduccion();
            line += afipContext.getTipoEmpresa();
            line += afipContext.getTipoOperacion();
            line += new DecimalFormat("00").format(afipContext.getCodigoSituacion());
            line += String.format("%-2s", afipContext.getCodigoCondicion());
            line += new DecimalFormat("000").format(afipContext.getCodigoActividad());
            line += String.format("%-3s", afipContext.getCodigoModalidadContratacion());
            line += String.format("%-2s", afipContext.getCodigoSiniestrado());
            line += new DecimalFormat("00").format(afipContext.getCodigoLocalidad());
            line += new DecimalFormat("00").format(afipContext.getSituacionRevista1());
            line += new DecimalFormat("00").format(afipContext.getDiaInicioSituacionRevista1());
            line += new DecimalFormat("00").format(afipContext.getSituacionRevista2());
            line += new DecimalFormat("00").format(afipContext.getDiaInicioSituacionRevista2());
            line += new DecimalFormat("00").format(afipContext.getSituacionRevista3());
            line += new DecimalFormat("00").format(afipContext.getDiaInicioSituacionRevista3());
            line += new DecimalFormat("00").format(afipContext.getCantidadDiasTrabajados());
            line += new DecimalFormat("000").format(afipContext.getHorasTrabajadas());
            line += new DecimalFormat("00000").format(afipContext.getPorcentajeAporteAdicionalSS().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("00000").format(afipContext.getContribucionTareaDiferencial().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000").format(afipContext.getCodigoObraSocial());
            line += new DecimalFormat("00").format(afipContext.getCantidadAdherentes());
            line += new DecimalFormat("000000000000000").format(afipContext.getAporteAdicionalOS().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getContribucionAdicionalOS().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseCalculoDiferencialAportesOSyFSR().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseCalculoDiferencialOSyFSR().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseCalculoDiferencialLRT().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getRemuneracionMaternidadANSeS().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getRemuneracionBruta().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible1().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible2().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible3().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible4().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible5().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible6().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible7().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible8().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible9().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseParaElCalculoDiferencialDeAporteDeSeguridadSocial().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseParaElCalculoDiferencialDeContribucionesDeSeguridadSocial().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getBaseImponible10().multiply(BigDecimal.valueOf(100)));
            line += new DecimalFormat("000000000000000").format(afipContext.getImporteDetraer().multiply(BigDecimal.valueOf(100)));
            bufferedWriter.write(line);
        }
    }

}
