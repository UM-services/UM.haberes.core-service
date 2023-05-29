/**
 * 
 */
package um.haberes.rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CategoriaNotFoundException;
import um.haberes.rest.exception.CategoriaPeriodoNotFoundException;
import um.haberes.rest.exception.common.TituloNotFoundException;
import um.haberes.rest.model.Categoria;
import um.haberes.rest.model.CategoriaPeriodo;
import um.haberes.rest.model.Designacion;
import um.haberes.rest.model.pk.CategoriaPeriodoPk;
import um.haberes.rest.model.view.CategoriaSearch;
import um.haberes.rest.repository.ICargoLiquidacionRepository;
import um.haberes.rest.repository.ICategoriaRepository;
import um.haberes.rest.service.view.CategoriaByPeriodoService;
import um.haberes.rest.service.view.CategoriaSearchService;
import um.haberes.rest.util.Tool;
import um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class CategoriaService {

	@Autowired
	private ICategoriaRepository repository;

	@Autowired
	private ICargoLiquidacionRepository cargoLiquidacionRepository;

	@Autowired
	private CategoriaPeriodoService categoriaPeriodoService;

	@Autowired
	private CategoriaSearchService categoriaSearchService;

	@Autowired
	private CategoriaByPeriodoService categoriaByPeriodoService;

	@Autowired
	private DesignacionService designacionService;

	public List<Categoria> findAll() {
		return repository.findAll();
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
		List<Integer> categoriaIds = designacions.stream().map(d -> d.getCategoriaId()).collect(Collectors.toList());
		return repository.findAllByCategoriaIdNotIn(categoriaIds);
	}

	public List<Categoria> findAllNoDocenteByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByCategoriaIdIn(categoriaByPeriodoService.findAllNoDocente(anho, mes).stream()
				.distinct().map(categoria -> categoria.getCategoriaId()).collect(Collectors.toList()));
	}

	public List<Categoria> findAllNoDocenteByLegajoId(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByCategoriaIdIn(cargoLiquidacionRepository
				.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes,
						findAllNoDocentes().stream().map(categoria -> categoria.getCategoriaId())
								.collect(Collectors.toList()))
				.stream().map(categoria -> categoria.getCategoriaId()).collect(Collectors.toList()));
	}

	public Categoria findByCategoriaId(Integer categoriaId) {
		return repository.findByCategoriaId(categoriaId).orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
	}

	public Categoria findLast() {
		return repository.findTopByOrderByCategoriaIdDesc().orElseThrow(() -> new CategoriaNotFoundException());
	}

	public void delete(Integer categoriaId) {
		repository.deleteById(categoriaId);
	}

	@Transactional
	public Categoria add(Categoria categoria, Integer anho, Integer mes) {
		categoria = repository.save(categoria);
		log.debug("Categoria -> {}", categoria);
		if (anho > 0 && mes > 0) {
			Boolean old = false;
			CategoriaPeriodo categoriaPeriodo = null;
			try {
				categoriaPeriodo = categoriaPeriodoService.findByUnique(categoria.getCategoriaId(), anho, mes);
				old = true;
			} catch (CategoriaPeriodoNotFoundException e) {
				categoriaPeriodo = new CategoriaPeriodo(null, categoria.getCategoriaId(), anho, mes, "",
						BigDecimal.ZERO, (byte) 0, (byte) 0, (byte) 0);
			}
			categoriaPeriodo.setNombre(categoria.getNombre());
			categoriaPeriodo.setBasico(categoria.getBasico());
			categoriaPeriodo.setDocente(categoria.getDocente());
			categoriaPeriodo.setNoDocente(categoria.getNoDocente());
			categoriaPeriodo.setLiquidaPorHora(categoria.getLiquidaPorHora());

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
					newCategoria.getDocente(), newCategoria.getNoDocente(), newCategoria.getLiquidaPorHora());

			if (anho > 0 && mes > 0) {
				Boolean old = false;
				CategoriaPeriodo categoriaPeriodo = null;
				try {
					categoriaPeriodo = categoriaPeriodoService.findByUnique(categoria.getCategoriaId(), anho, mes);
					old = true;
				} catch (CategoriaPeriodoNotFoundException e) {
					categoriaPeriodo = new CategoriaPeriodo(null, categoria.getCategoriaId(), anho, mes, "",
							BigDecimal.ZERO, (byte) 0, (byte) 0, (byte) 0);
				}
				categoriaPeriodo.setNombre(categoria.getNombre());
				categoriaPeriodo.setBasico(categoria.getBasico());
				categoriaPeriodo.setDocente(categoria.getDocente());
				categoriaPeriodo.setNoDocente(categoria.getNoDocente());
				categoriaPeriodo.setLiquidaPorHora(categoria.getLiquidaPorHora());

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
		}).orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
	}

	@Transactional
	public List<Categoria> saveAll(List<Categoria> categorias, Integer anho, Integer mes) {
		Map<CategoriaPeriodoPk, CategoriaPeriodo> mapPeriodos = new HashMap<CategoriaPeriodoPk, CategoriaPeriodo>();
		for (CategoriaPeriodo categoriaPeriodo : categoriaPeriodoService.findAllByAnhoAndMes(anho, mes)) {
			mapPeriodos.put(new CategoriaPeriodoPk(categoriaPeriodo.getCategoriaId(), categoriaPeriodo.getAnho(),
					categoriaPeriodo.getMes()), categoriaPeriodo);
		}
		List<CategoriaPeriodo> categoriaPeriodos = new ArrayList<CategoriaPeriodo>();
		for (Categoria categoria : categorias) {
			Long categoriaPeriodoId = null;
			if (mapPeriodos.containsKey(new CategoriaPeriodoPk(categoria.getCategoriaId(), anho, mes)))
				categoriaPeriodoId = mapPeriodos.get(new CategoriaPeriodoPk(categoria.getCategoriaId(), anho, mes))
						.getCategoriaPeriodoId();
			CategoriaPeriodo categoriaPeriodo = new CategoriaPeriodo(categoriaPeriodoId, categoria.getCategoriaId(),
					anho, mes, categoria.getNombre(), categoria.getBasico(), categoria.getDocente(),
					categoria.getNoDocente(), categoria.getLiquidaPorHora());
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
			Integer rows = sheet.getLastRowNum();
			Integer cols = (int) sheet.getRow(0).getLastCellNum();
			Row row = sheet.getRow(0);
			Integer columnCategoriaId = null;
			Integer columnNombre = null;
			Integer columnBasico = null;
			Integer columnDocente = null;
			Integer columnNoDocente = null;
			Integer columnLiquidaPorHora = null;
			for (Integer column = 0; column < cols; column++) {
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
			}
			for (Integer rowNumber = 1; rowNumber <= rows; rowNumber++) {
				Double cellCategoriaId = null;
				String cellNombre = null;
				Double cellBasico = null;
				Double cellDocente = null;
				Double cellNoDocente = null;
				Double cellLiquidaPorHora = null;
				row = sheet.getRow(rowNumber);
				if (columnCategoriaId != null) {
					cellCategoriaId = row.getCell(columnCategoriaId).getNumericCellValue();
				}
				if (columnNombre != null)
					cellNombre = row.getCell(columnNombre).getStringCellValue();
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
				if (cellCategoriaId > 0) {
					Integer categoriaId = cellCategoriaId.intValue();
					String nombre = cellNombre;
					BigDecimal basico = new BigDecimal(cellBasico.doubleValue());
					Byte docente = cellDocente.byteValue();
					Byte noDocente = cellNoDocente.byteValue();
					Byte liquidaPorHora = cellLiquidaPorHora.byteValue();
					if (categorias.containsKey(categoriaId)) {
						categorias.replace(categoriaId,
								new Categoria(categoriaId, nombre, basico, docente, noDocente, liquidaPorHora));
					} else {
						categorias.put(categoriaId,
								new Categoria(categoriaId, nombre, basico, docente, noDocente, liquidaPorHora));
					}
				}
			}
			workbook.close();
			input.close();
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		this.saveAll(new ArrayList<Categoria>(categorias.values()), anho, mes);
	}

}
