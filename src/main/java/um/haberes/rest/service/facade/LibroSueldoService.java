package um.haberes.rest.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.rest.kotlin.model.Control;
import um.haberes.rest.kotlin.model.Item;
import um.haberes.rest.kotlin.model.LegajoControl;
import um.haberes.rest.kotlin.model.Liquidacion;
import um.haberes.rest.model.LegajoBanco;
import um.haberes.rest.service.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibroSueldoService {

    private List<Liquidacion> empleados;

    private Control control;

    private final Environment environment;

    private final LiquidacionService liquidacionService;

    private final LegajoBancoService legajoBancoservice;

    private final ControlService controlService;

    private final ItemService itemService;

    private final LegajoControlService legajoControlService

    @Autowired
    public LibroSueldoService(Environment environment, LiquidacionService liquidacionService, LegajoBancoService legajoBancoService,
                              ControlService controlService, ItemService itemService, LegajoControlService legajoControlService) {
        this.environment = environment;
        this.liquidacionService = liquidacionService;
        this.legajoBancoservice = legajoBancoService;
        this.controlService = controlService;
        this.itemService = itemService;
        this.legajoControlService = legajoControlService;
    }

    public String generate(Integer anho, Integer mes) throws IOException {
        String path = environment.getProperty("path.files");
        String filename = path + "libro.sueldos." + anho + "." + mes;
        log.debug("Filename={}", filename);

        // pull empleados liquidados
//        this.empleados = liquidacionService.findAllByPeriodo(anho, mes, 0);
        this.empleados = liquidacionService.findAllTestingWith3(anho, mes);

        // leyendo control del mes
        this.control = controlService.findByPeriodo(anho, mes);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        writeRegistrosTipo01(bufferedWriter, anho, mes);
        writeRegistrosTipo02(bufferedWriter);
        writeRegistrosTipo03(bufferedWriter, anho, mes);
        writeRegistrosTipo04(bufferedWriter, anho, mes);
        bufferedWriter.close();
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
        line += new DecimalFormat("000000").format(this.empleados.size());
        bufferedWriter.write(line);
        bufferedWriter.write("\r\n");
    }

    private void writeRegistrosTipo02(BufferedWriter bufferedWriter) throws IOException {
        // Registro tipo 02
        for (Liquidacion liquidacion : empleados) {
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
            bufferedWriter.write("\r\n");
        }
    }

    private void writeRegistrosTipo03(BufferedWriter bufferedWriter, Integer anho, Integer mes) throws IOException {
        // Registro tipo 03
        int semestre = 1;
        if (mes > 6) {
            semestre = 2;
        }
        for (Liquidacion liquidacion : empleados) {
            Long legajoId = liquidacion.getPersona().getLegajoId();
            String cuil = liquidacion.getPersona().getCuil();
            for (Item item : itemService.findAllByLegajo(legajoId, liquidacion.getAnho(), liquidacion.getMes())) {
                log.debug("Item={}", item);
                if (item.getCodigo().getAfipConceptoSueldoIdPrimerSemestre() != null) {
                    String line = "03";
                    line += cuil; // cuil
                    Long afipConceptoSueldoId = semestre == 1 ? item.getCodigo().getAfipConceptoSueldoIdPrimerSemestre() : item.getCodigo().getAfipConceptoSueldoIdSegundoSemestre();
                    line += String.format("%-10s", afipConceptoSueldoId); // afip concepto sueldo
                    int cantidad = 1;
                    line += new DecimalFormat("00000").format(cantidad * 100); //cantidad
                    line += "M"; // unidades
                    line += new DecimalFormat("000000000000000").format(item.getImporte().multiply(new BigDecimal(100)).abs());
                    String tipoMovimiento = "C";
                    if (item.getImporte().compareTo(BigDecimal.ZERO) < 0) {
                        tipoMovimiento = "D";
                    }
                    line += tipoMovimiento; // credito/debito
                    line += "      "; // Periodo ajuste
                    bufferedWriter.write(line);
                    bufferedWriter.write("\r\n");
                }
            }
        }
    }

    private void writeRegistrosTipo04(BufferedWriter bufferedWriter, Integer anho, Integer mes) throws IOException {
        // Registro tipo 04
        Map<Long, LegajoControl> legajoControls = legajoControlService.findAllByPeriodo(anho, mes).stream().collect(Collectors.toMap(LegajoControl::getLegajoId, legajoControl -> legajoControl));
        for (Liquidacion liquidacion : empleados) {
            Long legajoId = liquidacion.getPersona().getLegajoId();
            String cuil = liquidacion.getPersona().getCuil();
            String line = "04";
            line += cuil; // cuil
            bufferedWriter.write(line);
            bufferedWriter.write("\r\n");
        }
    }

}
