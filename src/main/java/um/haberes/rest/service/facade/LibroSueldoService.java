package um.haberes.rest.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.LegajoBancoException;
import um.haberes.rest.kotlin.model.internal.AfipContext;
import um.haberes.rest.kotlin.model.*;
import um.haberes.rest.service.*;
import um.haberes.rest.service.internal.AfipContextService;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
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

    public String generate(Integer anho, Integer mes, List<Long> legajoIds) throws IOException {
        String path = environment.getProperty("path.files");
        String outputFilename = path + "LSD.zip";

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilename));

        String filename = path + "libro.sueldos." + anho + "." + mes + ".txt";
        log.debug("Filename={}", filename);
        String filenameExcel = path + "libro.sueldos." + anho + "." + mes + ".xlsx";
        log.debug("Filename Excel={}", filenameExcel);

        Workbook book = new XSSFWorkbook();
        CellStyle styleNormal = book.createCellStyle();
        Font fontNormal = book.createFont();
        fontNormal.setBold(false);
        styleNormal.setFont(fontNormal);
        CellStyle styleBold = book.createCellStyle();
        Font fontBold = book.createFont();
        fontBold.setBold(true);
        styleBold.setFont(fontBold);

        // pull empleados liquidados
        this.legajoControls = legajoControlService.findAllByPeriodo(anho, mes).stream().collect(Collectors.toMap(LegajoControl::getLegajoId, legajoControl -> legajoControl));

        if (legajoIds.size() == 0) {
            this.empleadosLiquidados = liquidacionService.findAllByPeriodo(anho, mes, 0).stream().filter(empleado -> {
                boolean liquidado = false;
                if (this.legajoControls.containsKey(empleado.getLegajoId()))
                    liquidado = this.legajoControls.get(empleado.getLegajoId()).getLiquidado() == 1;
                return liquidado;
            }).toList();
        } else {
            this.empleadosLiquidados = liquidacionService.findAllByPeriodoAndLegajoIds(anho, mes, legajoIds).stream().filter(empleado -> {
                boolean liquidado = false;
                if (this.legajoControls.containsKey(empleado.getLegajoId()))
                    liquidado = this.legajoControls.get(empleado.getLegajoId()).getLiquidado() == 1;
                return liquidado;
            }).toList();
        }

        // leyendo control del mes
        this.control = controlService.findByPeriodo(anho, mes);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        Sheet sheet = book.createSheet("01");
        writeRegistrosTipo01(bufferedWriter, anho, mes, sheet, styleNormal, styleBold);
        sheet = book.createSheet("02");
        writeRegistrosTipo02(bufferedWriter, sheet, styleNormal, styleBold);
        sheet = book.createSheet("03");
        writeRegistrosTipo03(bufferedWriter, anho, mes, sheet, styleNormal, styleBold);
        sheet = book.createSheet("04");
        writeRegistrosTipo04(bufferedWriter, anho, mes, sheet, styleNormal, styleBold);
        bufferedWriter.close();

        try {
            File file = new File(filenameExcel);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            book.close();
        } catch (Exception e) {
            log.debug("Error generando libro sueldos excel");
        }

        byte[] buffer = new byte[1024];
        log.debug("Deflating {} ...", filename);
        zipOutputStream.putNextEntry(new ZipEntry(new File(filename).getName()));
        FileInputStream fileInputStream = new FileInputStream(filename);
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        zipOutputStream.closeEntry();

        log.debug("Deflating {} ...", filenameExcel);
        zipOutputStream.putNextEntry(new ZipEntry(new File(filenameExcel).getName()));
        fileInputStream = new FileInputStream(filenameExcel);
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        zipOutputStream.closeEntry();

        zipOutputStream.close();
        log.debug("Generado");
        return outputFilename;
    }

    private void writeRegistrosTipo01(BufferedWriter bufferedWriter, Integer anho, Integer mes, Sheet sheet, CellStyle styleNormal, CellStyle styleBold) throws IOException {
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
        // Excel tipo 01
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        setCellString(row, 0, "Identificador de Registro", styleBold);
        setCellString(row, 1, "CUIT", styleBold);
        setCellString(row, 2, "Identificación del envío", styleBold);
        setCellString(row, 3, "Año", styleBold);
        setCellString(row, 4, "Mes", styleBold);
        setCellString(row, 5, "Tipo Liquidación", styleBold);
        setCellString(row, 6, "Número de liquidación", styleBold);
        setCellString(row, 7, "Días base", styleBold);
        setCellString(row, 8, "Cantidad Registros 04", styleBold);
        // dato
        row = sheet.createRow(++fila);
        setCellString(row, 0, "01", styleNormal);
        setCellString(row, 1, "30518594466", styleNormal);
        setCellString(row, 2, "SJ", styleNormal);
        setCellInteger(row, 3, anho, styleNormal);
        setCellInteger(row, 4, mes, styleNormal);
        setCellString(row, 5, "M", styleNormal);
        setCellInteger(row, 6, 1, styleNormal);
        setCellInteger(row, 7, 30, styleNormal);
        setCellInteger(row, 8, this.empleadosLiquidados.size(), styleNormal);

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

    }

    private void writeRegistrosTipo02(BufferedWriter bufferedWriter, Sheet sheet, CellStyle styleNormal, CellStyle styleBold) throws IOException {
        // Excel tipo 02
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        setCellString(row, 0, "Identificador de Registro", styleBold);
        setCellString(row, 1, "CUIL", styleBold);
        setCellString(row, 2, "Legajo", styleBold);
        setCellString(row, 3, "Dependencia en Revista", styleBold);
        setCellString(row, 4, "CBU", styleBold);
        setCellString(row, 5, "Cantidad de días tope", styleBold);
        setCellString(row, 6, "Fecha Pago", styleBold);
        setCellString(row, 7, "Fecha Rubrica", styleBold);
        setCellString(row, 8, "Forma de Pago", styleBold);
        // Registro tipo 02
        for (Liquidacion liquidacion : empleadosLiquidados) {
            bufferedWriter.write("\r\n");
            String line = "02";
            Long legajoId = liquidacion.getPersona().getLegajoId();
            String cuil = liquidacion.getPersona().getCuil();
            line += cuil;  // CUIL
            line += String.format("%-10s", legajoId); // legajo
            line += String.format("%50s", ""); // dependencia
            String cbu = "";
            try {
                LegajoBanco legajoBanco = legajoBancoservice.findLegajoCbuPrincipal(legajoId, liquidacion.getAnho(), liquidacion.getMes());
                cbu = legajoBanco.getCbu();
                log.info("cbu try={}", cbu);
            } catch (LegajoBancoException e) {
                cbu = String.format("%022d", 0); // sin cbu
                log.info("cbu catch={}", cbu);
            }
            line += cbu; // cbu
            line += new DecimalFormat("000").format(0); // tope
            line += control.getFechaPago().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // fecha pago
            line += String.format("%8s", ""); // fecha rubrica
            line += "3"; // acreditacion
            bufferedWriter.write(line);
            // dato
            row = sheet.createRow(++fila);
            setCellString(row, 0, "02", styleNormal);
            setCellString(row, 1, cuil, styleNormal);
            setCellLong(row, 2, legajoId, styleNormal);
            setCellString(row, 3, "", styleNormal);
            setCellString(row, 4, cbu, styleNormal);
            setCellInteger(row, 5, 0, styleNormal);
            setCellString(row, 6, control.getFechaPago().format(DateTimeFormatter.ofPattern("yyyyMMdd")), styleNormal);
            setCellString(row, 7, "", styleNormal);
            setCellInteger(row, 8, 3, styleNormal);
        }
        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);
    }

    private void writeRegistrosTipo03(BufferedWriter bufferedWriter, Integer anho, Integer mes, Sheet
            sheet, CellStyle styleNormal, CellStyle styleBold) throws IOException {
        // Excel tipo 03
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        setCellString(row, 0, "Identificador de Registro", styleBold);
        setCellString(row, 1, "CUIL", styleBold);
        setCellString(row, 2, "Código Concepto", styleBold);
        setCellString(row, 3, "Cantidad", styleBold);
        setCellString(row, 4, "Unidades", styleBold);
        setCellString(row, 5, "Importe", styleBold);
        setCellString(row, 6, "Debito/Credito", styleBold);
        setCellString(row, 7, "Periodo Ajuste", styleBold);
        setCellString(row, 8, "Interno", styleBold);
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
                    // dato
                    row = sheet.createRow(++fila);
                    setCellString(row, 0, "03", styleNormal);
                    setCellString(row, 1, cuil, styleNormal);
                    setCellLong(row, 2, afipConceptoSueldoId, styleNormal);
                    setCellInteger(row, 3, cantidad, styleNormal);
                    setCellString(row, 4, "M", styleNormal);
                    setCellBigDecimal(row, 5, item.getImporte(), styleNormal);
                    setCellString(row, 6, tipoMovimiento, styleNormal);
                    setCellString(row, 7, "", styleNormal);
                    setCellString(row, 8, item.getCodigoNombre(), styleNormal);
                }
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

    }

    private void writeRegistrosTipo04(BufferedWriter bufferedWriter, Integer anho, Integer mes, Sheet
            sheet, CellStyle styleNormal, CellStyle styleBold) throws IOException {
        // Excel tipo 04
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        setCellString(row, 0, "Identificador de Registro", styleBold);
        setCellString(row, 1, "CUIL", styleBold);
        setCellString(row, 2, "Conyuge", styleBold);
        setCellString(row, 3, "Cantidad de hijos", styleBold);
        setCellString(row, 4, "Marca CCT", styleBold);
        setCellString(row, 5, "Marca SCVO", styleBold);
        setCellString(row, 6, "Marca Corresponde Reducción", styleBold);
        setCellString(row, 7, "Tipo Empresa", styleBold);
        setCellString(row, 8, "Tipo Operación", styleBold);
        setCellString(row, 9, "Código Situación", styleBold);
        setCellString(row, 10, "Código Condición", styleBold);
        setCellString(row, 11, "Código Actividad", styleBold);
        setCellString(row, 12, "Código Modalidad Contratación", styleBold);
        setCellString(row, 13, "Código Siniestrado", styleBold);
        setCellString(row, 14, "Código Localidad", styleBold);
        setCellString(row, 15, "Situación Revista 1", styleBold);
        setCellString(row, 16, "Día Inicio Situación Revista 1", styleBold);
        setCellString(row, 17, "Situación Revista 2", styleBold);
        setCellString(row, 18, "Día Inicio Situación Revista 2", styleBold);
        setCellString(row, 19, "Situación Revista 3", styleBold);
        setCellString(row, 20, "Día Inicio Situación Revista 3", styleBold);
        setCellString(row, 21, "Cantidad Días Trabajados", styleBold);
        setCellString(row, 22, "Horas Trabajadas", styleBold);
        setCellString(row, 23, "Porcentaje Aporte Adicional SS", styleBold);
        setCellString(row, 24, "Contribución Tarea Diferencial", styleBold);
        setCellString(row, 25, "Código Obra Social", styleBold);
        setCellString(row, 26, "Cantidad Adherentes", styleBold);
        setCellString(row, 27, "Aporte Adicional OS", styleBold);
        setCellString(row, 28, "Contribución Adicional OS", styleBold);
        setCellString(row, 29, "Base Cálculo Diferencial Aportes OS y FSR", styleBold);
        setCellString(row, 30, "Base Cálculo Diferencial OS y FSR", styleBold);
        setCellString(row, 31, "Base Cálculo Diferencial LRT", styleBold);
        setCellString(row, 32, "Remuneración Maternidad ANSeS", styleBold);
        setCellString(row, 33, "Remuneración Bruta", styleBold);
        setCellString(row, 34, "Base Imponible 1", styleBold);
        setCellString(row, 35, "Base Imponible 2", styleBold);
        setCellString(row, 36, "Base Imponible 3", styleBold);
        setCellString(row, 37, "Base Imponible 4", styleBold);
        setCellString(row, 38, "Base Imponible 5", styleBold);
        setCellString(row, 39, "Base Imponible 6", styleBold);
        setCellString(row, 40, "Base Imponible 7", styleBold);
        setCellString(row, 41, "Base Imponible 8", styleBold);
        setCellString(row, 42, "Base Imponible 9", styleBold);
        setCellString(row, 43, "Base Cálculo Diferencial Aporte Seg. Social", styleBold);
        setCellString(row, 44, "Base Cálculo Diferencial Contribuciones Seg. Social", styleBold);
        setCellString(row, 45, "Base Imponible 10", styleBold);
        setCellString(row, 46, "Importe a Detraer", styleBold);
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
            // dato
            row = sheet.createRow(++fila);
            setCellString(row, 0, "04", styleNormal);
            setCellString(row, 1, afipContext.getCuil(), styleNormal);
            setCellInteger(row, 2, afipContext.getConyuge(), styleNormal);
            setCellInteger(row, 3, afipContext.getCantidadHijos(), styleNormal);
            setCellInteger(row, 4, afipContext.getMarcaCCT(), styleNormal);
            setCellInteger(row, 5, afipContext.getMarcaSCVO(), styleNormal);
            setCellInteger(row, 6, afipContext.getMarcaCorrespondeReduccion(), styleNormal);
            setCellInteger(row, 7, afipContext.getTipoEmpresa(), styleNormal);
            setCellInteger(row, 8, afipContext.getTipoOperacion(), styleNormal);
            setCellInteger(row, 9, afipContext.getCodigoSituacion(), styleNormal);
            setCellInteger(row, 10, afipContext.getCodigoCondicion(), styleNormal);
            setCellInteger(row, 11, afipContext.getCodigoActividad(), styleNormal);
            setCellInteger(row, 12, afipContext.getCodigoModalidadContratacion(), styleNormal);
            setCellInteger(row, 13, afipContext.getCodigoSiniestrado(), styleNormal);
            setCellInteger(row, 14, afipContext.getCodigoLocalidad(), styleNormal);
            setCellInteger(row, 15, afipContext.getSituacionRevista1(), styleNormal);
            setCellInteger(row, 16, afipContext.getDiaInicioSituacionRevista1(), styleNormal);
            setCellInteger(row, 17, afipContext.getSituacionRevista2(), styleNormal);
            setCellInteger(row, 18, afipContext.getDiaInicioSituacionRevista2(), styleNormal);
            setCellInteger(row, 19, afipContext.getSituacionRevista2(), styleNormal);
            setCellInteger(row, 20, afipContext.getDiaInicioSituacionRevista2(), styleNormal);
            setCellInteger(row, 21, afipContext.getCantidadDiasTrabajados(), styleNormal);
            setCellInteger(row, 22, afipContext.getHorasTrabajadas(), styleNormal);
            setCellBigDecimal(row, 23, afipContext.getPorcentajeAporteAdicionalSS(), styleNormal);
            setCellBigDecimal(row, 24, afipContext.getContribucionTareaDiferencial(), styleNormal);
            setCellLong(row, 25, afipContext.getCodigoObraSocial(), styleNormal);
            setCellInteger(row, 26, afipContext.getCantidadAdherentes(), styleNormal);
            setCellBigDecimal(row, 27, afipContext.getAporteAdicionalOS(), styleNormal);
            setCellBigDecimal(row, 28, afipContext.getContribucionAdicionalOS(), styleNormal);
            setCellBigDecimal(row, 29, afipContext.getBaseCalculoDiferencialAportesOSyFSR(), styleNormal);
            setCellBigDecimal(row, 30, afipContext.getBaseCalculoDiferencialOSyFSR(), styleNormal);
            setCellBigDecimal(row, 31, afipContext.getBaseCalculoDiferencialLRT(), styleNormal);
            setCellBigDecimal(row, 32, afipContext.getRemuneracionMaternidadANSeS(), styleNormal);
            setCellBigDecimal(row, 33, afipContext.getRemuneracionBruta(), styleNormal);
            setCellBigDecimal(row, 34, afipContext.getBaseImponible1(), styleNormal);
            setCellBigDecimal(row, 35, afipContext.getBaseImponible2(), styleNormal);
            setCellBigDecimal(row, 36, afipContext.getBaseImponible3(), styleNormal);
            setCellBigDecimal(row, 37, afipContext.getBaseImponible4(), styleNormal);
            setCellBigDecimal(row, 38, afipContext.getBaseImponible5(), styleNormal);
            setCellBigDecimal(row, 39, afipContext.getBaseImponible6(), styleNormal);
            setCellBigDecimal(row, 40, afipContext.getBaseImponible7(), styleNormal);
            setCellBigDecimal(row, 41, afipContext.getBaseImponible8(), styleNormal);
            setCellBigDecimal(row, 42, afipContext.getBaseImponible9(), styleNormal);
            setCellBigDecimal(row, 43, afipContext.getBaseParaElCalculoDiferencialDeAporteDeSeguridadSocial(), styleNormal);
            setCellBigDecimal(row, 44, afipContext.getBaseParaElCalculoDiferencialDeContribucionesDeSeguridadSocial(), styleNormal);
            setCellBigDecimal(row, 45, afipContext.getBaseImponible10(), styleNormal);
            setCellBigDecimal(row, 46, afipContext.getImporteDetraer(), styleNormal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);
    }

    private void setCellOffsetDateTime(Row row, int column, OffsetDateTime value, CellStyle style) {
        setCellString(row, column, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(value), style);
    }

    private void setCellBigDecimal(Row row, int column, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private void setCellString(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellLong(Row row, int column, Long value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellInteger(Row row, int column, Integer value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellByte(Row row, int column, Byte value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private Sheet makeWorkbook(Workbook book, CellStyle styleNormal, CellStyle styleBold, String sheetName) {
        book = new XSSFWorkbook();
        styleNormal = book.createCellStyle();
        Font fontNormal = book.createFont();
        fontNormal.setBold(false);
        styleNormal.setFont(fontNormal);

        styleBold = book.createCellStyle();
        Font fontBold = book.createFont();
        fontBold.setBold(true);
        styleBold.setFont(fontBold);

        return book.createSheet(sheetName);
    }

}
