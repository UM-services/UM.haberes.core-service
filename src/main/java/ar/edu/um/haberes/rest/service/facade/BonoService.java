/**
 * 
 */
package ar.edu.um.haberes.rest.service.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import ar.edu.um.haberes.rest.exception.ContactoNotFoundException;
import ar.edu.um.haberes.rest.exception.LegajoBancoNotFoundException;
import ar.edu.um.haberes.rest.exception.LegajoControlNotFoundException;
import ar.edu.um.haberes.rest.exception.LetraNotFoundException;
import ar.edu.um.haberes.rest.model.Antiguedad;
import ar.edu.um.haberes.rest.model.BonoImpresion;
import ar.edu.um.haberes.rest.model.CargoClaseDetalle;
import ar.edu.um.haberes.rest.model.CargoLiquidacion;
import ar.edu.um.haberes.rest.model.Codigo;
import ar.edu.um.haberes.rest.model.CodigoGrupo;
import ar.edu.um.haberes.rest.model.Contacto;
import ar.edu.um.haberes.rest.model.Control;
import ar.edu.um.haberes.rest.model.CursoCargo;
import ar.edu.um.haberes.rest.model.Dependencia;
import ar.edu.um.haberes.rest.model.Item;
import ar.edu.um.haberes.rest.model.LegajoBanco;
import ar.edu.um.haberes.rest.model.LegajoControl;
import ar.edu.um.haberes.rest.model.Letra;
import ar.edu.um.haberes.rest.model.Liquidacion;
import ar.edu.um.haberes.rest.model.LiquidacionAdicional;
import ar.edu.um.haberes.rest.model.Persona;
import ar.edu.um.haberes.rest.service.AntiguedadService;
import ar.edu.um.haberes.rest.service.BonoImpresionService;
import ar.edu.um.haberes.rest.service.CargoClaseDetalleService;
import ar.edu.um.haberes.rest.service.CargoLiquidacionService;
import ar.edu.um.haberes.rest.service.CodigoGrupoService;
import ar.edu.um.haberes.rest.service.CodigoService;
import ar.edu.um.haberes.rest.service.ContactoService;
import ar.edu.um.haberes.rest.service.ControlService;
import ar.edu.um.haberes.rest.service.CursoCargoService;
import ar.edu.um.haberes.rest.service.DependenciaService;
import ar.edu.um.haberes.rest.service.ItemService;
import ar.edu.um.haberes.rest.service.LegajoBancoService;
import ar.edu.um.haberes.rest.service.LegajoControlService;
import ar.edu.um.haberes.rest.service.LetraService;
import ar.edu.um.haberes.rest.service.LiquidacionAdicionalService;
import ar.edu.um.haberes.rest.service.LiquidacionService;
import ar.edu.um.haberes.rest.service.PersonaService;
import ar.edu.um.haberes.rest.util.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class BonoService {

	@Autowired
	private Environment env;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private ControlService controlService;

	@Autowired
	private DependenciaService dependenciaService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private AntiguedadService antiguedadService;

	@Autowired
	private LegajoBancoService legajoBancoService;

	@Autowired
	private CursoCargoService cursoCargoService;

	@Autowired
	private CargoLiquidacionService cargoLiquidacionService;

	@Autowired
	private CargoClaseDetalleService cargoClaseDetalleService;

	@Autowired
	private CodigoGrupoService codigoGrupoService;

	@Autowired
	private LetraService letraService;

	@Autowired
	private BonoImpresionService bonoImpresionService;

	@Autowired
	private LiquidacionService liquidacionService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private LegajoControlService legajoControlService;

	@Autowired
	private LiquidacionAdicionalService liquidacionAdicionalService;

	@Autowired
	private CodigoService codigoService;

	public String generatePdfDependencia(Integer anho, Integer mes, Integer dependenciaId, String salida,
			Long legajoIdSolicitud, String ipAddress) {
		String path = env.getProperty("path.files");
		Control control = controlService.findByPeriodo(anho, mes);

		String filename = "";
		List<String> filenames = new ArrayList<>();
		for (Liquidacion liquidacion : liquidacionService.findAllByDependencia(dependenciaId, anho, mes, salida)) {
			filenames.add(filename = path + "bono." + liquidacion.getLegajoId() + "." + anho + "." + mes + ".pdf");
			log.debug("Filename -> {}", filename);
			filename = makePdf(filename, liquidacion.getLegajoId(), anho, mes, legajoIdSolicitud, ipAddress, control);
		}

		try {
			mergePdf(filename = path + "dependencia." + dependenciaId + ".pdf", filenames);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filename;
	}

	private void mergePdf(String filename, List<String> filenames) throws IOException {
		OutputStream outputStream = new FileOutputStream(new File(filename));
		Document document = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
		document.open();
		PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
		for (String name : filenames) {
			PdfReader pdfReader = new PdfReader(new FileInputStream(new File(name)));
			for (int pagina = 0; pagina < pdfReader.getNumberOfPages();) {
				document.newPage();
				PdfImportedPage page = pdfWriter.getImportedPage(pdfReader, ++pagina);
				pdfContentByte.addTemplate(page, 0, 0);
			}
		}
		outputStream.flush();
		document.close();
		outputStream.close();
	}

	public String generatePdf(Long legajoId, Integer anho, Integer mes, Long legajoIdSolicitud, String ipAddress) {
		String path = env.getProperty("path.files");
		String filename = path + "bono." + legajoId + "." + anho + "." + mes + ".pdf";
		Control control = controlService.findByPeriodo(anho, mes);

		return makePdf(filename, legajoId, anho, mes, legajoIdSolicitud, ipAddress, control);
	}

	@Transactional
	public String makePdf(String filename, Long legajoId, Integer anho, Integer mes, Long legajoIdSolicitud,
			String ipAddress, Control control) {
		Persona persona = personaService.findByLegajoId(legajoId);
		Antiguedad antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
		Integer mesesAntiguedad = antiguedad.getMesesDocentes() > antiguedad.getMesesAdministrativos()
				? antiguedad.getMesesDocentes()
				: antiguedad.getMesesAdministrativos();
		LegajoBanco legajoBanco = null;
		try {
			legajoBanco = legajoBancoService.findLegajoCbuPrincipal(legajoId, anho, mes);
		} catch (LegajoBancoNotFoundException e) {
			legajoBanco = new LegajoBanco();
		}
		log.debug("LegajoBanco -> {}", legajoBanco);
		Map<Integer, Dependencia> dependencias = dependenciaService.findAll().stream()
				.collect(Collectors.toMap(Dependencia::getDependenciaId, dependencia -> dependencia));
		// Elimina los items con importe 0 que no sean de los subtotales
		itemService.deleteAllByZero(legajoId, anho, mes);

		Map<Integer, Item> items = itemService.findAllByLegajo(legajoId, anho, mes).stream()
				.collect(Collectors.toMap(Item::getCodigoId, Function.identity(), (item, replacement) -> item));

		try {
			Document document = new Document(new Rectangle(PageSize.A4));
			PdfWriter.getInstance(document, new FileOutputStream(filename));
			document.setMargins(20, 20, 20, 20);
			document.open();

			Image marca = Image.getInstance("marca_um.png");
			Image firma = Image.getInstance("firma.png");

			// Tabla logo y datos UM
			float[] columnHeader = { 2, 8 };
			PdfPTable tableHeader = new PdfPTable(columnHeader);
			tableHeader.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell(marca);
			cell.setBorder(Rectangle.NO_BORDER);
			tableHeader.addCell(cell);

			Paragraph paragraph = new Paragraph(
					new Phrase("Universidad de Mendoza", new Font(Font.HELVETICA, 16, Font.BOLD)));
			paragraph.add(new Phrase("\nBoulogne Sur Mer 683 - 5500 - Mendoza", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\nRepública Argentina", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\nCUIT: 30-51859446-6", new Font(Font.HELVETICA, 8)));
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setLeading(0, 1.5f);
			tableHeader.addCell(cell);
			document.add(tableHeader);

			// Centrado Título
			paragraph = new Paragraph("Recibo por Pago de Remuneraciones", new Font(Font.HELVETICA, 12, Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setMultipliedLeading(2f);
			document.add(paragraph);
			// Nombre, documento y cuil
			paragraph = new Paragraph(new Phrase("Apellido, Nombre: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(MessageFormat.format("{0}, {1}", persona.getApellido(), persona.getNombre()),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("                   DU: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(persona.getDocumento().toString(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("                   CUIL: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(persona.getCuil(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setMultipliedLeading(2f);
			document.add(paragraph);
			// Legajo, dependencia y básico
			Dependencia dependencia = dependencias.get(persona.getDependenciaId());
			Item item = null;
			if (!items.containsKey(1)) {
				item = new Item();
			} else {
				item = items.get(1);
			}
			paragraph = new Paragraph(new Phrase("Legajo: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(persona.getLegajoId().toString(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("         Dependencia: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(dependencia.getAcronimo(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("         Sueldo Básico: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(item.getImporte()),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("         Ingreso: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(persona.getAltaAdministrativa().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("         Antigüedad: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(MessageFormat.format("{0}.{1}", mesesAntiguedad / 12, mesesAntiguedad % 12),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setMultipliedLeading(2f);
			document.add(paragraph);
			paragraph = new Paragraph(new Phrase("CBU: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(legajoBanco.getCbu(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("              Período Liquidado: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(control.getFechaDesde().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("-", new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(control.getFechaHasta().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("              Fecha Pago: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(control.getFechaPago().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setMultipliedLeading(2f);
			document.add(paragraph);
			paragraph = new Paragraph(new Phrase("Aporte Jub: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(control.getAporteJubilatorio(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase("              Fecha Depósito: ", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(control.getFechaDeposito().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setMultipliedLeading(2f);
			document.add(paragraph);
			// Cursos
			List<CursoCargo> cursos = cursoCargoService.findAllByLegajo(legajoId, anho, mes);
			if (cursos.size() > 0) {
				float[] columnCurso = { 4, 24, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f };
				PdfPTable tableCurso = new PdfPTable(columnCurso);
				tableCurso.setWidthPercentage(90);
				tableCurso.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(new Paragraph("Cargo", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("Curso", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("Ds", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("An", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("S1", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("S2", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableCurso.addCell(cell);
				cell = new PdfPCell(new Paragraph("Hr", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableCurso.addCell(cell);
				for (CursoCargo cursoCargo : cursos) {
					cell = new PdfPCell(new Paragraph(cursoCargo.getCargoTipo().getNombre(),
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(cursoCargo.getCurso().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(cursoCargo.getDesarraigo() == 1 ? "*" : "",
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(cursoCargo.getCurso().getAnual() == 1 ? "*" : "",
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(cursoCargo.getCurso().getSemestre1() == 1 ? "*" : "",
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(cursoCargo.getCurso().getSemestre2() == 1 ? "*" : "",
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableCurso.addCell(cell);
					cell = new PdfPCell(new Paragraph(new DecimalFormat("#0").format(cursoCargo.getHorasSemanales()),
							new Font(Font.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableCurso.addCell(cell);
				}
				document.add(tableCurso);
			}
			// Actividad Docente
			List<CargoLiquidacion> cargos = cargoLiquidacionService.findAllDocenteByLegajo(legajoId, anho, mes);
			if (cargos.size() > 0) {
				paragraph = new Paragraph("Actividad Docente", new Font(Font.HELVETICA, 8, Font.BOLD));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setMultipliedLeading(2f);
				document.add(paragraph);
				// Cargos Liquidados
				float[] columnGrupo = { 1, 1 };
				PdfPTable tableGrupo = new PdfPTable(columnGrupo);
				tableGrupo.setWidthPercentage(90);
				tableGrupo.setHorizontalAlignment(Element.ALIGN_CENTER);
				float[] columnCargo = { 2, 6, 3 };
				PdfPTable tableCargo = new PdfPTable(columnCargo);
				tableCargo.setWidthPercentage(100);
				tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(new Paragraph("Dep", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Cargo", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Básico", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(tableCargo);
				cell.setBorder(Rectangle.NO_BORDER);
				tableGrupo.addCell(cell);
				tableGrupo.addCell(cell);
				document.add(tableGrupo);
				tableGrupo = new PdfPTable(columnGrupo);
				tableGrupo.setWidthPercentage(90);
				tableGrupo.setHorizontalAlignment(Element.ALIGN_CENTER);
				Integer count = 0;
				for (CargoLiquidacion cargoLiquidacion : cargos) {
					count++;
					tableCargo = new PdfPTable(columnCargo);
					tableCargo.setWidthPercentage(100);
					tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(new Paragraph(cargoLiquidacion.getDependencia().getAcronimo(),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(cargoLiquidacion.getCategoria().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(new DecimalFormat("#,###.00").format(cargoLiquidacion.getCategoriaBasico()),
									new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(tableCargo);
					cell.setBorder(Rectangle.NO_BORDER);
					tableGrupo.addCell(cell);
				}
				// Total Adicionales
				Map<Integer, BigDecimal> adicionales = new HashMap<Integer, BigDecimal>();
				for (LiquidacionAdicional liquidacionAdicional : liquidacionAdicionalService.findAllByLegajo(legajoId,
						anho, mes)) {
					dependencia = dependenciaService.findByDependenciaId(liquidacionAdicional.getDependenciaId());
					if (!adicionales.containsKey(dependencia.getDependenciaId())) {
						adicionales.put(dependencia.getDependenciaId(), BigDecimal.ZERO);
					}
					adicionales.put(dependencia.getDependenciaId(), adicionales.get(dependencia.getDependenciaId())
							.add(liquidacionAdicional.getAdicional()).setScale(2, RoundingMode.HALF_UP));
				}
				for (Integer dependenciaId : adicionales.keySet()) {
					count++;
					dependencia = dependenciaService.findByDependenciaId(dependenciaId);
					tableCargo = new PdfPTable(columnCargo);
					tableCargo.setWidthPercentage(100);
					tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(new Paragraph(dependencia.getAcronimo(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);

					Codigo codigo = codigoService.findByCodigoId(981);
					cell = new PdfPCell(new Paragraph(codigo.getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(new DecimalFormat("#,###.00").format(adicionales.get(dependenciaId)),
									new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(tableCargo);
					cell.setBorder(Rectangle.NO_BORDER);
					tableGrupo.addCell(cell);
				}
				// Completa con una celda vacía si la cantidad de cargos es impar
				if (count % 2 != 0) {
					cell = new PdfPCell();
					cell.setBorder(Rectangle.NO_BORDER);
					tableGrupo.addCell(cell);
				}
				document.add(tableGrupo);
			}
			// Actividad No Docente
			cargos = cargoLiquidacionService.findAllNoDocenteByLegajo(legajoId, anho, mes);
			if (cargos.size() > 0) {
				paragraph = new Paragraph("Actividad No Docente", new Font(Font.HELVETICA, 8, Font.BOLD));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setMultipliedLeading(2f);
				document.add(paragraph);
				// Cargos Liquidados
				float[] columnGrupo = { 1, 1 };
				PdfPTable tableGrupo = new PdfPTable(columnGrupo);
				tableGrupo.setWidthPercentage(90);
				tableGrupo.setHorizontalAlignment(Element.ALIGN_CENTER);
				float[] columnCargo = { 2, 6, 3 };
				PdfPTable tableCargo = new PdfPTable(columnCargo);
				tableCargo.setWidthPercentage(100);
				tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(new Paragraph("Dep", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Cargo", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Básico", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(tableCargo);
				cell.setBorder(Rectangle.NO_BORDER);
				tableGrupo.addCell(cell);
				tableGrupo.addCell(cell);
				document.add(tableGrupo);
				tableGrupo = new PdfPTable(columnGrupo);
				tableGrupo.setWidthPercentage(90);
				tableGrupo.setHorizontalAlignment(Element.ALIGN_CENTER);
				Integer count = 0;
				for (CargoLiquidacion cargoLiquidacion : cargos) {
					count++;
					tableCargo = new PdfPTable(columnCargo);
					tableCargo.setWidthPercentage(100);
					tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell(new Paragraph(cargoLiquidacion.getDependencia().getAcronimo(),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(cargoLiquidacion.getCategoria().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					BigDecimal multiplicador = BigDecimal.ONE;
					if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) == 0) {
						multiplicador = new BigDecimal(cargoLiquidacion.getJornada());
					} else {
						multiplicador = cargoLiquidacion.getHorasJornada();
					}
					cell = new PdfPCell(new Paragraph(
							new DecimalFormat("#,###.00").format(cargoLiquidacion.getCategoriaBasico()
									.multiply(multiplicador).setScale(2, RoundingMode.HALF_UP)),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(tableCargo);
					cell.setBorder(Rectangle.NO_BORDER);
					tableGrupo.addCell(cell);
				}
				// Completa con una celda vacía si la cantidad de cargos es impar
				if (count % 2 != 0) {
					cell = new PdfPCell();
					cell.setBorder(Rectangle.NO_BORDER);
					tableGrupo.addCell(cell);
				}
				document.add(tableGrupo);
			}
			// Cargos con Clase
			List<CargoClaseDetalle> clases = cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes);
			if (clases.size() > 0) {
				paragraph = new Paragraph("Actividad Académica", new Font(Font.HELVETICA, 8, Font.BOLD));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setMultipliedLeading(2f);
				document.add(paragraph);
				float[] columnCargo = { 2, 14, 3 };
				PdfPTable tableCargo = new PdfPTable(columnCargo);
				tableCargo.setWidthPercentage(90);
				tableCargo.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell(new Paragraph("Dep", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Cargo", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableCargo.addCell(cell);
				cell = new PdfPCell(new Paragraph("Básico", new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableCargo.addCell(cell);
				for (CargoClaseDetalle cargoClaseDetalle : clases) {
					cell = new PdfPCell(new Paragraph(cargoClaseDetalle.getDependencia().getAcronimo(),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(new Paragraph(
							MessageFormat.format("{0} / {1} / {2} / {3} / {4} {5}",
									cargoClaseDetalle.getCargoClase().getClase().getNombre(),
									cargoClaseDetalle.getCargoClase().getNombre(),
									cargoClaseDetalle.getCargoClasePeriodo().getDescripcion(),
									cargoClaseDetalle.getCargoClasePeriodo().getGeografica().getNombre(),
									cargoClaseDetalle.getCargoClasePeriodo().getHoras().toString(), "horas"),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCargo.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(
									cargoClaseDetalle.getValorHora()
											.multiply(new BigDecimal(cargoClaseDetalle.getHoras())).toString(),
									new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCargo.addCell(cell);
				}
				document.add(tableCargo);
			}
			// Códigos Liquidados
			Integer count = 0;
			float[] columnGrupoCodigo = { 1, 1, 1 };
			PdfPTable tableGrupoCodigo = new PdfPTable(columnGrupoCodigo);
			tableGrupoCodigo.setWidthPercentage(100);
			tableGrupoCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] columnCodigo = { 2, 10, 4 };
			PdfPTable tableCodigoRemun = new PdfPTable(columnCodigo);
			tableCodigoRemun.setWidthPercentage(100);
			tableCodigoRemun.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Cód", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("Concepto", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableCodigoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("Remun", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoRemun.addCell(cell);
			PdfPTable tableCodigoNoRemun = new PdfPTable(columnCodigo);
			tableCodigoNoRemun.setWidthPercentage(100);
			tableCodigoNoRemun.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Cód", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoNoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("Concepto", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableCodigoNoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("No Rem", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoNoRemun.addCell(cell);
			PdfPTable tableCodigoDeduc = new PdfPTable(columnCodigo);
			tableCodigoDeduc.setWidthPercentage(100);
			tableCodigoDeduc.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("Cód", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoDeduc.addCell(cell);
			cell = new PdfPCell(new Paragraph("Concepto", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableCodigoDeduc.addCell(cell);
			cell = new PdfPCell(new Paragraph("Deduc", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoDeduc.addCell(cell);
			// Detalle Remunerativo
			count = 0;
			BigDecimal totalRemunerativo = BigDecimal.ZERO;
			for (CodigoGrupo codigoGrupo : codigoGrupoService.findAllByRemunerativo((byte) 1)) {
				if (items.containsKey(codigoGrupo.getCodigoId())) {
					item = items.get(codigoGrupo.getCodigoId());
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigoId().toString(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoRemun.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigo().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCodigoRemun.addCell(cell);
					cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(item.getImporte()),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoRemun.addCell(cell);
					totalRemunerativo = totalRemunerativo.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
					count++;
				}
			}
			// Si no hay códigos remun
			if (count == 0) {
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoRemun.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoRemun.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoRemun.addCell(cell);
			}
			// Detalle No Remunerativo
			count = 0;
			BigDecimal totalNoRemunerativo = BigDecimal.ZERO;
			for (CodigoGrupo codigoGrupo : codigoGrupoService.findAllByNoRemunerativo((byte) 1)) {
				if (items.containsKey(codigoGrupo.getCodigoId())) {
					item = items.get(codigoGrupo.getCodigoId());
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigoId().toString(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoNoRemun.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigo().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCodigoNoRemun.addCell(cell);
					cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(item.getImporte()),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoNoRemun.addCell(cell);
					totalNoRemunerativo = totalNoRemunerativo.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
					count++;
				}
			}
			// Si no hay códigos no remun
			if (count == 0) {
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoNoRemun.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoNoRemun.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoNoRemun.addCell(cell);
			}
			// Detalle Deducciones
			count = 0;
			BigDecimal totalDeduccion = BigDecimal.ZERO;
			for (CodigoGrupo codigoGrupo : codigoGrupoService.findAllByDeduccion((byte) 1)) {
				if (items.containsKey(codigoGrupo.getCodigoId())) {
					item = items.get(codigoGrupo.getCodigoId());
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigoId().toString(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoDeduc.addCell(cell);
					cell = new PdfPCell(
							new Paragraph(codigoGrupo.getCodigo().getNombre(), new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tableCodigoDeduc.addCell(cell);
					cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(item.getImporte()),
							new Font(Font.HELVETICA, 8)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tableCodigoDeduc.addCell(cell);
					totalDeduccion = totalDeduccion.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
					count++;
				}
			}
			// Si no hay códigos deduc
			if (count == 0) {
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoDeduc.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoDeduc.addCell(cell);
				cell = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, 8)));
				cell.setBorder(Rectangle.NO_BORDER);
				tableCodigoDeduc.addCell(cell);
			}

			cell = new PdfPCell(tableCodigoRemun);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			cell = new PdfPCell(tableCodigoNoRemun);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			cell = new PdfPCell(tableCodigoDeduc);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			document.add(tableGrupoCodigo);
			// Totales
			tableGrupoCodigo = new PdfPTable(columnGrupoCodigo);
			tableGrupoCodigo.setWidthPercentage(100);
			tableGrupoCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
			// Agregando Remunerativos
			tableCodigoRemun = new PdfPTable(columnCodigo);
			tableCodigoRemun.setWidthPercentage(100);
			tableCodigoRemun.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("Remunerativos", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(totalRemunerativo),
					new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoRemun.addCell(cell);
			cell = new PdfPCell(tableCodigoRemun);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			// Agregando No Remunerativos
			tableCodigoNoRemun = new PdfPTable(columnCodigo);
			tableCodigoNoRemun.setWidthPercentage(100);
			tableCodigoNoRemun.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoNoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph("No Remunerativos", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoNoRemun.addCell(cell);
			cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(totalNoRemunerativo),
					new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoNoRemun.addCell(cell);
			cell = new PdfPCell(tableCodigoNoRemun);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			// Agregando Deducciones
			tableCodigoDeduc = new PdfPTable(columnCodigo);
			tableCodigoDeduc.setWidthPercentage(100);
			tableCodigoDeduc.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell(new Paragraph("", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoDeduc.addCell(cell);
			cell = new PdfPCell(new Paragraph("Deducciones", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoDeduc.addCell(cell);
			cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(totalDeduccion),
					new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableCodigoDeduc.addCell(cell);
			cell = new PdfPCell(tableCodigoDeduc);
			cell.setBorder(Rectangle.NO_BORDER);
			tableGrupoCodigo.addCell(cell);
			document.add(tableGrupoCodigo);
			// Firma y Letras
			Letra letra = null;
			BigDecimal neto = BigDecimal.ZERO;
			if (items.containsKey(99)) {
				neto = items.get(99).getImporte();
			}
			try {
				letra = letraService.findByUnique(legajoId, anho, mes);
				letra = letraService.update(
						new Letra(letra.getLetraId(), legajoId, anho, mes, neto, Tool.number_2_text(neto)),
						letra.getLetraId());
			} catch (LetraNotFoundException e) {
				letra = letraService.add(new Letra(null, legajoId, anho, mes, neto, Tool.number_2_text(neto)));
			}
			PdfPTable tableFirma = new PdfPTable(columnHeader);
			tableFirma.setWidthPercentage(100);
			cell = new PdfPCell(firma);
			cell.setBorder(Rectangle.NO_BORDER);
			tableFirma.addCell(cell);
			paragraph = new Paragraph(new Phrase("Neto: ", new Font(Font.HELVETICA, 10)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(letra.getNeto()),
					new Font(Font.HELVETICA, 10, Font.BOLD)));
			paragraph.add(new Phrase("\nson pesos: ", new Font(Font.HELVETICA, 10)));
			paragraph.add(new Phrase(letra.getCadena(), new Font(Font.HELVETICA, 10, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setLeading(0, 2f);
			tableFirma.addCell(cell);
			document.add(tableFirma);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bonoImpresionService.add(new BonoImpresion(null, legajoId, anho, mes, legajoIdSolicitud,
				Tool.hourAbsoluteArgentina(), ipAddress));

		return filename;
	}

	public String sendBono(Long legajoId, Integer anho, Integer mes, Long legajoIdSolicitud, String ipAddress)
			throws MessagingException {
		// Genera PDF
		String filenameBono = this.generatePdf(legajoId, anho, mes, legajoIdSolicitud, ipAddress);
		log.info("Filename_bono -> " + filenameBono);
		if (filenameBono.equals("")) {
			return "ERROR: Sin Chequera para ENVIAR";
		}

		String data = "";

		Persona persona = personaService.findByLegajoId(legajoId);

		Contacto contacto = null;
		try {
			contacto = contactoService.findByLegajoId(legajoId);
		} catch (ContactoNotFoundException e) {
			return "ERROR: Sin correos para ENVIAR";
		}

		data = "Estimad@ " + persona.getApellidoNombre() + ": " + (char) 10;
		data = data + (char) 10;
		data = data + "Le enviamos como archivo adjunto su bono de sueldo." + (char) 10;
		data = data + (char) 10;
		data = data + "Atentamente." + (char) 10;
		data = data + (char) 10;
		data = data + "Universidad de Mendoza" + (char) 10;
		data = data + (char) 10;
		data = data + (char) 10
				+ "Por favor no responda este mail, fue generado automáticamente. Su respuesta no será leída."
				+ (char) 10;

		// Envia correo
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		List<String> addresses = new ArrayList<String>();

		if (!contacto.getMailInstitucional().equals(""))
			addresses.add(contacto.getMailInstitucional());

		try {
			helper.setTo(addresses.toArray(new String[addresses.size()]));
			helper.setText(data);
			helper.setReplyTo("no-reply@um.edu.ar");
			helper.setSubject("Envío Automático de Bono de Sueldo -> " + filenameBono);

			FileSystemResource fileBono = new FileSystemResource(filenameBono);
			helper.addAttachment(filenameBono, fileBono);

		} catch (MessagingException e) {
			e.printStackTrace();
			return "ERROR: No pudo ENVIARSE";
		}

		LegajoControl legajoControl = null;
		try {
			legajoControl = legajoControlService.findByUnique(legajoIdSolicitud, anho, mes);
			legajoControl.setBonoEnviado((byte) 1);
			legajoControl = legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
		} catch (LegajoControlNotFoundException e) {
			legajoControl = new LegajoControl(null, legajoId, anho, mes, (byte) 0, (byte) 0, (byte) 1, persona);
			legajoControl = legajoControlService.add(legajoControl);
		}
		sender.send(message);

		return "Envío de Correo Ok!!";
	}

}
