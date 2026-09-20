package um.haberes.core.hexagonal.liquidaciones.categoria.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.CreateCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.DeleteCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.FindCategoriasBySearchUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetAllCategoriasUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriaByCategoriaIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasAsignablesUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasByCategoriaIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasDocentesUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasNoDocentesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetCategoriasNoDocentesUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.GetLastCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.SaveAllCategoriasUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.UpdateCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.UploadCategoriasUseCase;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final GetAllCategoriasUseCase getAllCategoriasUseCase;
    private final GetCategoriasByCategoriaIdsUseCase getCategoriasByCategoriaIdsUseCase;
    private final FindCategoriasBySearchUseCase findCategoriasBySearchUseCase;
    private final GetCategoriasDocentesUseCase getCategoriasDocentesUseCase;
    private final GetCategoriasNoDocentesUseCase getCategoriasNoDocentesUseCase;
    private final GetCategoriasAsignablesUseCase getCategoriasAsignablesUseCase;
    private final GetCategoriasNoDocentesByPeriodoUseCase getCategoriasNoDocentesByPeriodoUseCase;
    private final GetCategoriasNoDocentesByLegajoUseCase getCategoriasNoDocentesByLegajoUseCase;
    private final GetCategoriaByCategoriaIdUseCase getCategoriaByCategoriaIdUseCase;
    private final GetLastCategoriaUseCase getLastCategoriaUseCase;
    private final CreateCategoriaUseCase createCategoriaUseCase;
    private final UpdateCategoriaUseCase updateCategoriaUseCase;
    private final DeleteCategoriaUseCase deleteCategoriaUseCase;
    private final SaveAllCategoriasUseCase saveAllCategoriasUseCase;
    private final UploadCategoriasUseCase uploadCategoriasUseCase;

    public List<Categoria> findAll() {
        return getAllCategoriasUseCase.getAllCategorias();
    }

    public List<Categoria> findAllByIds(Set<Integer> categoriaIds) {
        return getCategoriasByCategoriaIdsUseCase.getCategoriasByCategoriaIds(categoriaIds);
    }

    public List<CategoriaSearchResult> findAllSearch(String chain) {
        return findCategoriasBySearchUseCase.findCategoriasBySearch(chain);
    }

    public List<Categoria> findAllDocentes() {
        return getCategoriasDocentesUseCase.getCategoriasDocentes();
    }

    public List<Categoria> findAllNoDocentes() {
        return getCategoriasNoDocentesUseCase.getCategoriasNoDocentes();
    }

    public List<Categoria> findAllAsignables() {
        return getCategoriasAsignablesUseCase.getCategoriasAsignables();
    }

    public List<Categoria> findAllNoDocenteByPeriodo(Integer anho, Integer mes) {
        return getCategoriasNoDocentesByPeriodoUseCase.getCategoriasNoDocentesByPeriodo(anho, mes);
    }

    public List<Categoria> findAllNoDocenteByLegajoId(Long legajoId, Integer anho, Integer mes) {
        return getCategoriasNoDocentesByLegajoUseCase.getCategoriasNoDocentesByLegajo(legajoId, anho, mes);
    }

    public Categoria findByCategoriaId(Integer categoriaId) {
        return getCategoriaByCategoriaIdUseCase.getCategoriaByCategoriaId(categoriaId)
                .orElseThrow(() -> new CategoriaException(categoriaId));
    }

    public Categoria findLast() {
        return getLastCategoriaUseCase.getLastCategoria().orElseThrow(CategoriaException::new);
    }

    public void delete(Integer categoriaId) {
        deleteCategoriaUseCase.deleteCategoria(categoriaId);
    }

    public Categoria add(Categoria categoria, Integer anho, Integer mes) {
        Categoria saved = createCategoriaUseCase.createCategoria(categoria, anho, mes);
        log.debug("Categoria -> {}", saved);
        return saved;
    }

    public Categoria update(Categoria newCategoria, Integer categoriaId, Integer anho, Integer mes) {
        Categoria updated = updateCategoriaUseCase.updateCategoria(categoriaId, newCategoria, anho, mes)
                .orElseThrow(() -> new CategoriaException(categoriaId));
        log.debug("Categoria -> {}", updated);
        return updated;
    }

    public List<Categoria> saveAll(List<Categoria> categorias, Integer anho, Integer mes) {
        List<Categoria> saved = saveAllCategoriasUseCase.saveAllCategorias(categorias, anho, mes);
        log.debug("Categorias Registradas -> {}", saved);
        return saved;
    }

    public void upload(UploadedFile file, Integer anho, Integer mes) {
        uploadCategoriasUseCase.uploadCategorias(file, anho, mes);
    }
}
