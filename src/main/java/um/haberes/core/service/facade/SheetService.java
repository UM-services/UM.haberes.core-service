/**
 *
 */
package um.haberes.core.service.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import um.haberes.core.client.CuentaMovimientoClient;
import um.haberes.core.exception.ContactoException;
import um.haberes.core.kotlin.model.*;
import um.haberes.core.kotlin.model.extern.CuentaDto;
import um.haberes.core.kotlin.model.extern.CuentaMovimientoDto;
import um.haberes.core.kotlin.model.view.LegajoCursoCantidad;
import um.haberes.core.kotlin.model.view.NovedadAcumulado;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AcreditacionException;
import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.exception.NovedadException;
import um.haberes.core.service.view.LegajoCursoCantidadService;
import um.haberes.core.service.view.NovedadAcumuladoService;
import um.haberes.core.util.Periodo;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.service.*;

/**
 * @author daniel
 */
@Service
@Slf4j
public class SheetService {

    private final CargoLiquidacionService cargoLiquidacionService;
    private final PersonaService personaService;
    private final FacultadService facultadService;
    private final CategoriaService categoriaService;
    private final GeograficaService geograficaService;
    private final DependenciaService dependenciaService;
    private final LiquidacionService liquidacionService;
    private final CodigoService codigoService;
    private final ItemService itemService;
    private final NovedadAcumuladoService novedadAcumuladoService;
    private final NovedadService novedadService;
    private final AcreditacionService acreditacionService;
    private final LegajoCursoCantidadService legajoCursoCantidadService;
    private final CargoClaseDetalleService cargoClaseDetalleService;
    private final LiquidacionAdicionalService liquidacionAdicionalService;
    private final LegajoBancoService legajoBancoService;
    private final ContactoService contactoService;
    private final Environment environment;
    private final LegajoCategoriaImputacionService legajoCategoriaImputacionService;
    private final LegajoCargoClaseImputacionService legajoCargoClaseImputacionService;
    private final CodigoGrupoService codigoGrupoService;
    private final LegajoCodigoImputacionService legajoCodigoImputacionService;
    private final CuentaMovimientoClient cuentaMovimientoClient;

    @Autowired
    public SheetService(PersonaService personaService, FacultadService facultadService, CategoriaService categoriaService, GeograficaService geograficaService,
                        DependenciaService dependenciaService, LiquidacionService liquidacionService, CodigoService codigoService, ItemService itemService,
                        NovedadAcumuladoService novedadAcumuladoService, NovedadService novedadService, AcreditacionService acreditacionService, LegajoCursoCantidadService legajoCursoCantidadService,
                        CargoLiquidacionService cargoLiquidacionService, CargoClaseDetalleService cargoClaseDetalleService, LiquidacionAdicionalService liquidacionAdicionalService, LegajoBancoService legajoBancoService,
                        ContactoService contactoService, Environment environment, LegajoCategoriaImputacionService legajoCategoriaImputacionService,
                        LegajoCargoClaseImputacionService legajoCargoClaseImputacionService, CodigoGrupoService codigoGrupoService, LegajoCodigoImputacionService legajoCodigoImputacionService, CuentaMovimientoClient cuentaMovimientoClient) {
        this.personaService = personaService;
        this.facultadService = facultadService;
        this.categoriaService = categoriaService;
        this.geograficaService = geograficaService;
        this.dependenciaService = dependenciaService;
        this.liquidacionService = liquidacionService;
        this.codigoService = codigoService;
        this.itemService = itemService;
        this.novedadAcumuladoService = novedadAcumuladoService;
        this.novedadService = novedadService;
        this.acreditacionService = acreditacionService;
        this.legajoCursoCantidadService = legajoCursoCantidadService;
        this.cargoLiquidacionService = cargoLiquidacionService;
        this.cargoClaseDetalleService = cargoClaseDetalleService;
        this.liquidacionAdicionalService = liquidacionAdicionalService;
        this.legajoBancoService = legajoBancoService;
        this.contactoService = contactoService;
        this.environment = environment;
        this.legajoCategoriaImputacionService = legajoCategoriaImputacionService;
        this.legajoCargoClaseImputacionService = legajoCargoClaseImputacionService;
        this.codigoGrupoService = codigoGrupoService;
        this.legajoCodigoImputacionService = legajoCodigoImputacionService;
        this.cuentaMovimientoClient = cuentaMovimientoClient;
    }

