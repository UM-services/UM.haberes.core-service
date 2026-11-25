/**
 *
 */
package um.haberes.core.service.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.exception.NovedadException;
import um.haberes.core.exception.common.ImportNewsException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.model.NovedadUploadEntity;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.service.NovedadUploadService;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.util.Periodo;
import um.haberes.core.util.Tool;
import um.haberes.core.util.transfer.FileInfo;
import um.haberes.core.util.transfer.NovedadesTransferencia;
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
        List<NovedadUploadEntity> novedades = new ArrayList<>();
        File file = Tool.writeFile(fileInfo);

        // Procesa Excel
        try {
            InputStream input = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(input);
            int sheetCount = workbook.getNumberOfSheets();
            for (int counter = 0; counter < sheetCount; counter++) {
                Sheet sheet = workbook.getSheetAt(counter);
                log.debug("Sheet -> " + sheet.getSheetName());
                int rows = sheet.getLastRowNum();
                Integer cols = (int) sheet.getRow(0).getLastCellNum();
                Row row = sheet.getRow(0);
                String columnName = null;
                // Verificacion Legajo
                Integer columnLegajoId = null;
                try {
                    columnName = row.getCell(0).getStringCellValue().toLowerCase().replace(" ", "");
                    if (columnName.equals("legajoid")) {
                        columnLegajoId = 0;
                    }
                } catch (NullPointerException e) {
                    response = response + "Sheet= " + sheet.getSheetName() + " SIN LegajoId" + (char) 10;
                }
                // Verificacion CodigoEntity
                Integer columnCodigoId = null;
                try {
                    columnName = row.getCell(1).getStringCellValue().toLowerCase().replace(" ", "");
                    if (columnName.equals("codigoid")) {
                        columnCodigoId = 1;
                    }
                } catch (NullPointerException e) {
                    response = response + "Sheet= " + sheet.getSheetName() + " SIN CodigoId" + (char) 10;
                }
                // Verificacion Importe
                Integer columnImporte = null;
                try {
                    columnName = row.getCell(2).getStringCellValue().toLowerCase().replace(" ", "");
                    if (columnName.equals("importe")) {
                        columnImporte = 2;
                    }
                } catch (NullPointerException e) {
                }
                // Verificacion DependenciaEntity
                Integer columnDependenciaId = null;
                try {
                    columnName = row.getCell(3).getStringCellValue().toLowerCase().replace(" ", "");
                    if (columnName.equals("dependenciaid")) {
                        columnDependenciaId = 3;
                    }
                } catch (NullPointerException e) {
                }
                if (columnLegajoId == null || columnCodigoId == null || columnImporte == null)
                    continue;
                for (int rowNumber = 1; rowNumber <= rows; rowNumber++) {
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
                        boolean testString = true;
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
                            var novedadUpload = new NovedadUploadEntity(null, legajoId, anho, mes, codigoId, dependenciaId, importe,
                                    value, (byte) 1, null, null, null);
                            novedades.add(novedadUpload);
                        }
                    }
                }
            }
            workbook.close();
            input.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        if (stepByStep) {
            for (NovedadUploadEntity novedadUpload : novedades) {
                String detail = "";
                try {
                    log.debug("NovedadUploadEntity -> {}", detail =JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(novedadUpload));
                } catch (JsonProcessingException e) {
                    log.debug("NovedadUploadEntity -> null {}", e.getMessage());
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
        for (NovedadUploadEntity novedadUpload : novedadUploadService.findAllByPendiente(anho, mes, (byte) 1)) {
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

    /**
     * Copia al período destino las novedades transferibles del mes anterior.
     *
     * <p>El período destino se lee <b>una sola vez</b> y sus claves se resuelven en memoria:
     * antes se hacía un {@code findByUnique} por fila (N + 1 consultas sobre ~2.400 filas) y
     * la transferencia tardaba más de un minuto. Además, al quedar las entidades del destino
     * gestionadas en la misma sesión, el {@code saveAll} sólo emite los UPDATE (batcheados por
     * Hibernate) sin volver a consultar cada fila.</p>
     */
    @Transactional
    public NovedadesTransferencia transfer(Integer anho, Integer mes) {
        Periodo anterior = Periodo.prevMonth(anho, mes);
        List<Integer> codigoIds = codigoService.findAllByTransferible((byte) 1).stream()
                .map(Codigo::getCodigoId)
                .toList();
        if (codigoIds.isEmpty()) {
            return new NovedadesTransferencia(0, 0, 0, 0);
        }

        List<Novedad> destino = novedadService.findAllByPeriodo(anho, mes);
        Map<ClaveNovedad, Novedad> existentes = new HashMap<>(destino.size() * 2);
        for (Novedad novedad : destino) {
            existentes.putIfAbsent(ClaveNovedad.of(novedad), novedad);
        }
        if (existentes.size() != destino.size()) {
            log.warn("Transferencia {}/{}: el período destino tiene {} claves duplicadas",
                    mes, anho, destino.size() - existentes.size());
        }

        List<Novedad> origen = novedadService.findAllByCodigos(codigoIds, anterior.getAnho(), anterior.getMes());
        List<Novedad> novedades = new ArrayList<>(origen.size());
        Set<ClaveNovedad> procesadas = new HashSet<>(origen.size() * 2);
        int nuevas = 0;
        int actualizadas = 0;
        int sinCambios = 0;
        int repetidas = 0;
        for (Novedad old : origen) {
            ClaveNovedad clave = ClaveNovedad.of(old);
            // El único de `novedad` permite varias filas con dependencia NULL: se copia una sola
            // para no duplicar en el destino (el legacy hubiera insertado todas).
            if (!procesadas.add(clave)) {
                repetidas++;
                continue;
            }
            Novedad existente = existentes.get(clave);
            if (existente == null) {
                nuevas++;
            } else if (sinCambios(existente, old)) {
                // Reejecutar la transferencia no debe reescribir filas idénticas: sin UPDATE ni
                // evento de auditoría por fila. En MySQL un UPDATE cuesta un viaje por fila.
                sinCambios++;
                continue;
            } else {
                actualizadas++;
            }
            novedades.add(
                    new Novedad(existente == null ? null : existente.getNovedadId(), old.getLegajoId(), anho, mes,
                            old.getCodigoId(), old.getDependenciaId(), old.getImporte(), old.getValue(),
                            "Transferido", (byte) 1, null, null, null, null));
        }
        if (repetidas > 0) {
            log.warn("Transferencia {}/{}: {} claves repetidas en el período de origen omitidas", mes, anho, repetidas);
        }

        if (!novedades.isEmpty()) {
            novedadService.saveAll(novedades);
        }
        log.info("Transferencia {}/{}: {} novedades ({} nuevas, {} actualizadas, {} sin cambios)", mes, anho,
                origen.size() - repetidas, nuevas, actualizadas, sinCambios);
        return new NovedadesTransferencia(nuevas, actualizadas, sinCambios, origen.size() - repetidas);
    }

    /** ¿El destino ya tiene exactamente lo que la transferencia escribiría? */
    private static boolean sinCambios(Novedad destino, Novedad origen) {
        return "Transferido".equals(destino.getObservaciones())
                && Byte.valueOf((byte) 1).equals(destino.getImportado())
                && destino.getNovedadUploadId() == null
                && mismoImporte(destino.getImporte(), origen.getImporte())
                && texto(destino.getValue()).equals(texto(origen.getValue()));
    }

    private static boolean mismoImporte(BigDecimal destino, BigDecimal origen) {
        if (destino == null || origen == null) {
            return destino == origen;
        }
        return destino.compareTo(origen) == 0;
    }

    private static String texto(String value) {
        return value == null ? "" : value;
    }

    /** Clave única lógica de una novedad: legajo + código + dependencia (período aparte). */
    private record ClaveNovedad(Long legajoId, Integer codigoId, Integer dependenciaId) {

        static ClaveNovedad of(Novedad novedad) {
            return new ClaveNovedad(novedad.getLegajoId(), novedad.getCodigoId(), novedad.getDependenciaId());
        }
    }

}
