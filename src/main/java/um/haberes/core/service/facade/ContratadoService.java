package um.haberes.core.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.core.client.ContratadoPersonaClient;
import um.haberes.core.client.CursoCargoContratadoClient;
import um.haberes.core.kotlin.model.CargoTipo;
import um.haberes.core.kotlin.model.Curso;
import um.haberes.core.kotlin.model.Facultad;
import um.haberes.core.kotlin.model.Geografica;
import um.haberes.core.kotlin.model.extern.ContratadoPersonaDto;
import um.haberes.core.kotlin.model.extern.CursoCargoContratadoDto;
import um.haberes.core.service.CargoTipoService;
import um.haberes.core.service.CursoService;
import um.haberes.core.service.FacultadService;
import um.haberes.core.service.GeograficaService;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContratadoService {

    private final Environment environment;

    private final ContratadoPersonaClient contratadoPersonaClient;

    private final CursoCargoContratadoClient cursoCargoContratadoClient;

    private final FacultadService facultadService;

    private final GeograficaService geograficaService;

    private final CursoService cursoService;

    private final CargoTipoService cargoTipoService;

    @Autowired
    public ContratadoService(Environment environment, ContratadoPersonaClient contratadoPersonaClient, CursoCargoContratadoClient cursoCargoContratadoClient, FacultadService facultadService, GeograficaService geograficaService, CursoService cursoService, CargoTipoService cargoTipoService) {
        this.environment = environment;
        this.contratadoPersonaClient = contratadoPersonaClient;
        this.cursoCargoContratadoClient = cursoCargoContratadoClient;
        this.facultadService = facultadService;
        this.geograficaService = geograficaService;
        this.cursoService = cursoService;
        this.cargoTipoService = cargoTipoService;
    }

    public String generatePlanillaContratados(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "contratados" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle styleNormal = book.createCellStyle();
        Font fontNormal = book.createFont();
        fontNormal.setBold(false);
        styleNormal.setFont(fontNormal);

        CellStyle styleBold = book.createCellStyle();
        Font fontBold = book.createFont();
        fontBold.setBold(true);
        styleBold.setFont(fontBold);

        Sheet sheet = book.createSheet("Contratados");
        Row row = null;
        int fila = 0;
        row = sheet.createRow(fila);

        this.setCellString(row, 0, "CUIT", styleBold);
        this.setCellString(row, 1, "Apellido, Nombre", styleBold);
        this.setCellString(row, 2, "Periodo", styleBold);
        this.setCellString(row, 3, "#Facultad", styleBold);
        this.setCellString(row, 4, "Facultad", styleBold);
        this.setCellString(row, 5, "#Sede", styleBold);
        this.setCellString(row, 6, "Sede", styleBold);
        this.setCellString(row, 7, "#Curso", styleBold);
        this.setCellString(row, 8, "Curso", styleBold);
        this.setCellString(row, 9, "#Cargo", styleBold);
        this.setCellString(row, 10, "Cargo", styleBold);
        this.setCellString(row, 11, "Horas", styleBold);
        this.setCellString(row, 12, "Dictado", styleBold);

        Map<Integer, Facultad> facultades = facultadService.findAll().stream()
                .collect(Collectors.toMap(Facultad::getFacultadId, facultad -> facultad));

        Map<Integer, Geografica> geograficas = geograficaService.findAll().stream()
                .collect(Collectors.toMap(Geografica::getGeograficaId, geografica -> geografica));

        Map<Long, Curso> cursos = cursoService.findAll().stream()
                .collect(Collectors.toMap(Curso::getCursoId, curso -> curso));

        Map<Integer, CargoTipo> cargos = cargoTipoService.findAll().stream()
                .collect(Collectors.toMap(CargoTipo::getCargoTipoId, cargoTipo -> cargoTipo));

        for (ContratadoPersonaDto contratadoPersona : contratadoPersonaClient.findAll()) {
            for (CursoCargoContratadoDto cursoCargoContratado : cursoCargoContratadoClient.findAllByCursosContratado(contratadoPersona.getContratadoId(), anho, mes)) {
                Curso curso = cursos.get(cursoCargoContratado.getCursoId());
                Facultad facultad = facultades.get(curso.getFacultadId());
                Geografica geografica = geograficas.get(curso.getGeograficaId());
                CargoTipo cargoTipo = cargos.get(cursoCargoContratado.getCargoTipoId());
                row = sheet.createRow(++fila);
                this.setCellString(row, 0, contratadoPersona.getCuit(), styleNormal);
                this.setCellString(row, 1, contratadoPersona.getApellido() + ", " + contratadoPersona.getNombre(), styleNormal);
                this.setCellString(row, 2, mes + "/" + anho, styleNormal);
                this.setCellInteger(row, 3, curso.getFacultadId(), styleNormal);
                this.setCellString(row, 4, facultad.getNombre(), styleNormal);
                this.setCellInteger(row, 5, curso.getGeograficaId(), styleNormal);
                this.setCellString(row, 6, geografica.getNombre(), styleNormal);
                this.setCellLong(row, 7, cursoCargoContratado.getCursoId(), styleNormal);
                this.setCellString(row, 8, curso.getNombre(), styleNormal);
                this.setCellInteger(row, 9, cursoCargoContratado.getCargoTipoId(), styleNormal);
                this.setCellString(row, 10, cargoTipo.getNombre(), styleNormal);
                this.setCellBigDecimal(row, 11, cursoCargoContratado.getHorasSemanales(), styleNormal);
                String dictado = "";
                if (curso.getAnual() == 1)
                    dictado = "Anual";
                if (curso.getSemestre1() == 1)
                    dictado = "1er Semestre";
                if (curso.getSemestre2() == 1)
                    dictado = "2do Semestre";
                this.setCellString(row, 12, dictado, styleNormal);
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            log.debug("Processing ContratadoService.generatePlanillaContratados error: " + e.getMessage());
        }
        return filename;
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

}
