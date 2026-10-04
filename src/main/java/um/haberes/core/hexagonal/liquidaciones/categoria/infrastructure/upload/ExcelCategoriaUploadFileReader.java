package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaUploadFileReader;
import um.haberes.core.util.Tool;
import um.haberes.core.util.transfer.FileInfo;

@Component
@Slf4j
public class ExcelCategoriaUploadFileReader implements CategoriaUploadFileReader {

    private static final String COL_CATEGORIA_ID = "categoria_id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_BASICO = "basico";
    private static final String COL_DOCENTE = "docente";
    private static final String COL_NO_DOCENTE = "no_docente";
    private static final String COL_LIQUIDA_POR_HORA = "liquida_por_hora";
    private static final String COL_ESTADO_DOCENTE = "estado_docente";

    @Override
    public List<Categoria> readCategorias(UploadedFile file) {
        File localFile = Tool.writeFile(new FileInfo(file.getFilename(), file.getBase64()));
        List<Categoria> categorias = new ArrayList<>();
        try {
            InputStream input = new FileInputStream(localFile);
            Workbook workbook = new XSSFWorkbook(input);
            try {
                Sheet sheet = workbook.getSheetAt(0);
                int rows = sheet.getLastRowNum();
                int cols = (int) sheet.getRow(0).getLastCellNum();
                Row header = sheet.getRow(0);
                Integer columnCategoriaId = null;
                Integer columnNombre = null;
                Integer columnBasico = null;
                Integer columnDocente = null;
                Integer columnNoDocente = null;
                Integer columnLiquidaPorHora = null;
                Integer columnEstadoDocente = null;
                for (int column = 0; column < cols; column++) {
                    String columnName;
                    try {
                        columnName = header.getCell(column).getStringCellValue();
                    } catch (NullPointerException e) {
                        throw new CategoriaException(MessageFormat.format("Planilla {0} - Columna {1} SIN Título",
                                sheet.getSheetName(), column + 1));
                    }
                    columnName = columnName.toLowerCase().replaceAll(" ", "");
                    if (columnName.equals(COL_CATEGORIA_ID)) {
                        columnCategoriaId = column;
                    }
                    if (columnName.equals(COL_NOMBRE)) {
                        columnNombre = column;
                    }
                    if (columnName.equals(COL_BASICO)) {
                        columnBasico = column;
                    }
                    if (columnName.equals(COL_DOCENTE)) {
                        columnDocente = column;
                    }
                    if (columnName.equals(COL_NO_DOCENTE)) {
                        columnNoDocente = column;
                    }
                    if (columnName.equals(COL_LIQUIDA_POR_HORA)) {
                        columnLiquidaPorHora = column;
                    }
                    if (columnName.equals(COL_ESTADO_DOCENTE)) {
                        columnEstadoDocente = column;
                    }
                }
                for (int rowNumber = 1; rowNumber <= rows; rowNumber++) {
                    if (columnCategoriaId == null) {
                        break;
                    }
                    Row row = sheet.getRow(rowNumber);
                    Double cellCategoriaId = null;
                    if (row != null && row.getCell(columnCategoriaId) != null) {
                        cellCategoriaId = row.getCell(columnCategoriaId).getNumericCellValue();
                    }
                    if (cellCategoriaId == null || cellCategoriaId <= 0) {
                        continue;
                    }
                    Categoria.CategoriaBuilder categoria = Categoria.builder()
                            .categoriaId(cellCategoriaId.intValue());
                    if (columnNombre != null && row.getCell(columnNombre) != null) {
                        categoria.nombre(row.getCell(columnNombre).getStringCellValue());
                    }
                    if (columnBasico != null && row.getCell(columnBasico) != null) {
                        categoria.basico(BigDecimal.valueOf(row.getCell(columnBasico).getNumericCellValue()));
                    }
                    if (columnDocente != null && row.getCell(columnDocente) != null) {
                        categoria.docente(Double.valueOf(row.getCell(columnDocente).getNumericCellValue()).byteValue());
                    }
                    if (columnNoDocente != null && row.getCell(columnNoDocente) != null) {
                        categoria.noDocente(Double.valueOf(row.getCell(columnNoDocente).getNumericCellValue()).byteValue());
                    }
                    if (columnLiquidaPorHora != null && row.getCell(columnLiquidaPorHora) != null) {
                        categoria.liquidaPorHora(
                                Double.valueOf(row.getCell(columnLiquidaPorHora).getNumericCellValue()).byteValue());
                    }
                    if (columnEstadoDocente != null && row.getCell(columnEstadoDocente) != null) {
                        categoria.estadoDocente(
                                BigDecimal.valueOf(row.getCell(columnEstadoDocente).getNumericCellValue()));
                    }
                    categorias.add(categoria.build());
                }
            } finally {
                workbook.close();
                input.close();
            }
            localFile.delete();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return categorias;
    }
}
