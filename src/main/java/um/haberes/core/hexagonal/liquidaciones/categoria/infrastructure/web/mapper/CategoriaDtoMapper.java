package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaRequest;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaSearchResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaUploadRequest;

@Component
public class CategoriaDtoMapper {

    public Categoria toDomain(CategoriaRequest request) {
        if (request == null) {
            return null;
        }
        Categoria.CategoriaBuilder builder = Categoria.builder()
                .categoriaId(request.getCategoriaId());
        if (request.getNombre() != null) {
            builder.nombre(request.getNombre());
        }
        if (request.getBasico() != null) {
            builder.basico(request.getBasico());
        }
        if (request.getDocente() != null) {
            builder.docente(request.getDocente());
        }
        if (request.getNoDocente() != null) {
            builder.noDocente(request.getNoDocente());
        }
        if (request.getLiquidaPorHora() != null) {
            builder.liquidaPorHora(request.getLiquidaPorHora());
        }
        if (request.getEstadoDocente() != null) {
            builder.estadoDocente(request.getEstadoDocente());
        }
        return builder.build();
    }

    public CategoriaResponse toResponse(Categoria domain) {
        if (domain == null) {
            return null;
        }
        return CategoriaResponse.builder()
                .categoriaId(domain.getCategoriaId())
                .nombre(domain.getNombre())
                .basico(domain.getBasico())
                .docente(domain.getDocente())
                .noDocente(domain.getNoDocente())
                .liquidaPorHora(domain.getLiquidaPorHora())
                .estadoDocente(domain.getEstadoDocente())
                .build();
    }

    public CategoriaSearchResponse toSearchResponse(CategoriaSearchResult domain) {
        if (domain == null) {
            return null;
        }
        return CategoriaSearchResponse.builder()
                .categoriaId(domain.getCategoriaId())
                .nombre(domain.getNombre())
                .basico(domain.getBasico())
                .search(domain.getSearch())
                .build();
    }

    public UploadedFile toUploadedFile(CategoriaUploadRequest request) {
        if (request == null) {
            return null;
        }
        return UploadedFile.builder()
                .filename(request.getFilename())
                .base64(request.getBase64())
                .build();
    }
}
