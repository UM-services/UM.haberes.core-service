package um.haberes.rest.service.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.rest.kotlin.model.Anotador;
import um.haberes.rest.service.AnotadorService;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class AnotadorPendientesService {

    private final Environment environment;
    private final AnotadorService anotadorService;

    public AnotadorPendientesService(Environment environment, AnotadorService anotadorService) {
        this.environment = environment;
        this.anotadorService = anotadorService;
    }

    public String generate(Integer facultadId, Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");
        String filename = path + "anotador." + facultadId + "." + anho + "." + mes + ".pdf";

        generateReport(filename, facultadId, anho, mes);
        return filename;
    }

    private void generateReport(String filename, Integer facultadId, Integer anho, Integer mes) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            HeaderFooter event = new HeaderFooter(anho, mes);
            writer.setPageEvent(event);
            document.open();

            // Encabezado del documento
            event.setHeader(document);

            // Agregar contenido del reporte
            for (Anotador anotador : anotadorService.findPendientesByFacultad(facultadId, anho, mes)) {
                addAnotadorDetails(document, anotador);
            }

            document.close();
        } catch (DocumentException | IOException e) {
            log.debug("Error generating report {}", e.getMessage());
        }
    }

    private void addAnotadorDetails(Document document, Anotador anotador) throws DocumentException {
        PdfPTable detailTable = new PdfPTable(1);
        detailTable.setWidthPercentage(95);

        // Encabezado de anotador
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase("\nLegajo: ", new Font(Font.HELVETICA, 11)));
        paragraph.add(new Phrase(anotador.getLegajoId() + " - " + anotador.getPersona().getDocumento() + " - " + anotador.getPersona().getApellidoNombre(), new Font(Font.HELVETICA, 11, Font.BOLD)));
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        // Detalles del anotador
        paragraph = new Paragraph();
        paragraph.add(new Phrase( "\t\t" + anotador.getFacultad().getNombre(), new Font(Font.HELVETICA, 10, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase( "\t\t\t\t" + anotador.getCreated(), new Font(Font.HELVETICA, 9)));
        paragraph.add(new Phrase( "\t\t" + anotador.getUser(), new Font(Font.HELVETICA, 10, Font.ITALIC)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase( "\t\t\t\t\t\t" + getEstado(anotador), new Font(Font.HELVETICA, 10, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("\t\t\t\t" + anotador.getAnotacion(), new Font(Font.HELVETICA, 9)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("\t\t\t\tRespuesta: ", new Font(Font.HELVETICA, 8, Font.BOLD)));
        paragraph.add(new Phrase("\n\t\t\t\t\t\t" + anotador.getRespuesta(), new Font(Font.HELVETICA, 9)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        detailTable.addCell(cell);

        document.add(detailTable);
        document.add(Chunk.NEWLINE);
    }

    private String getEstado(Anotador anotador) {
        if (anotador.getAutorizado() == 0 && anotador.getRechazado() == 0) {
            return "Pendiente";
        }
        if (anotador.getAutorizado() == 1) {
            return "Autorizado";
        }
        if (anotador.getRechazado() == 1) {
            return "Rechazado";
        }
        return "Unknown";
    }

    static class HeaderFooter extends PdfPageEventHelper {
        private final Integer anho;
        private final Integer mes;
        private PdfPTable headerTable;

        public HeaderFooter(Integer anho, Integer mes) {
            this.anho = anho;
            this.mes = mes;
        }

        public void setHeader(Document document) throws DocumentException, IOException {
            float[] columnHeader = {1, 4};
            headerTable = new PdfPTable(columnHeader);
            headerTable.setWidthPercentage(100);

            // Imagen de la Universidad
            log.debug("Imagen de la Universidad");
            Image image = Image.getInstance("marca_um.png");
            PdfPCell imageCell = new PdfPCell(image);
            imageCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(imageCell);

            // Texto del encabezado
            log.debug("Texto del encabezado");
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase("UNIVERSIDAD DE MENDOZA", new Font(Font.HELVETICA, 15, Font.BOLD)));
            paragraph.add(new Phrase("\n\nAnotador - Pendientes", new Font(Font.HELVETICA, 13, Font.BOLD)));
            paragraph.add(new Phrase("\n\nPeriodo: ", new Font(Font.HELVETICA, 11)));
            paragraph.add(new Phrase(mes + "/" + anho, new Font(Font.HELVETICA, 11, Font.BOLD)));
            PdfPCell cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            headerTable.addCell(cell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable footer = new PdfPTable(1);
            try {
                footer.setTotalWidth(523);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                footer.addCell(new Phrase(String.format("Página %d", writer.getPageNumber()), new Font(Font.HELVETICA, 8, Font.BOLD)));
                footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
            } catch (DocumentException e) {
                log.debug("Error al finalizar la página -> {}", e.getMessage());
            }
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                document.add(headerTable);
                document.add(Chunk.NEWLINE);
            } catch (DocumentException e) {
                log.debug("Error al iniciar la página -> {}", e.getMessage());
            }
        }
    }

}
