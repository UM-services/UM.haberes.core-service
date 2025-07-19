/**
 *
 */
package um.haberes.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CategoriaException;
import um.haberes.core.exception.CategoriaPeriodoException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.kotlin.model.CargoLiquidacion;
import um.haberes.core.kotlin.model.Categoria;
import um.haberes.core.kotlin.model.CategoriaPeriodo;
import um.haberes.core.kotlin.model.Designacion;
import um.haberes.core.kotlin.model.view.CategoriaByPeriodo;
import um.haberes.core.kotlin.model.view.CategoriaSearch;
import um.haberes.core.repository.CargoLiquidacionRepository;
import um.haberes.core.repository.CategoriaRepository;
import um.haberes.core.service.view.CategoriaByPeriodoService;
import um.haberes.core.service.view.CategoriaSearchService;
import um.haberes.core.util.Tool;
import java.util.Set;
import um.haberes.core.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 */
@Service
@Slf4j
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final CategoriaPeriodoService categoriaPeriodoService;
    private final CategoriaSearchService categoriaSearchService;
    private final CategoriaByPeriodoService categoriaByPeriodoService;
    private final DesignacionService designacionService;

    public CategoriaService(CategoriaRepository repository,
                            CargoLiquidacionRepository cargoLiquidacionRepository,
                            CategoriaPeriodoService categoriaPeriodoService,
                            CategoriaSearchService categoriaSearchService,
                            CategoriaByPeriodoService categoriaByPeriodoService,
                            DesignacionService designacionService) {
        this.repository = repository;
        this.cargoLiquidacionRepository = cargoLiquidacionRepository;
        this.categoriaPeriodoService = categoriaPeriodoService;
        this.categoriaSearchService = categoriaSearchService;
        this.categoriaByPeriodoService = categoriaByPeriodoService;
        this.designacionService = designacionService;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public List<Categoria> findAllByIds(Set<Integer> categoriaIds) {
        return repository.findAllByCategoriaIdIn(categoriaIds);
    }

    public List<CategoriaSearch> findAllSearch(String chain) {
        return categoriaSearchService.findTop50BySearchLike("%" + chain + "%", Sort.by("nombre").ascending());
    }

    public List<Categoria> findAllDocentes() {
        return repository.findAllByDocente((byte) 1, Sort.by("categoriaId").ascending());
    }

    public List<Categoria> findAllNoDocentes() {
        return repository.findAllByNoDocente((byte) 1, Sort.by("categoriaId").ascending());
    }

    public List<Categoria> findAllAsignables() {
        List<Designacion> designacions = designacionService.findAllAsignables();
        List<Integer> categoriaIds = designacions.stream().map(Designacion::getCategoriaId).collect(Collectors.toList());
        return repository.findAllByCategoriaIdNotIn(categoriaIds);
    }

    public List<Categoria> findAllNoDocenteByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByCategoriaIdIn(categoriaByPeriodoService.findAllNoDocente(anho, mes).stream()
                .distinct().map(CategoriaByPeriodo::getCategoriaId).collect(Collectors.toList()));
    }

    public List<Categoria> findAllNoDocenteByLegajoId(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByCategoriaIdIn(cargoLiquidacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes,
                        findAllNoDocentes().stream().map(Categoria::getCategoriaId)
                                .collect(Collectors.toList()))
                .stream().map(CargoLiquidacion::getCategoriaId).collect(Collectors.toList()));
    }

    public Categoria findByCategoriaId(Integer categoriaId) {
        return repository.findByCategoriaId(categoriaId).orElseThrow(() -> new CategoriaException(categoriaId));
    }

    public Categoria findLast() {
        return repository.findTopByOrderByCategoriaIdDesc().orElseThrow(CategoriaException::new);
    }

    public void delete(Integer categoriaId) {
        repository.deleteById(categoriaId);
    }

    @Transactional
    public Categoria add(Categoria categoria, Integer anho, Integer mes) {
        categoria = repository.save(categoria);
        log.debug("Categoria -> {}", categoria);
        if (anho > 0 && mes > 0) {
            boolean old = false;
            CategoriaPeriodo categoriaPeriodo;
            try {
                categoriaPeriodo = categoriaPeriodoService.findByUnique(categoria.getCategoriaId(), anho, mes);
                old = true;
            } catch (CategoriaPeriodoException e) {
                categoriaPeriodo = new CategoriaPeriodo(null, categoria.getCategoriaId(), anho, mes, "",
                        BigDecimal.ZERO, (byte) 0, (byte) 0, (byte) 0, BigDecimal.ZERO);
            }
            categoriaPeriodo.setNombre(categoria.getNombre());
            categoriaPeriodo.setBasico(categoria.getBasico());
            categoriaPeriodo.setDocente(categoria.getDocente());
            categoriaPeriodo.setNoDocente(categoria.getNoDocente());
            categoriaPeriodo.setLiquidaPorHora(categoria.getLiquidaPorHora());
            categoriaPeriodo.setEstadoDocente(categoria.getEstadoDocente());

            if (old) {
                categoriaPeriodo = categoriaPeriodoService.update(categoriaPeriodo,
                        categoriaPeriodo.getCategoriaPeriodoId());
            } else {
                categoriaPeriodo = categoriaPeriodoService.add(categoriaPeriodo);
            }
        }
        return categoria;
    }

    @Transactional
    public Categoria update(Categoria newCategoria, Integer categoriaId, Integer anho, Integer mes) {
        return repository.findById(categoriaId).map(categoria -> {
            categoria = new Categoria(categoriaId, newCategoria.getNombre(), newCategoria.getBasico(),
                    newCategoria.getDocente(), newCategoria.getNoDocente(), newCategoria.getLiquidaPorHora(), newCategoria.getEstadoDocente());

            if (anho > 0 && mes > 0) {
                boolean old = false;
                CategoriaPeriodo categoriaPeriodo = null;
                try {
                    categoriaPeriodo = categoriaPeriodoService.findByUnique(categoria.getCategoriaId(), anho, mes);
                    old = true;
                } catch (CategoriaPeriodoException e) {
                    categoriaPeriodo = new CategoriaPeriodo(null, categoria.getCategoriaId(), anho, mes, "",
                            BigDecimal.ZERO, (byte) 0, (byte) 0, (byte) 0, BigDecimal.ZERO);
                }
                categoriaPeriodo.setNombre(categoria.getNombre());
                categoriaPeriodo.setBasico(categoria.getBasico());
                categoriaPeriodo.setDocente(categoria.getDocente());
                categoriaPeriodo.setNoDocente(categoria.getNoDocente());
                categoriaPeriodo.setLiquidaPorHora(categoria.getLiquidaPorHora());
                categoriaPeriodo.setEstadoDocente(categoria.getEstadoDocente());

                if (old) {
                    categoriaPeriodo = categoriaPeriodoService.update(categoriaPeriodo,
                            categoriaPeriodo.getCategoriaPeriodoId());
                } else {
                    categoriaPeriodo = categoriaPeriodoService.add(categoriaPeriodo);
                }
            }
            categoria = repository.save(categoria);
            log.debug("Categoria -> {}", categoria);
            return categoria;
        }).orElseThrow(() -> new CategoriaException(categoriaId));
    }

    @Transactional
    public List<Categoria> saveAll(List<Categoria> categorias, Integer anho, Integer mes) {

        class CategoriaPeriodoPk {
            static String categoriaPeriodoKey(CategoriaPeriodo categoriaPeriodo) {
                return categoriaPeriodoKey(categoriaPeriodo.getCategoriaId(), categoriaPeriodo.getAnho(), categoriaPeriodo.getMes());
            }

            static String categoriaPeriodoKey(Integer categoriaId, Integer anho, Integer mes) {
                return categoriaId + "." + anho + "." + mes;
            }
        }

        Map<String, CategoriaPeriodo> mapPeriodos = new HashMap<>();
        for (CategoriaPeriodo categoriaPeriodo : categoriaPeriodoService.findAllByAnhoAndMes(anho, mes)) {
            mapPeriodos.put(CategoriaPeriodoPk.categoriaPeriodoKey(categoriaPeriodo), categoriaPeriodo);
        }
        List<CategoriaPeriodo> categoriaPeriodos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            Long categoriaPeriodoId = null;
            String key = "";
            if (mapPeriodos.containsKey(key = CategoriaPeriodoPk.categoriaPeriodoKey(categoria.getCategoriaId(), anho, mes)))
                categoriaPeriodoId = mapPeriodos.get(key).getCategoriaPeriodoId();
            CategoriaPeriodo categoriaPeriodo = new CategoriaPeriodo(categoriaPeriodoId, categoria.getCategoriaId(),
                    anho, mes, categoria.getNombre(), categoria.getBasico(), categoria.getDocente(),
                    categoria.getNoDocente(), categoria.getLiquidaPorHora(), categoria.getEstadoDocente());
            categoriaPeriodos.add(categoriaPeriodo);
        }
        categorias = repository.saveAll(categorias);
        log.debug("Categorias Registradas -> {}", categorias);
        categoriaPeriodos = categoriaPeriodoService.saveAll(categoriaPeriodos);
        log.debug("Categorias Periodo Registradas -> {}", categoriaPeriodos);
        return categorias;
    }

    @Transactional
    public void upload(FileInfo fileInfo, Integer anho, Integer mes) throws TituloNotFoundException {
        Map<Integer, Categoria> categorias = this.findAll().stream()
                .collect(Collectors.toMap(Categoria::getCategoriaId, categoria -> categoria));
        File file = Tool.writeFile(fileInfo);

        // Procesa Excel
        try {
            InputStream input = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            int cols = (int) sheet.getRow(0).getLastCellNum();
            Row row = sheet.getRow(0);
            Integer columnCategoriaId = null;
            Integer columnNombre = null;
            Integer columnBasico = null;
            Integer columnDocente = null;
            Integer columnNoDocente = null;
            Integer columnLiquidaPorHora = null;
            Integer columnEstadoDocente = null;
            for (int column = 0; column < cols; column++) {
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
                if (columnName.equals("categoria_id"))
                    columnCategoriaId = column;
                if (columnName.equals("nombre"))
                    columnNombre = column;
                if (columnName.equals("basico"))
                    columnBasico = column;
                if (columnName.equals("docente"))
                    columnDocente = column;
                if (columnName.equals("no_docente"))
                    columnNoDocente = column;
                if (columnName.equals("liquida_por_hora"))
                    columnLiquidaPorHora = column;
                if (columnName.equals("estado_docente"))
                    columnEstadoDocente = column;
            }
            for (int rowNumber = 1; rowNumber <= rows; rowNumber++) {
                Double cellCategoriaId = null;
                String cellNombre = null;
                Double cellBasico = null;
                Double cellDocente = null;
                Double cellNoDocente = null;
                Double cellLiquidaPorHora = null;
                Double cellEstadoDocente = null;
                row = sheet.getRow(rowNumber);
                if (columnCategoriaId != null) {
                    cellCategoriaId = row.getCell(columnCategoriaId).getNumericCellValue();
                }
                if (columnNombre != null) {
                    cellNombre = row.getCell(columnNombre).getStringCellValue();
                }
                if (columnBasico != null) {
                    cellBasico = row.getCell(columnBasico).getNumericCellValue();
                }
                if (columnDocente != null) {
                    cellDocente = row.getCell(columnDocente).getNumericCellValue();
                }
                if (columnNoDocente != null) {
                    cellNoDocente = row.getCell(columnNoDocente).getNumericCellValue();
                }
                if (columnLiquidaPorHora != null) {
                    cellLiquidaPorHora = row.getCell(columnLiquidaPorHora).getNumericCellValue();
                }
                if (columnEstadoDocente != null) {
                    cellEstadoDocente = row.getCell(columnEstadoDocente).getNumericCellValue();
                }
                if (cellCategoriaId > 0) {
                    Integer categoriaId = cellCategoriaId.intValue();
                    String nombre = cellNombre;
                    BigDecimal basico = BigDecimal.valueOf(cellBasico.doubleValue());
                    byte docente = cellDocente.byteValue();
                    byte noDocente = cellNoDocente.byteValue();
                    byte liquidaPorHora = cellLiquidaPorHora.byteValue();
                    BigDecimal estadoDocente = BigDecimal.valueOf(cellEstadoDocente.doubleValue());
                    if (categorias.containsKey(categoriaId)) {
                        categorias.replace(categoriaId,
                                new Categoria(categoriaId, nombre, basico, docente, noDocente, liquidaPorHora, estadoDocente));
                    } else {
                        categorias.put(categoriaId,
                                new Categoria(categoriaId, nombre, basico, docente, noDocente, liquidaPorHora, estadoDocente));
                    }
                }
            }
            workbook.close();
            input.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        this.saveAll(new ArrayList<>(categorias.values()), anho, mes);
    }

}
