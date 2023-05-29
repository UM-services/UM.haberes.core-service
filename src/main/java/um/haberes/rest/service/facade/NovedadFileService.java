/**
 * 
 */
package um.haberes.rest.service.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.NovedadException;
import um.haberes.rest.exception.common.ImportNewsException;
import um.haberes.rest.exception.common.TituloNotFoundException;
import um.haberes.rest.model.Codigo;
import um.haberes.rest.model.Novedad;
import um.haberes.rest.model.NovedadUpload;
import um.haberes.rest.service.CodigoService;
import um.haberes.rest.service.NovedadService;
import um.haberes.rest.service.NovedadUploadService;
import um.haberes.rest.util.Periodo;
import um.haberes.rest.util.Tool;
import um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class NovedadFileService {

	@Autowired
	private NovedadService novedadService;

	@Autowired
	private NovedadUploadService novedadUploadService;

	@Autowired
	private CodigoService codigoService;

	public void upload(FileInfo fileInfo, Integer anho, Integer mes) throws TituloNotFoundException {
		// Elimina todas las pendientes anteriores
		novedadUploadService.deleteAllByPendiente((byte) 1);

		List<NovedadUpload> novedades = new ArrayList<NovedadUpload>();
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
				Integer columnLegajoId = null;
				Integer columnCodigoId = null;
				Integer columnImporte = null;
				Integer columnDependenciaId = null;
				for (Integer column = 0; column < cols; column++) {
					String columnName = null;
					try {
						columnName = row.getCell(column).getStringCellValue();
					} catch (NullPointerException e) {
						workbook.close();
						throw new TituloNotFoundException(MessageFormat.format("Planilla {0} - Columna {1} SIN Título",
								sheet.getSheetName(), column + 1));
					}
					columnName = columnName.toLowerCase();
					columnName = columnName.replaceAll(" ", "");
					if (columnName.equals("legajoid"))
						columnLegajoId = column;
					if (columnName.equals("codigoid"))
						columnCodigoId = column;
					if (columnName.equals("importe"))
						columnImporte = column;
					if (columnName.equals("dependenciaid"))
						columnDependenciaId = column;
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
						novedades.add(new NovedadUpload(null, legajoId, anho, mes, codigoId, dependenciaId, importe,
								value, (byte) 1, null, null, null));
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
		log.debug("Grabando Upload Novedades");
//		novedadUploadService.saveAll(novedades);
		for (NovedadUpload novedadUpload : novedades) {
			log.debug("Novedad Upload -> {}", novedadUpload);
			novedadUpload = novedadUploadService.add(novedadUpload);
		}
		log.debug("Después Upload Novedades");
	}

	@Transactional
	public void importNews(Integer anho, Integer mes) {
		List<Novedad> novedades = new ArrayList<Novedad>();
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
