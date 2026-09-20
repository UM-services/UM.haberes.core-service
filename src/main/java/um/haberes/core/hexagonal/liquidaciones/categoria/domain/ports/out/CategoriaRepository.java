package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface CategoriaRepository {

    List<Categoria> findAll();

    List<Categoria> findAllByCategoriaIdIn(List<Integer> categoriaIds);

    List<Categoria> findAllByCategoriaIdNotIn(List<Integer> categoriaIds);

    List<Categoria> findAllByDocente(Byte docente);

    List<Categoria> findAllByNoDocente(Byte noDocente);

    Optional<Categoria> findByCategoriaId(Integer categoriaId);

    Optional<Categoria> findLast();

    Categoria save(Categoria categoria);

    List<Categoria> saveAll(List<Categoria> categorias);

    void deleteByCategoriaId(Integer categoriaId);
}
