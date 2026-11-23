package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.upload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;

class ExcelCategoriaUploadFileReaderTest {

    private final ExcelCategoriaUploadFileReader reader = new ExcelCategoriaUploadFileReader();

    @Test
    void leeLosPuntosCuandoLaColumnaEstaPresente() throws IOException {
        UploadedFile file = planilla(
                new String[] { "categoria_id", "nombre", "basico", "puntos" },
                new Object[][] { { 992, "DIRECTOR ETEC", 0, 1738 } });

        List<Categoria> categorias = reader.readCategorias(file);

        assertEquals(1, categorias.size());
        assertEquals(0, new BigDecimal("1738").compareTo(categorias.get(0).getPuntos()));
    }

    @Test
    void dejaLosPuntosEnNullCuandoLaPlanillaNoTraeLaColumna() throws IOException {
        UploadedFile file = planilla(
                new String[] { "categoria_id", "nombre", "basico" },
                new Object[][] { { 992, "DIRECTOR ETEC", 0 } });

        List<Categoria> categorias = reader.readCategorias(file);

        assertEquals(1, categorias.size());
        assertNull(categorias.get(0).getPuntos());
    }

    private UploadedFile planilla(String[] cabeceras, Object[][] filas) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Categorias");
            Row header = sheet.createRow(0);
            for (int column = 0; column < cabeceras.length; column++) {
                header.createCell(column).setCellValue(cabeceras[column]);
            }
            for (int index = 0; index < filas.length; index++) {
                Row row = sheet.createRow(index + 1);
                for (int column = 0; column < filas[index].length; column++) {
                    Object valor = filas[index][column];
                    if (valor instanceof Number numero) {
                        row.createCell(column).setCellValue(numero.doubleValue());
                    } else {
                        row.createCell(column).setCellValue(String.valueOf(valor));
                    }
                }
            }
            workbook.write(output);
            return UploadedFile.builder()
                    .filename("categorias.xlsx")
                    .base64(Base64.getEncoder().encodeToString(output.toByteArray()))
                    .build();
        }
    }
}
