/**
 *
 */
package um.haberes.core.service.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.NovedadException;
import um.haberes.core.exception.common.ImportNewsException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.kotlin.model.Codigo;
import um.haberes.core.kotlin.model.Novedad;
import um.haberes.core.kotlin.model.NovedadUpload;
import um.haberes.core.kotlin.model.Persona;
import um.haberes.core.service.CodigoService;
import um.haberes.core.service.NovedadService;
import um.haberes.core.service.NovedadUploadService;
import um.haberes.core.service.PersonaService;
import um.haberes.core.util.Periodo;
import um.haberes.core.util.Tool;
import um.haberes.core.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class NovedadFileService {

    private final NovedadService novedadService;
    private final NovedadUploadService novedadUploadService;
    private final CodigoService codigoService;
    private final PersonaService personaService;

    public NovedadFileService(NovedadService novedadService, NovedadUploadService novedadUploadService, CodigoService codigoService, PersonaService personaService) {
        this.novedadService = novedadService;
        this.novedadUploadService = novedadUploadService;
        this.codigoService = codigoService;
        this.personaService = personaService;
    }

    public String upload(FileInfo fileInfo, Integer anho, Integer mes, Boolean stepByStep) throws TituloNotFoundException {
        String response = "";
        // Elimina todas las pendientes anteriores
        novedadUploadService.deleteAllByPendiente((byte) 1);

        Map<Long, Persona> legajoIds = personaService.findAll().stream().collect(Collectors.toMap(Persona::getLegajoId, persona -> persona));
        List<NovedadUpload> novedades = new ArrayList<>();
        File file = Tool.writeFile(fileInfo);

        // Procesa Excel
        try {
            InputStream input = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(input);
            Integer sheetCount = workbook.getNumberOfSheets();
            for (Integer counter = 0; counter < sheetCount; counter++) {
                Sheet sheet = workbook.getSheetAt(counter);
                log.debug("Sheet -> " + sheet.getSheetName());
                Integer rows = sheet.getLastRowNum();
                Integer cols = (int) sheet.getRow(0).getLastCellNum();
                Row row = sheet.getRow(0);
                String columnName = null;
                // Verificacion Legajo
                Integer columnLegajoId = null;
                try {
                    columnName = row.getCell(0).getStringCellValue().toLowerCase().replaceAll(" ", "");
                    if (columnName.equals("legajoid")) {
                        columnLegajoId = 0;
                    }
                } catch (NullPointerException e) {
                    response = response + "Sheet= " + sheet.getSheetName() + " SIN LegajoId" + (char) 10;
                }
                // Verificacion Codigo
                Integer columnCodigoId = null;
                try {
                    columnName = row.getCell(1).getStringCellValue().toLowerCase().replaceAll(" ", "");
                    if (columnName.equals("codigoid")) {
                        columnCodigoId = 1;
                    }
                } catch (NullPointerException e) {
                    response = response + "Sheet= " + sheet.getSheetName() + " SIN CodigoId" + (char) 10;
                }
                // Verificacion Importe
                Integer columnImporte = null;
                try {
                    columnName = row.getCell(2).getStringCellValue().toLowerCase().replaceAll(" ", "");
                    if (columnName.equals("importe")) {
                        columnImporte = 2;
                    }
                } catch (NullPointerException e) {
                }
                // Verificacion Dependencia
                Integer columnDependenciaId = null;
                try {
                    columnName = row.getCell(3).getStringCellValue().toLowerCase().replaceAll(" ", "");
                    if (columnName.equals("dependenciaid")) {
                        columnDependenciaId = 3;
                    }
                } catch (NullPointerException e) {
                }
                if (columnLegajoId == null || columnCodigoId == null || columnImporte == null)
                    continue;
                for (Integer rowNumber = 1; rowNumber <= rows; rowNumber++) {
                    Double cellLegajoId = null;
                    Double cellCodigoId = null;
                    Double cellImporte = null;
                    String cellValue = null;
                    Double cellDependenciaId = null;
                    row = sheet.getRow(rowNumber);
                    if (columnLegajoId != null) {
                        try {
                            cellLegajoId = row.getCell(columnLegajoId).getNumericCellValue();
                            log.debug("Sheet -> " + sheet.getSheetName() + " - LegajoId -> {}", cellLegajoId);
                            if (cellLegajoId == 0)
                                continue;
                        } catch (NullPointerException e) {
                            continue;
                        }
                    }
                    if (columnCodigoId != null)
                        cellCodigoId = row.getCell(columnCodigoId).getNumericCellValue();
                    if (columnImporte != null) {
                        Boolean testString = true;
                        try {
                            cellImporte = row.getCell(columnImporte).getNumericCellValue();
                            log.debug("Sheet -> " + sheet.getSheetName() + " - Importe -> {}", cellImporte);
                            testString = false;
                        } catch (IllegalStateException e) {
                        }
                        if (testString) {
                            try {
                                cellValue = row.getCell(columnImporte).getStringCellValue();
                                log.debug("Sheet -> " + sheet.getSheetName() + " - Value -> {}", cellValue);
                            } catch (IllegalStateException e) {
                                cellImporte = Double.valueOf(row.getCell(columnImporte).getStringCellValue());
                            }
                        }
                    }
                    if (columnDependenciaId != null)
                        cellDependenciaId = row.getCell(columnDependenciaId).getNumericCellValue();
                    if (cellLegajoId > 0 && cellCodigoId > 0) {
                        Long legajoId = cellLegajoId.longValue();
                        if (!legajoIds.containsKey(legajoId)) {
                            response = response + "Legajo= " + legajoId + " inexistente / Sheet= " + sheet.getSheetName() + (char) 10;
                        } else {
                            Integer codigoId = cellCodigoId.intValue();
                            BigDecimal importe = BigDecimal.ZERO;
                            if (cellImporte != null) {
                                importe = new BigDecimal(cellImporte).setScale(2, RoundingMode.HALF_UP);
                            }
                            String value = "";
                            if (cellValue != null) {
                                value = cellValue;
                            }
                            Integer dependenciaId = null;
                            if (cellDependenciaId != null)
                                dependenciaId = cellDependenciaId.intValue();
                            var novedadUpload = new NovedadUpload(null, legajoId, anho, mes, codigoId, dependenciaId, importe,
                                    value, (byte) 1, null, null, null);
                            novedades.add(novedadUpload);
                        }
                    }
                }
            }
            workbook.close();
            input.close();
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        if (stepByStep) {
            for (NovedadUpload novedadUpload : novedades) {
                String detail = "";
                try {
                    log.debug("NovedadUpload -> {}", detail =JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(novedadUpload));
                } catch (JsonProcessingException e) {
                    log.debug("NovedadUpload -> null {}", e.getMessage());
                }
                try {
                novedadUploadService.add(novedadUpload);
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    return "Error uploading -> " + detail + " " +e.getMessage();
                }
            }
        } else {
            try {
                novedades = novedadUploadService.saveAll(novedades);
            } catch (Exception e) {
                log.debug(e.getMessage());
                return "Error uploading -> " + e.getMessage();
            }
        }
        return response;
    }

    @Transactional
    public void importNews(Integer anho, Integer mes) {
        List<Novedad> novedades = new ArrayList<>();
        for (NovedadUpload novedadUpload : novedadUploadService.findAllByPendiente(anho, mes, (byte) 1)) {
            Long novedadId = null;
            try {
                Novedad novedad = novedadService.findByUnique(novedadUpload.getLegajoId(), anho, mes,
                        novedadUpload.getCodigoId(), novedadUpload.getDependenciaId());
                novedadId = novedad.getNovedadId();
            } catch (IncorrectResultSizeDataAccessException e) {
                throw new ImportNewsException(novedadUpload.getLegajoId(), anho, mes, novedadUpload.getCodigoId(),
                        novedadUpload.getDependenciaId());
            } catch (NovedadException e) {
                novedadId = null;
            }
            novedades.add(new Novedad(novedadId, novedadUpload.getLegajoId(), anho, mes, novedadUpload.getCodigoId(),
                    novedadUpload.getDependenciaId(), novedadUpload.getImporte(), novedadUpload.getValue(),
                    "Importado Excel", (byte) 1, novedadUpload.getNovedadUploadId(), null, null, null));
        }
        novedadService.saveAll(novedades);
    }

    @Transactional
    public void transfer(Integer anho, Integer mes) {
        List<Novedad> novedades = new ArrayList<Novedad>();
        Periodo anterior = Periodo.prevMonth(anho, mes);
        Integer anhoAnterior = anterior.getAnho();
        Integer mesAnterior = anterior.getMes();
        for (Codigo codigo : codigoService.findAllByTransferible((byte) 1)) {
            for (Novedad old : novedadService.findAllByCodigo(codigo.getCodigoId(), anhoAnterior, mesAnterior)) {
                Long novedadId = null;
                try {
                    Novedad novedad = novedadService.findByUnique(old.getLegajoId(), anho, mes, old.getCodigoId(),
                            old.getDependenciaId());
                    novedadId = novedad.getNovedadId();
                } catch (NovedadException e) {
                    novedadId = null;
                }
                novedades.add(
                        new Novedad(novedadId, old.getLegajoId(), anho, mes, old.getCodigoId(), old.getDependenciaId(),
                                old.getImporte(), old.getValue(), "Transferido", (byte) 1, null, null, null, null));
            }
        }
        novedadService.saveAll(novedades);
    }

}
