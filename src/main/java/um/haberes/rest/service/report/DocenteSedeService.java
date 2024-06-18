package um.haberes.rest.service.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.rest.kotlin.model.Curso;
import um.haberes.rest.kotlin.model.CursoCargo;
import um.haberes.rest.kotlin.model.Facultad;
import um.haberes.rest.kotlin.model.Geografica;
import um.haberes.rest.service.CursoCargoService;
import um.haberes.rest.service.CursoService;
import um.haberes.rest.service.FacultadService;
import um.haberes.rest.service.GeograficaService;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class DocenteSedeService {

    private final Environment environment;
    private final CursoService cursoService;
    private final CursoCargoService cursoCargoService;
    private final FacultadService facultadService;
    private final GeograficaService geograficaService;

    private Facultad facultad;
    private Geografica geografica;

    public DocenteSedeService(Environment environment, CursoService cursoService, CursoCargoService cursoCargoService, FacultadService facultadService, GeograficaService geograficaService) {
        this.environment = environment;
        this.cursoService = cursoService;
        this.cursoCargoService = cursoCargoService;
        this.facultadService = facultadService;
        this.geograficaService = geograficaService;
    }

    public String generate(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");
        String filename = path + "docentes." + facultadId + "." + geograficaId + "." + anho + "." + mes + "." + facultadId + ".pdf";
        facultad = facultadService.findByFacultadId(facultadId);
        try {
            log.debug("Facultad -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facultad));
        } catch (JsonProcessingException e) {
            log.debug("facultad -> null");
        }
        geografica = geograficaService.findByGeograficaId(geograficaId);
        try {
            log.debug("Geografica -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(geografica));
        } catch (JsonProcessingException e) {
            log.debug("geografica -> null");
        }

        generateReport(filename, facultadId, geograficaId, anho, mes);
        return filename;
    }

    private void generateReport(String filename, Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            HeaderFooter event = new HeaderFooter(facultad, geografica, anho, mes);
            writer.setPageEvent(event);
            document.open();

            // Encabezado del documento
            event.setHeader(document);

            // Agregar contenido del reporte
            for (Curso curso : cursoService.findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(facultadId, geograficaId, anho, mes, cursoCargoService)) {
                // Encabezado del curso
                event.setCursoHeader(document, curso);

                // Detalles del curso
                addCursoDetails(document, curso, anho, mes);
            }

            document.close();
        } catch (DocumentException | IOException e) {
            log.debug("Error generating report {}", e.getMessage());
        }
    }

    private void addCursoDetails(Document document, Curso curso, Integer anho, Integer mes) throws DocumentException {
        PdfPTable detailTable = new PdfPTable(5);
        detailTable.setWidthPercentage(95);
        detailTable.setWidths(new int[]{25, 110, 35, 20, 10});

        // Cabeceras de columnas
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase("Cargo", new Font(Font.HELVETICA, 8, Font.BOLD)));
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("Docente", new Font(Font.HELVETICA, 8, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("Designación", new Font(Font.HELVETICA, 8, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("Horas", new Font(Font.HELVETICA, 8, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("Des", new Font(Font.HELVETICA, 8, Font.BOLD)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        // Datos de los detalles
        for (CursoCargo cursoCargo : cursoCargoService.findAllByCurso(curso.getCursoId(), anho, mes)) {
            paragraph = new Paragraph();
            paragraph.add(new Phrase(cursoCargo.getCargoTipo().getNombre(), new Font(Font.HELVETICA, 8)));
            cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            detailTable.addCell(cell);

            paragraph = new Paragraph();
            paragraph.add(new Phrase(cursoCargo.getPersona().getApellidoNombre(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            detailTable.addCell(cell);

            paragraph = new Paragraph();
            paragraph.add(new Phrase(cursoCargo.getDesignacionTipo().getNombre(), new Font(Font.HELVETICA, 8)));
            cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            detailTable.addCell(cell);

            paragraph = new Paragraph();
            paragraph.add(new Phrase(cursoCargo.getHorasSemanales().toString(), new Font(Font.HELVETICA, 8)));
            cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            detailTable.addCell(cell);

            String desarraigo = "";
            if (cursoCargo.getDesarraigo() == 1) {
                desarraigo = "*";
            }

            paragraph = new Paragraph();
            paragraph.add(new Phrase(desarraigo, new Font(Font.HELVETICA, 8, Font.BOLD)));
            cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            detailTable.addCell(cell);
        }

        // Agrego una línea en blanco para separar los cursos
        paragraph = new Paragraph();
        paragraph.add(new Phrase("", new Font(Font.HELVETICA, 8)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("", new Font(Font.HELVETICA, 8)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("", new Font(Font.HELVETICA, 8)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("", new Font(Font.HELVETICA, 8)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("", new Font(Font.HELVETICA, 8)));
        cell = new PdfPCell(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        document.add(detailTable);
        document.add(Chunk.NEWLINE);
    }

    static class HeaderFooter extends PdfPageEventHelper {
        private final Facultad facultad;
        private final Geografica geografica;
        private final Integer anho;
        private final Integer mes;
        private PdfPTable headerTable;

        public HeaderFooter(Facultad facultad, Geografica geografica, Integer anho, Integer mes) {
            this.facultad = facultad;
            this.geografica = geografica;
            this.anho = anho;
            this.mes = mes;
        }

        public void setHeader(Document document) throws DocumentException, IOException {
            float[] columnHeader = {2, 8};
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
            paragraph.add(new Phrase("Universidad de Mendoza", new Font(Font.HELVETICA, 15, Font.BOLD)));
            paragraph.add(new Phrase("\n" + facultad.getNombre(), new Font(Font.HELVETICA, 13, Font.BOLD)));
            paragraph.add(new Phrase("\n\nDocentes por Sede", new Font(Font.HELVETICA, 14, Font.BOLD)));
            paragraph.add(new Phrase("\n\nPeriodo: ", new Font(Font.HELVETICA, 11)));
            paragraph.add(new Phrase(mes + "/" + anho, new Font(Font.HELVETICA, 11, Font.BOLD)));
            paragraph.add(new Phrase("\nSede: ", new Font(Font.HELVETICA, 11)));
            paragraph.add(new Phrase(geografica.getNombre(), new Font(Font.HELVETICA, 11, Font.BOLD)));

            PdfPCell cell = new PdfPCell(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            headerTable.addCell(cell);
            log.debug("document -> {}", document);
            document.add(headerTable);
            document.add(Chunk.NEWLINE);
        }

        public void setCursoHeader(Document document, Curso curso) throws DocumentException {
            float[] columnHeader = {8.5f, 1.5f};
            PdfPTable cursoTable = new PdfPTable(columnHeader);
            cursoTable.setWidthPercentage(100);

            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase("Curso: ", new Font(Font.HELVETICA, 9)));
            paragraph.add(new Phrase(curso.getNombre(), new Font(Font.HELVETICA, 9, Font.BOLD)));
            PdfPCell cursoHeaderCell = new PdfPCell(paragraph);
            cursoHeaderCell.setBorder(Rectangle.NO_BORDER);
            cursoTable.addCell(cursoHeaderCell);

            String periodo = "";
            if (curso.getAnual() == 1) {
                periodo = "Anual";
            }
            if (curso.getSemestre1() == 1) {
                periodo = "1er Semestre";
            }
            if (curso.getSemestre2() == 1) {
                periodo = "2do Semestre";
            }
            paragraph = new Paragraph();
            paragraph.add(new Phrase(periodo, new Font(Font.HELVETICA, 9)));
            cursoHeaderCell = new PdfPCell(paragraph);
            cursoHeaderCell.setBorder(Rectangle.NO_BORDER);
            cursoHeaderCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cursoTable.addCell(cursoHeaderCell);

            document.add(cursoTable);
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
