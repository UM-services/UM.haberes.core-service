package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;

public interface UploadCategoriasUseCase {

    void uploadCategorias(UploadedFile file, Integer anho, Integer mes);
}
