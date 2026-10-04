package um.haberes.core.hexagonal.personas.persona.infrastructure.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.personas.persona.domain.model.ContactoMail;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.ContactoUploadFileReader;
import um.haberes.core.util.Tool;
import um.haberes.core.util.transfer.FileInfo;

@Component
@Slf4j
public class ExcelContactoUploadFileReader implements ContactoUploadFileReader {

    private static final String COL_LEGAJO_ID = "legajo_id";
    private static final String COL_MAIL_PERSONAL = "mail_personal";
    private static final String COL_MAIL_INSTITUCIONAL = "mail_institucional";

    @Override
    public List<ContactoMail> readContactos(UploadedFile file) {
        File localFile = Tool.writeFile(new FileInfo(file.getFilename(), file.getBase64()));
        List<ContactoMail> filas = new ArrayList<>();
        try {
            InputStream input = new FileInputStream(localFile);
            Workbook workbook = new XSSFWorkbook(input);
            try {
                Sheet sheet = workbook.getSheetAt(0);
                Row header = sheet.getRow(0);
                int cols = header.getPhysicalNumberOfCells();
                int rows = sheet.getLastRowNum() + 1;
                log.debug("rows -> " + rows);
                Integer colLegajoId = null;
                Integer colMailPersonal = null;
                Integer colMailInstitucional = null;
                for (int col = 0; col < cols; col++) {
                    String headerValue = header.getCell(col).getStringCellValue();
                    if (COL_LEGAJO_ID.equals(headerValue)) {
                        colLegajoId = col;
                    }
                    if (COL_MAIL_PERSONAL.equals(headerValue)) {
                        colMailPersonal = col;
                    }
                    if (COL_MAIL_INSTITUCIONAL.equals(headerValue)) {
                        colMailInstitucional = col;
                    }
                }
                if (colLegajoId != null) {
                    for (int rowNumber = 1; rowNumber < rows; rowNumber++) {
                        Row row = sheet.getRow(rowNumber);
                        Cell legajoCell = row.getCell(colLegajoId);
                        if (legajoCell == null) {
                            continue;
                        }
                        Long legajoId = Double.valueOf(legajoCell.getNumericCellValue()).longValue();
                        ContactoMail.ContactoMailBuilder fila = ContactoMail.builder().legajoId(legajoId);
                        if (colMailPersonal != null && row.getCell(colMailPersonal) != null) {
                            fila.mailPersonal(row.getCell(colMailPersonal).getStringCellValue());
                        }
                        if (colMailInstitucional != null && row.getCell(colMailInstitucional) != null) {
                            fila.mailInstitucional(row.getCell(colMailInstitucional).getStringCellValue());
                        }
                        filas.add(fila.build());
                    }
                }
            } finally {
                workbook.close();
                input.close();
            }
            localFile.delete();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return filas;
    }
}
