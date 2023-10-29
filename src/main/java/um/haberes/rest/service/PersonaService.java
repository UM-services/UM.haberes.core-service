/**
 *
 */
package um.haberes.rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import um.haberes.rest.kotlin.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ContactoException;
import um.haberes.rest.exception.PersonaException;
import um.haberes.rest.kotlin.model.view.PersonaSearch;
import um.haberes.rest.repository.IPersonaRepository;
import um.haberes.rest.service.facade.ToolService;
import um.haberes.rest.service.view.PersonaSearchService;
import um.haberes.rest.util.Tool;
import um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class PersonaService {

    private final IPersonaRepository repository;

    private final PersonaSearchService personaSearchService;

    private final LiquidacionService liquidacionService;

    private final CategoriaService categoriaService;

    private final CargoLiquidacionService cargoLiquidacionService;

    private final CursoCargoService cursoCargoService;

    private final ContactoService contactoService;

    @Autowired
    public PersonaService(IPersonaRepository repository, PersonaSearchService personaSearchService, LiquidacionService liquidacionService,
                          CategoriaService categoriaService, CargoLiquidacionService cargoLiquidacionService, CursoCargoService cursoCargoService,
                          ContactoService contactoService) {
        this.repository = repository;
        this.personaSearchService = personaSearchService;
        this.liquidacionService = liquidacionService;
        this.categoriaService = categoriaService;
        this.cargoLiquidacionService = cargoLiquidacionService;
        this.cursoCargoService = cursoCargoService;
        this.contactoService = contactoService;
    }

    public List<Persona> findAll() {
        return repository.findAll();
    }

    public List<Persona> findAllDocente(Integer anho, Integer mes) {
        List<Long> legajos = cursoCargoService.findAllByAnhoAndMes(anho, mes).stream().map(CursoCargo::getLegajoId)
                .collect(Collectors.toList());
        return repository.findAllByLegajoIdIn(legajos,
                Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
    }

    public List<Persona> findAllNoDocente(Integer anho, Integer mes) {
        // Categorias No Docentes
        List<Integer> categoriaIds = categoriaService.findAllNoDocentes().stream()
                .map(Categoria::getCategoriaId).collect(Collectors.toList());
        // Legajos Liquidados en el período
        List<Long> ids = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
                .map(Liquidacion::getLegajoId).collect(Collectors.toList());
        // Legajos liquidados y acreditados con categorias no docentes
        List<Long> legajoIds = cargoLiquidacionService
                .findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(ids, categoriaIds, anho, mes).stream()
                .map(CargoLiquidacion::getLegajoId).collect(Collectors.toList());
        // Personas con los legajos filtrados
        return repository.findAllByLegajoIdIn(legajoIds, Sort.by("legajoId").ascending());
    }

    public List<Persona> findAllBySemestre(Integer anho, Integer semestre) {
        List<Long> legajos = liquidacionService.findAllByAnhoAndMesBetween(anho, (semestre - 1) * 6 + 1, semestre * 6)
                .stream().map(Liquidacion::getLegajoId).collect(Collectors.toList());
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
                .map(CursoCargo::getLegajoId).collect(Collectors.toList());
        return repository.findAllByLegajoIdIn(legajos,
                Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
    }

    public List<Persona> findAllByLiquidado(Integer anho, Integer mes) {
        List<Long> legajos = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
                .map(Liquidacion::getLegajoId).collect(Collectors.toList());
        return repository.findAllByLegajoIdIn(legajos,
                Sort.by("apellido").ascending().and(Sort.by("nombre").ascending()));
    }

    public List<Persona> findAllByFiltro(String filtro) {
        return repository.findAllByApellidoLike("%" + filtro + "%");
    }

    public List<Persona> findAllLiquidables() {
        return repository.findAllByLiquida("S", Sort.by("apellido").and(Sort.by("nombre")));
    }

    public List<Persona> findAllOrderByDependencia() {
        return repository.findAll(Sort.by("dependencia.dependenciaId")
                .and(Sort.by("apellido").and(Sort.by("nombre").and(Sort.by("legajoId")))));
    }

    public Persona findByLegajoId(Long legajoId) {
        return repository.findByLegajoId(legajoId).orElseThrow(() -> new PersonaException(legajoId));
    }

    public Persona findByDocumento(BigDecimal documento) {
        return repository.findByDocumento(documento).orElseThrow(() -> new PersonaException(documento));
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
                    newPersona.getModeloContratacionAfip(), newPersona.getDirectivoEtec(), newPersona.getDependencia(), newPersona.getAfipSituacion());
            repository.save(persona);
            log.debug(persona.toString());
            return persona;
        }).orElseThrow(() -> new PersonaException(legajoId));
    }

    public List<Persona> saveall(List<Persona> personas) {
        repository.saveAll(personas);
        return personas;
    }

    public List<Persona> upload(FileInfo fileinfo) {
        File file = Tool.writeFile(fileinfo);
        InputStream input;
        List<Long> legajos = new ArrayList<>();
        try {
            input = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();
            int rows = sheet.getLastRowNum() + 1;
            log.debug("rows -> " + rows);
            Row row = sheet.getRow(0);
            Integer col_legajo_id = null;
            Integer col_mail_personal = null;
            Integer col_mail_institucional = null;
            for (int col = 0; col < cols; col++) {
                if (row.getCell(col).getStringCellValue().equals("legajo_id"))
                    col_legajo_id = col;
                if (row.getCell(col).getStringCellValue().equals("mail_personal"))
                    col_mail_personal = col;
                if (row.getCell(col).getStringCellValue().equals("mail_institucional"))
                    col_mail_institucional = col;
            }
            List<Contacto> contactos = new ArrayList<>();
            for (int row_number = 1; row_number < rows; row_number++) {
                row = sheet.getRow(row_number);
                if (col_legajo_id != null) {
                    if (row.getCell(col_legajo_id) != null) {
                        boolean news = false;
                        Long legajoId = Double.valueOf(row.getCell(col_legajo_id).getNumericCellValue()).longValue();
                        if (legajoId > 0) {
                            Contacto contacto = null;
                            try {
                                contacto = contactoService.findByLegajoId(legajoId);
                            } catch (ContactoException e) {
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
                                log.debug("Contacto -> " + contacto);
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
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return repository.findAllById(legajos);
    }

}