    public String generateLiquidables() {
        String path = environment.getProperty("path.files");

        String filename = path + "liquidables.xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Liquidables");
        Row row = null;
        int fila = 0;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "Apellido, Nombre", style_bold);
        this.setCellString(row, 2, "Estado", style_bold);
        this.setCellString(row, 3, "#Dependencia", style_bold);
        this.setCellString(row, 4, "Dependencia", style_bold);

        for (Persona persona : personaService.findAllLiquidables()) {
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            this.setCellInteger(row, 2, persona.getEstado(), style_normal);
            Dependencia dependencia = persona.getDependencia();
            if (dependencia == null) {
                dependencia = new Dependencia();
            }
            this.setCellInteger(row, 3, dependencia.getDependenciaId(), style_normal);
            this.setCellString(row, 4, dependencia.getNombre(), style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateCategorias() {
        String path = environment.getProperty("path.files");

        String filename = path + "categorias.xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Categorias");
        Row row = null;
        int fila = 0;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "categoria_id", style_bold);
        this.setCellString(row, 1, "nombre", style_bold);
        this.setCellString(row, 2, "basico", style_bold);
        this.setCellString(row, 3, "docente", style_bold);
        this.setCellString(row, 4, "no_docente", style_bold);
        this.setCellString(row, 5, "liquida_por_hora", style_bold);

        for (Categoria categoria : categoriaService.findAll()) {
            row = sheet.createRow(++fila);
            this.setCellInteger(row, 0, categoria.getCategoriaId(), style_bold);
            this.setCellString(row, 1, categoria.getNombre(), style_normal);
            this.setCellBigDecimal(row, 2, categoria.getBasico(), style_normal);
            this.setCellByte(row, 3, categoria.getDocente(), style_normal);
            this.setCellByte(row, 4, categoria.getNoDocente(), style_normal);
            this.setCellByte(row, 5, categoria.getLiquidaPorHora(), style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateCargos(Integer anho, Integer mes) {
        // Depurar los cargos de legajos no liquidados
        List<Long> legajos = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
                .map(Liquidacion::getLegajoId).collect(Collectors.toList());
        log.debug("Legajos" + legajos);
        cargoLiquidacionService.deleteAllNotInByPeriodo(legajos, anho, mes);

        String path = environment.getProperty("path.files");

        String filename = path + "cargos" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Cargos");
        Row row = null;
        int fila = 0;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "Apellido, Nombre", style_bold);
        this.setCellString(row, 2, "Periodo", style_bold);
        this.setCellString(row, 3, "#Dependencia", style_bold);
        this.setCellString(row, 4, "Dependencia", style_bold);
        this.setCellString(row, 5, "#Categoria", style_bold);
        this.setCellString(row, 6, "Categoria", style_bold);

        Map<Long, Persona> personas = personaService.findAllLegajos(legajos).stream()
                .collect(Collectors.toMap(Persona::getLegajoId, persona -> persona));

        Map<Integer, Dependencia> dependencias = dependenciaService.findAll().stream()
                .collect(Collectors.toMap(Dependencia::getDependenciaId, dependencia -> dependencia));

        Map<Integer, Categoria> categorias = categoriaService.findAll().stream()
                .collect(Collectors.toMap(Categoria::getCategoriaId, categoria -> categoria));

        for (CargoLiquidacion cargo : cargoLiquidacionService.findAllActivosByPeriodo(anho, mes)) {
            Persona persona = personas.get(cargo.getLegajoId());
            Dependencia dependencia = dependencias.get(cargo.getDependenciaId());
            Categoria categoria = categorias.get(cargo.getCategoriaId());
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, cargo.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            this.setCellString(row, 2, mes + "/" + anho, style_normal);
            if (cargo.getDependenciaId() != null) {
                this.setCellInteger(row, 3, cargo.getDependenciaId(), style_normal);
                this.setCellString(row, 4, dependencia.getNombre(), style_normal);
            }
            this.setCellInteger(row, 5, cargo.getCategoriaId(), style_normal);
            this.setCellString(row, 6, categoria.getNombre(), style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateItems(Integer anho, Integer mes) {
        List<Item> items = itemService.findAllByPeriodo(anho, mes, 1000000);
        List<Long> legajos = items.stream().map(Item::getLegajoId).collect(Collectors.toList());
        List<Codigo> codigos = codigoService
                .findAllByCodigoIds(items.stream().map(Item::getCodigoId).collect(Collectors.toList()));
        Map<String, Item> mapItems = items.stream()
                .collect(Collectors.toMap(Item::legajoKey, Function.identity(), (item, replacement) -> item));

        String path = environment.getProperty("path.files");

        String filename = path + "items" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Items");
        Row row = null;
        int fila = 0;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "CUIL", style_bold);
        this.setCellString(row, 2, "Apellido, Nombre", style_bold);
        int columnaDatos = 3;
        int columna = columnaDatos;
        for (Codigo codigo : codigos) {
            this.setCellString(row, columna++, codigo.getNombre() + " (" + codigo.getCodigoId() + ")", style_bold);
        }

        for (Persona persona : personaService.findAllLegajos(legajos)) {
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getCuil(), style_normal);
            this.setCellString(row, 2, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            columna = columnaDatos;
            for (Codigo codigo : codigos) {
                String key = persona.getLegajoId() + "." + anho + "." + mes + "." + codigo.getCodigoId();
                if (mapItems.containsKey(key)) {
                    Item item = mapItems.get(key);
                    this.setCellBigDecimal(row, columna, item.getImporte(), style_normal);
                }
                columna++;
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateCodigosNoDoc(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "codigosnodoc" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);

        Sheet sheet = book.createSheet("Códigos");
        Row row = null;
        int fila = 0;
        int columna = 1;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "Legajo", style_normal);
        this.setCellString(row, 1, "Apellido, Nombre", style_normal);
        List<Codigo> codigos = codigoService.findAll();
        for (Codigo codigo : codigos) {
            this.setCellString(row, ++columna, codigo.getNombre() + " (" + codigo.getCodigoId() + ")", style_normal);
        }

        for (Persona persona : personaService.findAllNoDocente(anho, mes)) {
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            Map<Integer, BigDecimal> items = itemService
                    .findAllByLegajo(persona.getLegajoId(), anho, mes).stream()
                    .collect(Collectors.toMap(Item::getCodigoId, Item::getImporte));
            columna = 1;
            for (Codigo codigo : codigos) {
                ++columna;
                if (items.containsKey(codigo.getCodigoId()))
                    this.setCellBigDecimal(row, columna, items.get(codigo.getCodigoId()), style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateCategoriasNoDoc(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "categoriasnodoc" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);

        Sheet sheet = book.createSheet("Categorías");
        Row row = null;
        int fila = 0;
        int columna = 1;
        row = sheet.createRow(fila);
        this.setCellString(row, 0, "Legajo", style_normal);
        this.setCellString(row, 1, "Apellido, Nombre", style_normal);
        List<Categoria> categorias = categoriaService.findAllNoDocentes();
        for (Categoria categoria : categorias) {
            this.setCellString(row, ++columna, categoria.getNombre() + " (" + categoria.getCategoriaId() + ")",
                    style_normal);
        }

        for (Persona persona : personaService.findAllNoDocente(anho, mes)) {
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            Map<Integer, String> marcas = categoriaService.findAllNoDocenteByLegajoId(persona.getLegajoId(), anho, mes)
                    .stream().collect(Collectors.toMap(Categoria::getCategoriaId, categoria -> "*"));
            columna = 1;
            for (Categoria categoria : categorias) {
                ++columna;
                if (marcas.containsKey(categoria.getCategoriaId()))
                    this.setCellString(row, columna, marcas.get(categoria.getCategoriaId()), style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String comparativoCodigo(Integer codigoId, Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "codigo" + codigoId + "-" + anho + String.format("%02d", mes) + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);

        Sheet sheet = book.createSheet("Código " + codigoId);
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Código " + codigoId, style_normal);
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Legajo", style_normal);
        this.setCellString(row, 1, "Apellido, Nombre", style_normal);
        this.setCellString(row, 2, "Liquidado", style_normal);
        this.setCellString(row, 3, "Novedad", style_normal);
        this.setCellString(row, 4, "#Cantidad", style_normal);

        Map<Long, BigDecimal> items = itemService.findAllByCodigo(codigoId, anho, mes).stream()
                .collect(Collectors.toMap(Item::getLegajoId, Item::getImporte));
        List<NovedadAcumulado> novedades = novedadAcumuladoService.findAllByCodigo(codigoId, anho, mes);
        Map<Long, BigDecimal> importes = novedades.stream()
                .collect(Collectors.toMap(NovedadAcumulado::getLegajoId, NovedadAcumulado::getImporte));
        Map<Long, Integer> cantidades = novedades.stream()
                .collect(Collectors.toMap(NovedadAcumulado::getLegajoId, NovedadAcumulado::getCantidad));

        for (Persona persona : personaService.findAllByLiquidado(anho, mes)) {
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            if (items.containsKey(persona.getLegajoId())) {
                this.setCellBigDecimal(row, 2, items.get(persona.getLegajoId()), style_normal);
            }
            if (importes.containsKey(persona.getLegajoId())) {
                this.setCellBigDecimal(row, 3, importes.get(persona.getLegajoId()), style_normal);
                this.setCellInteger(row, 4, cantidades.get(persona.getLegajoId()), style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(1).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String simulasac(Integer anho, Integer semestre) {
        String path = environment.getProperty("path.files");

        String filename = path + "simulasac" + anho + String.format("%02d", semestre) + ".xlsx";

        int mesDesde = (semestre - 1) * 6 + 1;
        int mesHasta = semestre * 6;
        boolean simulaUltimo = false;
        if (liquidacionService.findAllByPeriodo(anho, mesHasta, 1).isEmpty()) {
            simulaUltimo = true;
            mesHasta--;
        }

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);

        Sheet sheet = book.createSheet("SAC");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Legajo", style_normal);
        this.setCellString(row, 1, "Apellido, Nombre", style_normal);
        List<String> meses = null;
        if (semestre == 1)
            meses = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio");
        if (semestre == 2)
            meses = List.of("Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre");
        int columna = 1;
        for (String mes : meses) {
            this.setCellString(row, ++columna, mes, style_normal);
            if (columna == 7 && simulaUltimo) {
                this.setCellString(row, columna, mes + "(*)", style_normal);
            }
        }
        this.setCellString(row, ++columna, "Mejor Bruto", style_normal);
        this.setCellString(row, ++columna, "SAC 50%", style_normal);
        this.setCellString(row, ++columna, "Adelanto SAC", style_normal);

        for (Persona persona : personaService.findAllBySemestre(anho, semestre)) {
            Map<Integer, Liquidacion> liquidaciones = liquidacionService
                    .findAllBySemestreLegajo(anho, semestre, persona.getLegajoId(), 0).stream()
                    .collect(Collectors.toMap(Liquidacion::getMes, liquidacion -> liquidacion));
            int cantidadMeses = 0;
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellString(row, 1, persona.getApellido() + ", " + persona.getNombre(), style_normal);
            columna = 1;
            BigDecimal mejorBruto = BigDecimal.ZERO;
            BigDecimal brutoSimulado = BigDecimal.ZERO;
            BigDecimal asignacionPosgrado = BigDecimal.ZERO;
            BigDecimal asignacionPosgradoTotal = BigDecimal.ZERO;
            BigDecimal asignacionPosgradoProrrata = BigDecimal.ZERO;
            BigDecimal bruto = BigDecimal.ZERO;
            for (int mes = mesDesde; mes <= mesHasta; mes++) {
                Map<Integer, Item> items = new HashMap<>();
                if (liquidaciones.containsKey(mes)) {
                    items = itemService.findAllByLegajo(persona.getLegajoId(), anho, mes).stream()
                            .collect(Collectors.toMap(Item::getCodigoId, item -> item));
                }
                bruto = BigDecimal.ZERO;
                Integer codigoId = 96;
                if (items.containsKey(codigoId)) {
                    bruto = items.get(codigoId).getImporte();
                }
                codigoId = 22;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 23;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 25;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 3;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 25;
                if (items.containsKey(codigoId)) {
                    asignacionPosgradoTotal = asignacionPosgradoTotal.add(items.get(codigoId).getImporte()).setScale(2,
                            RoundingMode.HALF_UP);
                }
                BigDecimal importe25 = BigDecimal.ZERO;
                codigoId = 25;
                if (items.containsKey(codigoId)) {
                    importe25 = items.get(codigoId).getImporte();
                }
                this.setCellBigDecimal(row, ++columna, bruto.add(importe25), style_normal);
                if (mes == mesHasta) {
                    brutoSimulado = bruto;
                    asignacionPosgrado = importe25;
                }
                if (bruto.compareTo(mejorBruto) > 0)
                    mejorBruto = bruto;
                if (bruto.compareTo(BigDecimal.ZERO) > 0)
                    cantidadMeses++;
            }
            if (simulaUltimo) {
                if (bruto.compareTo(BigDecimal.ZERO) == 0) {
                    this.setCellBigDecimal(row, ++columna, BigDecimal.ZERO, style_normal);
                    mejorBruto = BigDecimal.ZERO;
                } else {
                    this.setCellBigDecimal(row, ++columna, brutoSimulado.add(asignacionPosgrado), style_normal);
                    asignacionPosgradoTotal = asignacionPosgradoTotal.add(asignacionPosgrado);
                    if (brutoSimulado.compareTo(BigDecimal.ZERO) > 0)
                        cantidadMeses++;
                }
            }
            if (cantidadMeses > 0) {
                if (mejorBruto.compareTo(BigDecimal.ZERO) > 0) {
                    asignacionPosgradoProrrata = asignacionPosgradoTotal.divide(new BigDecimal(cantidadMeses), 2,
                            RoundingMode.HALF_UP);
                    asignacionPosgradoProrrata = asignacionPosgradoProrrata.divide(new BigDecimal(6), 2,
                            RoundingMode.HALF_UP);
                    asignacionPosgradoProrrata = asignacionPosgradoProrrata.multiply(new BigDecimal(cantidadMeses));
                }
            } else {
                asignacionPosgradoProrrata = BigDecimal.ZERO;
            }
            this.setCellBigDecimal(row, ++columna, mejorBruto.add(asignacionPosgradoProrrata), style_normal);
            BigDecimal value = mejorBruto.divide(new BigDecimal(12), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(cantidadMeses));
            value = value.add(asignacionPosgradoProrrata.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));
            this.setCellBigDecimal(row, ++columna, value, style_normal);
            value = value.multiply(new BigDecimal("0.83")).setScale(2, RoundingMode.HALF_UP);
            this.setCellBigDecimal(row, ++columna, value, style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public void generasac(Integer anho, Integer semestre, Byte adelanto) {

        // Verifica que el período no esté acreditado para evitar reescrituras
        try {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, semestre * 6);
            // Si el periodo final del semestre está acreditado termina
            if (acreditacion.getAcreditado() == 1)
                return;
        } catch (AcreditacionException e) {
            log.debug("Sin Registro ACREDITACION");
        }

        List<Long> exceptos = List.of(2084L, 2458L, 2017L, 1638L, 2023L, 1948L, 2022L, 1382L, 2604L, 2500L, 878L);

        int mesDesde = (semestre - 1) * 6 + 1;
        int mesHasta = semestre * 6;
        boolean simulaUltimo = false;
        if (liquidacionService.findAllByPeriodo(anho, mesHasta, 1).isEmpty()) {
            simulaUltimo = true;
            mesHasta--;
        }

        List<Novedad> novedades = new ArrayList<>();
        for (Persona persona : personaService.findAllBySemestre(anho, semestre)) {
            Map<Integer, Liquidacion> liquidaciones = liquidacionService
                    .findAllBySemestreLegajo(anho, semestre, persona.getLegajoId(), 0).stream()
                    .collect(Collectors.toMap(Liquidacion::getMes, liquidacion -> liquidacion));
            int cantidadMeses = 0;
            BigDecimal mejorBruto = BigDecimal.ZERO;
            BigDecimal brutoSimulado = BigDecimal.ZERO;
            BigDecimal asignacionPosgrado = BigDecimal.ZERO;
            BigDecimal asignacionPosgradoTotal = BigDecimal.ZERO;
            BigDecimal asignacionPosgradoProrrata = BigDecimal.ZERO;
            BigDecimal bruto = BigDecimal.ZERO;
            boolean descontar23 = !exceptos.contains(persona.getLegajoId());
            for (int mes = mesDesde; mes <= mesHasta; mes++) {
                Map<Integer, Item> items = new HashMap<>();
                if (liquidaciones.containsKey(mes)) {
                    items = itemService.findAllByLegajo(persona.getLegajoId(), anho, mes).stream()
                            .collect(Collectors.toMap(Item::getCodigoId, item -> item));
                }
                bruto = BigDecimal.ZERO;
                Integer codigoId = 96;
                if (items.containsKey(codigoId)) {
                    bruto = items.get(codigoId).getImporte();
                }
                codigoId = 22;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                if (descontar23) {
                    codigoId = 23;
                    if (items.containsKey(codigoId)) {
                        bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                    }
                }
                codigoId = 25;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 3;
                if (items.containsKey(codigoId)) {
                    bruto = bruto.subtract(items.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                }
                codigoId = 25;
                if (items.containsKey(codigoId)) {
                    asignacionPosgradoTotal = asignacionPosgradoTotal.add(items.get(codigoId).getImporte()).setScale(2,
                            RoundingMode.HALF_UP);
                }
                if (mes == mesHasta) {
                    brutoSimulado = bruto;
                    codigoId = 25;
                    if (items.containsKey(codigoId)) {
                        asignacionPosgrado = items.get(codigoId).getImporte();
                    }
                }
                if (bruto.compareTo(mejorBruto) > 0)
                    mejorBruto = bruto;
                if (bruto.compareTo(BigDecimal.ZERO) > 0)
                    cantidadMeses++;
            }
            if (simulaUltimo) {
                if (bruto.compareTo(BigDecimal.ZERO) == 0) {
                    mejorBruto = BigDecimal.ZERO;
                } else {
                    asignacionPosgradoTotal = asignacionPosgradoTotal.add(asignacionPosgrado);
                    if (brutoSimulado.compareTo(BigDecimal.ZERO) > 0)
                        cantidadMeses++;
                }
            }
            if (cantidadMeses > 0) {
                if (mejorBruto.compareTo(BigDecimal.ZERO) > 0) {
                    asignacionPosgradoProrrata = asignacionPosgradoTotal.divide(new BigDecimal(cantidadMeses), 2,
                            RoundingMode.HALF_UP);
                    asignacionPosgradoProrrata = asignacionPosgradoProrrata.divide(new BigDecimal(6), 2,
                            RoundingMode.HALF_UP);
                    asignacionPosgradoProrrata = asignacionPosgradoProrrata.multiply(new BigDecimal(cantidadMeses));
                }
            } else {
                asignacionPosgradoProrrata = BigDecimal.ZERO;
            }
            BigDecimal aguinaldo = mejorBruto.divide(new BigDecimal(12), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(cantidadMeses));
            aguinaldo = aguinaldo.add(asignacionPosgradoProrrata.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));

            if (aguinaldo.compareTo(BigDecimal.ZERO) > 0) {
                Integer codigoId = adelanto == 1 ? 23 : 3;
                Long novedadId = null;
                BigDecimal importe = BigDecimal.ZERO;
                try {
                    Novedad novedad = novedadService.findByUnique(persona.getLegajoId(), anho, semestre * 6, codigoId,
                            null);
                    novedadId = novedad.getNovedadId();
                } catch (NovedadException e) {
                    novedadId = null;
                }
                importe = aguinaldo;
                if (adelanto == 1) {
                    importe = BigDecimal.valueOf(-0.83).multiply(aguinaldo).setScale(2, RoundingMode.HALF_UP);
                }
                novedades.add(new Novedad(novedadId, persona.getLegajoId(), anho, semestre * 6, codigoId, null, importe,
                        "", "Generación Externa", (byte) 0, null, null, null, null));
            }
        }
        novedadService.saveAll(novedades);
    }

    public String generateLegajoCursoCantidad(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "docentes." + anho + "." + mes + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Docentes");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Facultad", style_bold);
        this.setCellString(row, 1, "Sede", style_bold);
        this.setCellString(row, 2, "Legajo", style_bold);
        this.setCellString(row, 3, "Apellido, Nombre", style_bold);
        this.setCellString(row, 4, "Anuales", style_bold);
        this.setCellString(row, 5, "Semestre 1", style_bold);
        this.setCellString(row, 6, "Semestre 2", style_bold);
        this.setCellString(row, 7, "Bruto", style_bold);

        Map<Integer, Facultad> facultades = facultadService.findAll().stream()
                .collect(Collectors.toMap(Facultad::getFacultadId, facultad -> facultad));
        Map<Integer, Geografica> geograficas = geograficaService.findAll().stream()
                .collect(Collectors.toMap(Geografica::getGeograficaId, geografica -> geografica));
        List<LegajoCursoCantidad> legajoCursos = legajoCursoCantidadService.findAllByPeriodo(anho, mes);
        List<Long> legajoIds = legajoCursos.stream().map(LegajoCursoCantidad::getLegajoId)
                .collect(Collectors.toList());
        Map<Long, Persona> legajos = personaService.findAllLegajos(legajoIds).stream()
                .collect(Collectors.toMap(Persona::getLegajoId, persona -> persona));
        Map<Long, Item> items = itemService.findAllByCodigo(96, anho, mes).stream()
                .collect(Collectors.toMap(Item::getLegajoId, item -> item));
        for (LegajoCursoCantidad legajoCurso : legajoCursos) {
            row = sheet.createRow(++fila);
            this.setCellString(row, 0, facultades.get(legajoCurso.getFacultadId()).getNombre(), style_normal);
            this.setCellString(row, 1, geograficas.get(legajoCurso.getGeograficaId()).getNombre(), style_normal);
            this.setCellLong(row, 2, legajoCurso.getLegajoId(), style_normal);
            Persona legajo = legajos.get(legajoCurso.getLegajoId());
            this.setCellString(row, 3, legajo.getApellido() + ", " + legajo.getNombre(), style_normal);
            this.setCellInteger(row, 4, legajoCurso.getAnuales(), style_normal);
            this.setCellInteger(row, 5, legajoCurso.getSemestre1(), style_normal);
            this.setCellInteger(row, 6, legajoCurso.getSemestre2(), style_normal);
            if (items.containsKey(legajoCurso.getLegajoId())) {
                this.setCellBigDecimal(row, 7, items.get(legajoCurso.getLegajoId()).getImporte(), style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generateBasicos(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "basicos." + anho + "." + mes + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Básicos");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, MessageFormat.format("Periodo: {0}/{1}", mes, anho), style_bold);
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "Apellido, Nombre", style_bold);
        this.setCellString(row, 2, "Dependencia", style_bold);
        this.setCellString(row, 3, "#/Clase", style_bold);
        this.setCellString(row, 4, "Categoría/Cargo", style_bold);
        this.setCellString(row, 5, "Básico/Valor Hora", style_bold);
        this.setCellString(row, 6, "Horas Jornada", style_bold);
        this.setCellString(row, 7, "Jornada/Horas", style_bold);

        for (Liquidacion liquidacion : liquidacionService.findAllByPeriodo(anho, mes, 0)) {
            for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(liquidacion.getLegajoId(),
                    anho, mes)) {
                row = sheet.createRow(++fila);
                this.setCellLong(row, 0, liquidacion.getLegajoId(), style_normal);
                this.setCellString(row, 1, liquidacion.getPersona().getApellidoNombre(), style_normal);
                this.setCellString(row, 2, cargoLiquidacion.getDependencia().getNombre(), style_normal);
                this.setCellInteger(row, 3, cargoLiquidacion.getCategoriaId(), style_normal);
                this.setCellString(row, 4, cargoLiquidacion.getCategoriaNombre(), style_normal);
                this.setCellBigDecimal(row, 5, cargoLiquidacion.getCategoriaBasico(), style_normal);
                if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) > 0) {
                    this.setCellBigDecimal(row, 6, cargoLiquidacion.getHorasJornada(), style_normal);
                } else {
                    this.setCellInteger(row, 7, cargoLiquidacion.getJornada(), style_normal);
                }
            }
            for (CargoClaseDetalle cargoClaseDetalle : cargoClaseDetalleService
                    .findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                row = sheet.createRow(++fila);
                this.setCellLong(row, 0, liquidacion.getLegajoId(), style_normal);
                this.setCellString(row, 1, liquidacion.getPersona().getApellidoNombre(), style_normal);
                this.setCellString(row, 2, cargoClaseDetalle.getDependencia().getNombre(), style_normal);
                this.setCellString(row, 3, cargoClaseDetalle.getCargoClase().getClase().getNombre(), style_normal);
                this.setCellString(row, 4,
                        MessageFormat.format("{0} / {1}", cargoClaseDetalle.getCargoClase().getNombre(),
                                cargoClaseDetalle.getCargoClasePeriodo().getDescripcion()),
                        style_normal);
                this.setCellBigDecimal(row, 5, cargoClaseDetalle.getValorHora(), style_normal);
                this.setCellInteger(row, 7, cargoClaseDetalle.getHoras(), style_normal);
            }
            Codigo codigo = codigoService.findByCodigoId(981);
            for (LiquidacionAdicional liquidacionAdicional : liquidacionAdicionalService
                    .findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                row = sheet.createRow(++fila);
                this.setCellLong(row, 0, liquidacion.getLegajoId(), style_normal);
                this.setCellString(row, 1, liquidacion.getPersona().getApellidoNombre(), style_normal);
                this.setCellString(row, 2, liquidacionAdicional.getDependencia().getNombre(), style_normal);
                this.setCellInteger(row, 3, codigo.getCodigoId(), style_normal);
                this.setCellString(row, 4, codigo.getNombre(), style_normal);
                this.setCellBigDecimal(row, 5, liquidacionAdicional.getAdicional(), style_normal);
                this.setCellInteger(row, 7, 1, style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(1).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String comparaConsecutivos(Integer anho, Integer mes) {
        String path = environment.getProperty("path.files");

        String filename = path + "consecutivos." + anho + "." + mes + ".xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Comparativo");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, MessageFormat.format("Periodo Actual: {0}/{1}", mes, anho), style_bold);
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Dependencia", style_bold);
        this.setCellString(row, 1, "Legajo", style_bold);
        this.setCellString(row, 2, "Apellido, Nombre", style_bold);
        this.setCellString(row, 3, "Bruto Anterior", style_bold);
        this.setCellString(row, 4, "1/2 SAC Anterior", style_bold);
        this.setCellString(row, 5, "Bruto Actual", style_bold);
        this.setCellString(row, 6, "1/2 SAC Actual", style_bold);
        this.setCellString(row, 7, "Bruto Diferencia", style_bold);
        this.setCellString(row, 8, "Porcentaje", style_bold);
        this.setCellString(row, 9, "Neto Anterior", style_bold);
        this.setCellString(row, 10, "1/2 SAC Anterior (Est)", style_bold);
        this.setCellString(row, 11, "Neto Actual", style_bold);
        this.setCellString(row, 12, "1/2 SAC Actual (Est)", style_bold);
        this.setCellString(row, 13, "Neto Diferencia", style_bold);
        Map<String, Liquidacion> liquidacionMap = liquidacionService.findAllByPeriodo(anho, mes, 0).stream()
                .collect(Collectors.toMap(Liquidacion::key, liquidacion -> liquidacion));
        Map<String, Item> aguinaldoMap = itemService.findAllByCodigo(3, anho, mes).stream()
                .collect(Collectors.toMap(Item::legajoKey, item -> item));
        Periodo periodo = Periodo.prevMonth(anho, mes);
        Map<String, Liquidacion> liquidacionAnteriorMap = liquidacionService
                .findAllByPeriodo(periodo.getAnho(), periodo.getMes(), 0).stream()
                .collect(Collectors.toMap(Liquidacion::key, liquidacion -> liquidacion));
        Map<String, Item> aguinaldoAnteriorMap = itemService.findAllByCodigo(3, periodo.getAnho(), periodo.getMes())
                .stream().collect(Collectors.toMap(Item::legajoKey, item -> item));

        for (Persona persona : personaService.findAllOrderByDependencia()) {
            boolean liquidado = false;
            Liquidacion liquidacion = null;
            Liquidacion liquidacionAnterior = null;
            BigDecimal brutoAnterior = BigDecimal.ZERO;
            BigDecimal brutoActual = BigDecimal.ZERO;
            BigDecimal netoAnterior = BigDecimal.ZERO;
            BigDecimal netoActual = BigDecimal.ZERO;
            BigDecimal aguinaldoActual = BigDecimal.ZERO;
            BigDecimal aguinaldoAnterior = BigDecimal.ZERO;
            String key = persona.getLegajoId() + "." + anho + "." + mes;
            if (liquidacionMap.containsKey(key)) {
                liquidado = true;
                liquidacion = liquidacionMap.get(key);
                brutoActual = liquidacion.getTotalRemunerativo();
                netoActual = liquidacion.getTotalNeto();
            }
            key = persona.getLegajoId() + "." + periodo.getAnho() + "." + periodo.getMes();
            if (liquidacionAnteriorMap.containsKey(key)) {
                liquidado = true;
                liquidacionAnterior = liquidacionAnteriorMap.get(key);
                brutoAnterior = liquidacionAnterior.getTotalRemunerativo();
                netoAnterior = liquidacionAnterior.getTotalNeto();
            }
            key = persona.getLegajoId() + "." + anho + "." + mes + ".3";
            if (aguinaldoMap.containsKey(key)) {
                aguinaldoActual = aguinaldoMap.get(key).getImporte();
            }
            key = persona.getLegajoId() + "." + periodo.getAnho() + "." + periodo.getMes() + ".3";
            if (aguinaldoAnteriorMap.containsKey(key)) {
                aguinaldoAnterior = aguinaldoAnteriorMap.get(key).getImporte();
            }
            if (liquidado) {
                row = sheet.createRow(++fila);
                this.setCellString(row, 0, persona.getDependencia().getAcronimo(), style_normal);
                this.setCellLong(row, 1, persona.getLegajoId(), style_normal);
                this.setCellString(row, 2, persona.getApellidoNombre(), style_normal);
                this.setCellBigDecimal(row, 3, brutoAnterior, style_normal);
                this.setCellBigDecimal(row, 4, aguinaldoAnterior, style_normal);
                this.setCellBigDecimal(row, 5, brutoActual, style_normal);
                this.setCellBigDecimal(row, 6, aguinaldoActual, style_normal);
                this.setCellBigDecimal(row, 7, brutoActual.add(aguinaldoAnterior).subtract(brutoAnterior)
                        .subtract(aguinaldoActual).setScale(2, RoundingMode.HALF_UP), style_normal);
                BigDecimal porcentaje = BigDecimal.ZERO;
                if (brutoAnterior.subtract(aguinaldoAnterior).compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal numerador = brutoActual.subtract(aguinaldoActual).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal denominador = brutoAnterior.subtract(aguinaldoAnterior).setScale(2,
                            RoundingMode.HALF_UP);
                    porcentaje = numerador.divide(denominador, 4, RoundingMode.HALF_UP);
                    porcentaje = porcentaje.multiply(new BigDecimal("100.0")).setScale(4, RoundingMode.HALF_UP);
                    porcentaje = porcentaje.subtract(new BigDecimal("100.0"));
                }
                this.setCellBigDecimal(row, 8, porcentaje, style_normal);

                aguinaldoActual = aguinaldoActual.multiply(new BigDecimal("0.83")).setScale(2, RoundingMode.HALF_UP);
                aguinaldoAnterior = aguinaldoAnterior.multiply(new BigDecimal("0.83")).setScale(2, RoundingMode.HALF_UP);
                this.setCellBigDecimal(row, 9, netoAnterior, style_normal);
                this.setCellBigDecimal(row, 10, aguinaldoAnterior, style_normal);
                this.setCellBigDecimal(row, 11, netoActual, style_normal);
                this.setCellBigDecimal(row, 12, aguinaldoActual, style_normal);
                this.setCellBigDecimal(row, 13, netoActual.add(aguinaldoAnterior).subtract(netoAnterior)
                        .subtract(aguinaldoActual).setScale(2, RoundingMode.HALF_UP), style_normal);
            }
        }

        for (int column = 0; column < sheet.getRow(1).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String generatePersonales() {
        String[] posgrados = {"Sin Posgrado", "Doctorado", "Maestría", "Especialización"};

        String path = environment.getProperty("path.files");

        String filename = path + "personales.xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Sheet sheet = book.createSheet("Personales");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "Documento", style_bold);
        this.setCellString(row, 2, "Apellido, Nombre", style_bold);
        this.setCellString(row, 3, "Nacimiento", style_bold);
        this.setCellString(row, 4, "Alta Docente", style_bold);
        this.setCellString(row, 5, "Ajuste Docente", style_bold);
        this.setCellString(row, 6, "Alta Administrativa", style_bold);
        this.setCellString(row, 7, "Ajuste Administrativo", style_bold);
        this.setCellString(row, 8, "Estado Civil", style_bold);
        this.setCellString(row, 9, "Reemplazo Desarraigo", style_bold);
        this.setCellString(row, 10, "Mitad Desarraigo", style_bold);
        this.setCellString(row, 11, "CUIL", style_bold);
        this.setCellString(row, 12, "Posgrado", style_bold);
        this.setCellString(row, 13, "Estado", style_bold);
        this.setCellString(row, 14, "Liquida", style_bold);
        this.setCellString(row, 15, "Estado AFIP", style_bold);
        this.setCellString(row, 16, "Dependencia", style_bold);
        this.setCellString(row, 17, "Salida", style_bold);
        this.setCellString(row, 18, "Obra Social", style_bold);
        this.setCellString(row, 19, "Actividad AFIP", style_bold);
        this.setCellString(row, 20, "Localidad AFIP", style_bold);
        this.setCellString(row, 21, "Situacion AFIP", style_bold);
        this.setCellString(row, 22, "Modelo Contratación AFIP", style_bold);
        this.setCellString(row, 23, "CBU", style_bold);
        this.setCellString(row, 24, "Fijo", style_bold);
        this.setCellString(row, 25, "Porcentaje", style_bold);
        this.setCellString(row, 26, "Resto", style_bold);
        this.setCellString(row, 27, "e-mail Personal", style_bold);
        this.setCellString(row, 28, "e-mail Institucional", style_bold);

        for (Persona persona : personaService.findAll()) {
            Contacto contacto = null;
            try {
                contacto = contactoService.findByLegajoId(persona.getLegajoId());
            } catch (ContactoException e) {
                contacto = new Contacto();
            }
            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, persona.getLegajoId(), style_normal);
            this.setCellBigDecimal(row, 1, persona.getDocumento(), style_normal);
            this.setCellString(row, 2, persona.getApellidoNombre(), style_normal);
            if (persona.getNacimiento() != null) {
                this.setCellOffsetDateTime(row, 3, persona.getNacimiento(), style_normal);
            }
            if (persona.getAltaDocente() != null) {
                this.setCellOffsetDateTime(row, 4, persona.getAltaDocente(), style_normal);
            }
            this.setCellInteger(row, 5, persona.getAjusteDocente(), style_normal);
            if (persona.getAltaAdministrativa() != null) {
                this.setCellOffsetDateTime(row, 6, persona.getAltaAdministrativa(), style_normal);
            }
            this.setCellInteger(row, 7, persona.getAjusteAdministrativo(), style_normal);
            this.setCellString(row, 8, persona.getEstadoCivil(), style_normal);
            this.setCellString(row, 9, persona.getReemplazoDesarraigo() == 1 ? "*" : "", style_normal);
            this.setCellString(row, 10, persona.getMitadDesarraigo() == 1 ? "*" : "", style_normal);
            this.setCellString(row, 11, persona.getCuil(), style_normal);
            this.setCellString(row, 12, posgrados[persona.getPosgrado()], style_normal);
            this.setCellInteger(row, 13, persona.getEstado(), style_normal);
            this.setCellString(row, 14, persona.getLiquida(), style_normal);
            this.setCellInteger(row, 15, persona.getEstadoAfip(), style_normal);
            if (persona.getDependenciaId() != null) {
                this.setCellString(row, 16, persona.getDependencia().getAcronimo(), style_normal);
            }
            this.setCellString(row, 17, persona.getSalida(), style_normal);
            if (persona.getObraSocial() != null) {
                this.setCellLong(row, 18, persona.getObraSocial(), style_normal);
            }
            if (persona.getActividadAfip() != null) {
                this.setCellInteger(row, 19, persona.getActividadAfip(), style_normal);
            }
            if (persona.getLocalidadAfip() != null) {
                this.setCellInteger(row, 20, persona.getLocalidadAfip(), style_normal);
            }
            this.setCellInteger(row, 21, persona.getSituacionAfip(), style_normal);
            if (persona.getModeloContratacionAfip() != null) {
                this.setCellInteger(row, 22, persona.getModeloContratacionAfip(), style_normal);
            }
            try {
                boolean first = true;
                LegajoBanco legajoBanco = legajoBancoService.findLastByLegajoId(persona.getLegajoId());
                for (LegajoBanco banco : legajoBancoService.findAllByLegajoPeriodo(persona.getLegajoId(),
                        legajoBanco.getAnho(), legajoBanco.getMes())) {
                    String flecha = "---> ";
                    if (first) {
                        first = false;
                    } else {
                        flecha = "^-->";
                        row = sheet.createRow(++fila);
                    }
                    this.setCellString(row, 23, flecha + banco.getCbu(), style_normal);
                    this.setCellBigDecimal(row, 24, banco.getFijo(), style_normal);
                    this.setCellBigDecimal(row, 25, banco.getPorcentaje(), style_normal);
                    this.setCellString(row, 26, banco.getResto() == 1 ? "*" : "", style_normal);
                }
            } catch (LegajoBancoException e) {
                log.debug("Nothing to do");
            }
            this.setCellString(row, 27, contacto.getMailPersonal(), style_normal);
            this.setCellString(row, 28, contacto.getMailInstitucional(), style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String comparaImputaciones(Integer anhoDesde, Integer mesDesde, Integer anhoHasta, Integer mesHasta) {

        String path = environment.getProperty("path.files");

        String filename = path + "personales.xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        Map<BigDecimal, CuentaDto> cuentaMap = new HashMap<>();

        Sheet sheet = book.createSheet("Personales");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Cuenta", style_bold);

        Map<String, Map> saldos = new HashMap<>();
        for (int anho = anhoDesde, mes = mesDesde, column = 1; Periodo.toLong(anho, mes) <= Periodo.toLong(anhoHasta, mesHasta); column++) {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, mes);
            StringBuilder builder = new StringBuilder();
            builder.append(mes);
            builder.append(".");
            builder.append(anho);
            this.setCellString(row, column, builder.toString(), style_bold);
            Map<BigDecimal, CuentaMovimientoDto> cuentaMovimientoMap = null;
            if (acreditacion.getOrdenContable() > 0) {
                cuentaMovimientoMap = cuentaMovimientoClient.findAllByAsiento(acreditacion.getFechaContable(), acreditacion.getOrdenContable()).stream().collect(Collectors.toMap(CuentaMovimientoDto::getNumeroCuenta, cuentaMovimiento -> cuentaMovimiento));
                for (CuentaMovimientoDto cuentaMovimientoDto : cuentaMovimientoMap.values()) {
                    if (!cuentaMap.containsKey(cuentaMovimientoDto.getNumeroCuenta())) {
                        cuentaMap.put(cuentaMovimientoDto.getNumeroCuenta(), cuentaMovimientoDto.getCuentaDto());
                    }
                }
            }
            saldos.put(builder.toString(), cuentaMovimientoMap);
            Periodo next = Periodo.nextMonth(anho, mes);
            anho = next.getAnho();
            mes = next.getMes();
        }

        for (CuentaDto cuenta : cuentaMap.values()) {
            row = sheet.createRow(++fila);
            StringBuilder builder = new StringBuilder();
            builder.append(cuenta.getNumeroCuenta());
            builder.append(" - ");
            builder.append(cuenta.getNombre());
            this.setCellString(row, 0, builder.toString(), style_normal);
            for (int anho = anhoDesde, mes = mesDesde, column = 1; Periodo.toLong(anho, mes) <= Periodo.toLong(anhoHasta, mesHasta); column++) {
                builder = new StringBuilder();
                builder.append(mes);
                builder.append(".");
                builder.append(anho);
                if (saldos.containsKey(builder.toString())) {
                    Map<BigDecimal, CuentaMovimientoDto> cuentaMovimientoMap = saldos.get(builder.toString());
                    if (cuentaMovimientoMap != null) {
                        if (cuentaMovimientoMap.containsKey(cuenta.getNumeroCuenta())) {
                            CuentaMovimientoDto cuentaMovimientoDto = cuentaMovimientoMap.get(cuenta.getNumeroCuenta());
                            BigDecimal importe = cuentaMovimientoDto.getImporte();
                            if (cuentaMovimientoDto.getDebita() == 0) {
                                importe = new BigDecimal(-1).multiply(importe).setScale(2, RoundingMode.HALF_UP);
                            }
                            this.setCellBigDecimal(row, column, importe, style_normal);
                        }
                    }
                }
                Periodo next = Periodo.nextMonth(anho, mes);
                anho = next.getAnho();
                mes = next.getMes();
            }
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String cruceImputaciones(Integer anho, Integer mes) {

        String path = environment.getProperty("path.files");

        String filename = path + "cruce.xlsx";

        Workbook book = new XSSFWorkbook();
        CellStyle style_normal = book.createCellStyle();
        Font font_normal = book.createFont();
        font_normal.setBold(false);
        style_normal.setFont(font_normal);
        CellStyle style_bold = book.createCellStyle();
        Font font_bold = book.createFont();
        font_bold.setBold(true);
        style_bold.setFont(font_bold);

        // Codigos Remunerativos
        List<Integer> codigoIdRemunerativos = codigoGrupoService.findAllByRemunerativo((byte) 1).stream().map(CodigoGrupo::getCodigoId).collect(Collectors.toList());
        List<Integer> codigoIdNoRemunerativos = codigoGrupoService.findAllByNoRemunerativo((byte) 1).stream().map(CodigoGrupo::getCodigoId).collect(Collectors.toList());

        Sheet sheet = book.createSheet("Acreditados");
        Row row = null;
        int fila = -1;
        row = sheet.createRow(++fila);
        this.setCellString(row, 0, "Legajo", style_bold);
        this.setCellString(row, 1, "Apellido, Nombre", style_bold);
        this.setCellString(row, 2, "Cargos", style_bold);
        this.setCellString(row, 3, "Cargos Imputacion Basico", style_bold);
        this.setCellString(row, 4, "Cargos Imputacion Antigüedad", style_bold);
        this.setCellString(row, 5, "Cargos Clase", style_bold);
        this.setCellString(row, 6, "Cargos Clase Imputacion Basico", style_bold);
        this.setCellString(row, 7, "Cargos Clase Imputacion Antigüedad", style_bold);
        this.setCellString(row, 8, "Basico Liquidacion", style_bold);
        this.setCellString(row, 9, "Diferencia Basico", style_bold);
        this.setCellString(row, 10, "Antigüedad Liquidacion", style_bold);
        this.setCellString(row, 11, "Diferencia Antigüedad", style_bold);
        this.setCellString(row, 12, "Codigos Imputacion Remunerativos", style_bold);
        this.setCellString(row, 13, "Codigos Imputacion No Remunerativos", style_bold);
        this.setCellString(row, 14, "Remunerativos Liquidacion", style_bold);
        this.setCellString(row, 15, "Diferencia Remunerativos", style_bold);
        this.setCellString(row, 16, "No Remunerativos Liquidacion", style_bold);
        this.setCellString(row, 17, "Diferencia No Remunerativos", style_bold);

        for (Liquidacion liquidacion : liquidacionService.findAllByAcreditado(anho, mes)) {
//        Long[] valores = {54L, 57L, 725L, 1515L};
//        for (Liquidacion liquidacion : liquidacionService.findAllAcreditadoByLegajoIdIn(anho, mes, Arrays.asList(valores))) {
            Map<Integer, Item> itemMap = itemService.findAllByLegajo(liquidacion.getLegajoId(), anho, mes).stream().collect(Collectors.toMap(Item::getCodigoId, item -> item));
            BigDecimal totalCargos = BigDecimal.ZERO;
            for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                BigDecimal multiplicador = new BigDecimal(cargoLiquidacion.getJornada());
                if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) > 0) {
                    multiplicador = cargoLiquidacion.getHorasJornada();
                }
                totalCargos = totalCargos.add(cargoLiquidacion.getCategoriaBasico().multiply(multiplicador)).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal totalCargosBasicoImputacion = BigDecimal.ZERO;
            BigDecimal totalCargosAntiguedadImputacion = BigDecimal.ZERO;
            for (LegajoCategoriaImputacion legajoCategoriaImputacion : legajoCategoriaImputacionService.findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                totalCargosBasicoImputacion = totalCargosBasicoImputacion.add(legajoCategoriaImputacion.getBasico()).setScale(2, RoundingMode.HALF_UP);
                totalCargosAntiguedadImputacion = totalCargosAntiguedadImputacion.add(legajoCategoriaImputacion.getAntiguedad()).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal totalCargosClase = BigDecimal.ZERO;
            for (CargoClaseDetalle cargoClaseDetalle : cargoClaseDetalleService.findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                totalCargosClase = totalCargosClase.add(cargoClaseDetalle.getValorHora().multiply(new BigDecimal(cargoClaseDetalle.getHoras()))).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal totalCargosClaseBasicoImputacion = BigDecimal.ZERO;
            BigDecimal totalCargosClaseAntiguedadImputacion = BigDecimal.ZERO;
            for (LegajoCargoClaseImputacion legajoCargoClaseImputacion : legajoCargoClaseImputacionService.findAllByLegajo(liquidacion.getLegajoId(), anho, mes)) {
                totalCargosClaseBasicoImputacion = totalCargosClaseBasicoImputacion.add(legajoCargoClaseImputacion.getBasico()).setScale(2, RoundingMode.HALF_UP);
                totalCargosClaseAntiguedadImputacion = totalCargosClaseAntiguedadImputacion.add(legajoCargoClaseImputacion.getAntiguedad()).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal basicoLiquidacion = BigDecimal.ZERO;
            BigDecimal antiguedadLiquidacion = BigDecimal.ZERO;
            if (itemMap.containsKey(1)) {
                basicoLiquidacion = itemMap.get(1).getImporte();
            }
            if (itemMap.containsKey(2)) {
                antiguedadLiquidacion = itemMap.get(2).getImporte();
            }
            BigDecimal totalCodigosRemunerativosImputacion = BigDecimal.ZERO;
            for (LegajoCodigoImputacion legajoCodigoImputacion : legajoCodigoImputacionService.findAllByLegajoAndCodigos(liquidacion.getLegajoId(), anho, mes, codigoIdRemunerativos)) {
                totalCodigosRemunerativosImputacion = totalCodigosRemunerativosImputacion.add(legajoCodigoImputacion.getImporte()).setScale(2, RoundingMode.HALF_UP);
            }

            // Calcula los items remunerativos liquidados excepto basico y antiguedad
            codigoIdRemunerativos = codigoIdRemunerativos.stream().filter(codigoId -> codigoId > 2).collect(Collectors.toList());
            BigDecimal totalRemunerativosLiquidacion = BigDecimal.ZERO;
            for (Item item : itemService.findAllCodigoIdsByLegajo(liquidacion.getLegajoId(), anho, mes, codigoIdRemunerativos)) {
                totalRemunerativosLiquidacion = totalRemunerativosLiquidacion.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
            }

            BigDecimal totalCodigosNoRemunerativosImputacion = BigDecimal.ZERO;
            for (LegajoCodigoImputacion legajoCodigoImputacion : legajoCodigoImputacionService.findAllByLegajoAndCodigos(liquidacion.getLegajoId(), anho, mes, codigoIdNoRemunerativos)) {
                totalCodigosNoRemunerativosImputacion = totalCodigosNoRemunerativosImputacion.add(legajoCodigoImputacion.getImporte()).setScale(2, RoundingMode.HALF_UP);
            }

            // Calcula los items no remunerativos liquidados
            BigDecimal totalNoRemunerativosLiquidacion = BigDecimal.ZERO;
            for (Item item : itemService.findAllCodigoIdsByLegajo(liquidacion.getLegajoId(), anho, mes, codigoIdNoRemunerativos)) {
                totalNoRemunerativosLiquidacion = totalNoRemunerativosLiquidacion.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
            }

            BigDecimal diferenciaBasico = basicoLiquidacion.subtract(totalCargosBasicoImputacion).subtract(totalCargosClaseBasicoImputacion).setScale(2, RoundingMode.HALF_UP);
            BigDecimal diferenciaAntiguedad = antiguedadLiquidacion.subtract(totalCargosAntiguedadImputacion).subtract(totalCargosClaseAntiguedadImputacion).setScale(2, RoundingMode.HALF_UP);
            BigDecimal diferenciaRemunerativos = totalRemunerativosLiquidacion.subtract(totalCodigosRemunerativosImputacion).setScale(2, RoundingMode.HALF_UP);
            BigDecimal diferenciaNoRemunerativos = totalNoRemunerativosLiquidacion.subtract(totalCodigosNoRemunerativosImputacion).setScale(2, RoundingMode.HALF_UP);

            row = sheet.createRow(++fila);
            this.setCellLong(row, 0, liquidacion.getLegajoId(), style_normal);
            this.setCellString(row, 1, liquidacion.getPersona().getApellidoNombre(), style_normal);
            this.setCellBigDecimal(row, 2, totalCargos, style_normal);
            this.setCellBigDecimal(row, 3, totalCargosBasicoImputacion, style_normal);
            this.setCellBigDecimal(row, 4, totalCargosAntiguedadImputacion, style_normal);
            this.setCellBigDecimal(row, 5, totalCargosClase, style_normal);
            this.setCellBigDecimal(row, 6, totalCargosClaseBasicoImputacion, style_normal);
            this.setCellBigDecimal(row, 7, totalCargosClaseAntiguedadImputacion, style_normal);
            this.setCellBigDecimal(row, 8, basicoLiquidacion, style_normal);
            this.setCellBigDecimal(row, 9, diferenciaBasico, style_normal);
            this.setCellBigDecimal(row, 10, antiguedadLiquidacion, style_normal);
            this.setCellBigDecimal(row, 11, diferenciaAntiguedad, style_normal);
            this.setCellBigDecimal(row, 12, totalCodigosRemunerativosImputacion, style_normal);
            this.setCellBigDecimal(row, 13, totalCodigosNoRemunerativosImputacion, style_normal);
            this.setCellBigDecimal(row, 14, totalRemunerativosLiquidacion, style_normal);
            this.setCellBigDecimal(row, 15, diferenciaRemunerativos, style_normal);
            this.setCellBigDecimal(row, 16, totalNoRemunerativosLiquidacion, style_normal);
            this.setCellBigDecimal(row, 17, diferenciaNoRemunerativos, style_normal);
        }

        for (int column = 0; column < sheet.getRow(0).getPhysicalNumberOfCells(); column++)
            sheet.autoSizeColumn(column);

        try {
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);
            book.write(output);
            output.flush();
            output.close();
            log.debug(file.getAbsolutePath());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    private void setCellOffsetDateTime(Row row, int column, OffsetDateTime value, CellStyle style) {
        setCellString(row, column, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(value), style);
    }

    private void setCellBigDecimal(Row row, int column, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private void setCellString(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellLong(Row row, int column, Long value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellInteger(Row row, int column, Integer value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setCellByte(Row row, int column, Byte value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

}
