/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.ContactoNotFoundException;
import ar.edu.um.haberes.rest.exception.PersonaNotFoundException;
import ar.edu.um.haberes.rest.model.Contacto;
import ar.edu.um.haberes.rest.model.Persona;
import ar.edu.um.haberes.rest.model.view.PersonaSearch;
import ar.edu.um.haberes.rest.repository.IPersonaRepository;
import ar.edu.um.haberes.rest.service.facade.ToolService;
import ar.edu.um.haberes.rest.service.view.PersonaSearchService;
import ar.edu.um.haberes.rest.util.Tool;
import ar.edu.um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class PersonaService {

	@Autowired
	private IPersonaRepository repository;

	@Autowired
	private PersonaSearchService personaSearchService;

	@Autowired
	private LiquidacionService liquidacionService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private CargoLiquidacionService cargoLiquidacionService;

	@Autowired
	private CursoCargoService cursoCargoService;

	@Autowired
	private ContactoService contactoService;

	public List<Persona> findAll() {
		return repository.findAll();
	}

	public List<Persona> findAllDocente(Integer anho, Integer mes) {
		List<Long> legajos = cursoCargoService.findAllByAnhoAndMes(anho, mes).stream().map(curso -> curso.getLegajoId())
				.collect(Collectors.toList());
		return repository.findAllByLegajoIdIn(legajos,
				Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
	}

	public List<Persona> findAllNoDocente(Integer anho, Integer mes) {
		// Categorias No Docentes
		List<Integer> categoriaIds = categoriaService.findAllNoDocentes().stream()
				.map(categoria -> categoria.getCategoriaId()).collect(Collectors.toList());
		// Legajos Liquidados en el período
		List<Long> ids = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
				.map(liquidacion -> liquidacion.getLegajoId()).collect(Collectors.toList());
		// Legajos liquidados y acreditados con categorias no docentes
		List<Long> legajoIds = cargoLiquidacionService
				.findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(ids, categoriaIds, anho, mes).stream()
				.map(cargo -> cargo.getLegajoId()).collect(Collectors.toList());
		// Personas con los legajos filtrados
		return repository.findAllByLegajoIdIn(legajoIds, Sort.by("legajoId").ascending());
	}

	public List<Persona> findAllBySemestre(Integer anho, Integer semestre) {
		List<Long> legajos = liquidacionService.findAllByAnhoAndMesBetween(anho, (semestre - 1) * 6 + 1, semestre * 6)
				.stream().map(liquidacion -> liquidacion.getLegajoId()).collect(Collectors.toList());
		return repository.findAllByLegajoIdIn(legajos, Sort.by("legajoId").ascending());
	}

	public List<PersonaSearch> findByStrings(List<String> conditions) {
		return personaSearchService.findAllByStrings(conditions);
	}

	public List<Persona> findAllLegajos(List<Long> legajos) {
		return repository.findAllByLegajoIdIn(legajos,
				Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
	}

	public List<Persona> findAllByDesarraigo(Integer anho, Integer mes) {
		List<Long> legajos = cursoCargoService.findAllByAnhoAndMesAndDesarraigo(anho, mes, (byte) 1).stream()
				.map(curso -> curso.getLegajoId()).collect(Collectors.toList());
		return repository.findAllByLegajoIdIn(legajos,
				Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
	}

	public List<Persona> findAllByLiquidado(Integer anho, Integer mes) {
		List<Long> legajos = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
				.map(liquidacion -> liquidacion.getLegajoId()).collect(Collectors.toList());
		return repository.findAllByLegajoIdIn(legajos,
				Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
	}

	public List<Persona> findAllByFiltro(String filtro) {
		return repository.findAllByApellidoLike("%" + filtro + "%");
	}

	public List<Persona> findAllLiquidables() {
		return repository.findAllByLiquida("S", Sort.by("apellido").and(Sort.by("nombre")));
	}

	public Persona findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new PersonaNotFoundException(legajoId));
	}

	public Persona findByDocumento(BigDecimal documento) {
		return repository.findByDocumento(documento).orElseThrow(() -> new PersonaNotFoundException(documento));
	}

	public Persona add(Persona persona) {
		repository.save(persona);
		log.debug(persona.toString());
		return persona;
	}

	public Persona update(Persona newPersona, Long legajoId) {
		return repository.findByLegajoId(legajoId).map(persona -> {
			persona = new Persona(legajoId, newPersona.getDocumento(), newPersona.getApellido(), newPersona.getNombre(),
					newPersona.getNacimiento(), newPersona.getAltaDocente(), newPersona.getAjusteDocente(),
					newPersona.getAltaAdministrativa(), newPersona.getAjusteAdministrativo(),
					newPersona.getEstadoCivil(), newPersona.getSituacionId(), newPersona.getReemplazoDesarraigo(),
					newPersona.getMitadDesarraigo(), newPersona.getCuil(), newPersona.getPosgrado(),
					newPersona.getEstado(), newPersona.getLiquida(), newPersona.getEstadoAfip(),
					newPersona.getDependenciaId(), newPersona.getSalida(), newPersona.getObraSocial(),
					newPersona.getActividadAfip(), newPersona.getLocalidadAfip(), newPersona.getSituacionAfip(),
					newPersona.getModeloContratacionAfip(), newPersona.getDependencia());
			repository.save(persona);
			log.debug(persona.toString());
			return persona;
		}).orElseThrow(() -> new PersonaNotFoundException(legajoId));
	}

	public List<Persona> saveall(List<Persona> personas) {
		repository.saveAll(personas);
		return personas;
	}

	public List<Persona> upload(FileInfo fileinfo) {
		File file = Tool.writeFile(fileinfo);
		InputStream input;
		List<Long> legajos = new ArrayList<Long>();
		try {
			input = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(input);
			Sheet sheet = workbook.getSheetAt(0);
			Integer cols = sheet.getRow(0).getPhysicalNumberOfCells();
			Integer rows = sheet.getLastRowNum() + 1;
			log.debug("rows -> " + rows);
			Row row = sheet.getRow(0);
			Integer col_legajo_id = null;
			Integer col_mail_personal = null;
			Integer col_mail_institucional = null;
			for (Integer col = 0; col < cols; col++) {
				if (row.getCell(col).getStringCellValue().equals("legajo_id"))
					col_legajo_id = col;
				if (row.getCell(col).getStringCellValue().equals("mail_personal"))
					col_mail_personal = col;
				if (row.getCell(col).getStringCellValue().equals("mail_institucional"))
					col_mail_institucional = col;
			}
			List<Contacto> contactos = new ArrayList<Contacto>();
			for (Integer row_number = 1; row_number < rows; row_number++) {
				row = sheet.getRow(row_number);
				if (col_legajo_id != null) {
					if (row.getCell(col_legajo_id) != null) {
						Boolean news = false;
						Long legajoId = Double.valueOf(row.getCell(col_legajo_id).getNumericCellValue()).longValue();
						if (legajoId > 0) {
							Contacto contacto = null;
							try {
								contacto = contactoService.findByLegajoId(legajoId);
							} catch (ContactoNotFoundException e) {
								contacto = new Contacto();
							}
							contacto.setLegajoId(legajoId);
							if (col_mail_personal != null)
								if (row.getCell(col_mail_personal) != null) {
									String mailpersonal = row.getCell(col_mail_personal).getStringCellValue();
									if (ToolService.mailvalidate(mailpersonal)) {
										contacto.setMailPersonal(mailpersonal);
										news = true;
									}
								}
							if (col_mail_institucional != null)
								if (row.getCell(col_mail_institucional) != null) {
									String mailinstitucional = row.getCell(col_mail_institucional).getStringCellValue();
									if (ToolService.mailvalidate(mailinstitucional)) {
										contacto.setMailInstitucional(mailinstitucional);
										news = true;
									}
								}
							if (news) {
								contactos.add(contacto);
								log.debug("Contacto -> " + contacto.toString());
								legajos.add(legajoId);
							}
						}
					}
				}
			}
			contactoService.saveAll(contactos);
			workbook.close();
			input.close();
			file.delete();
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		return repository.findAllById(legajos);
	}

}
