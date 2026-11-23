package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaFilterRequest;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaPageResponse;
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
        if (request.getPuntos() != null) {
            builder.puntos(request.getPuntos());
        }
        if (request.getEtecHoras() != null) {
            builder.etecHoras(request.getEtecHoras());
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
                .puntos(domain.getPuntos())
                .etecHoras(domain.getEtecHoras())
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

    public CategoriaFilter toFilter(CategoriaFilterRequest request) {
        if (request == null) {
            return CategoriaFilter.builder().build();
        }
        return CategoriaFilter.builder()
                .texto(request.getTexto() == null ? "" : request.getTexto())
                .condiciones(request.getCondiciones() == null ? List.of() : request.getCondiciones())
                .pagina(request.getPagina() == null ? 0 : request.getPagina())
                .tamano(request.getTamano() == null ? CategoriaFilter.TAMANO_POR_DEFECTO
                        : request.getTamano())
                .build();
    }

    public CategoriaPageResponse toPageResponse(CategoriaPage page) {
        if (page == null) {
            return null;
        }
        return CategoriaPageResponse.builder()
                .filas(page.getFilas().stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()))
                .total(page.getTotal())
                .pagina(page.getPagina())
                .tamano(page.getTamano())
                .build();
    }
}
