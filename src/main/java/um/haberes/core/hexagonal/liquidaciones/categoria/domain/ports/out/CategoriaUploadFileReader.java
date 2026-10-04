package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;

public interface CategoriaUploadFileReader {

    List<Categoria> readCategorias(UploadedFile file);
}
