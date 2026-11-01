package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.UploadCategoriasUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaUploadFileReader;

@Component
@RequiredArgsConstructor
public class UploadCategoriasUseCaseImpl implements UploadCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaPeriodoRepository categoriaPeriodoRepository;
    private final CategoriaUploadFileReader categoriaUploadFileReader;

    @Override
    @Transactional
    public void uploadCategorias(UploadedFile file, Integer anho, Integer mes) {
        List<Categoria> filas = categoriaUploadFileReader.readCategorias(file);
        Map<Integer, Categoria> categorias = new LinkedHashMap<>();
        for (Categoria categoria : categoriaRepository.findAll()) {
            categorias.put(categoria.getCategoriaId(), categoria);
        }
        for (Categoria fila : filas) {
            categorias.put(fila.getCategoriaId(), fila);
        }
        List<Categoria> consolidadas = new ArrayList<>(categorias.values());
        categoriaRepository.saveAll(consolidadas);
        categoriaPeriodoRepository.upsertAll(consolidadas, anho, mes);
    }
}
