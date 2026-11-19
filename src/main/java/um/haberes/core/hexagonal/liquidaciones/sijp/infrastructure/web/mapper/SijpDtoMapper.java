package um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.mapper;

import java.util.Base64;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.LegajoOmitidoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.dto.LegajoOmitidoResponse;
import um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.dto.SijpResponse;

@Component
public class SijpDtoMapper {

    public SijpResponse toResponse(ArchivoSijp archivo) {
        return SijpResponse.builder()
                .nombreArchivo(archivo.getNombreArchivo())
                .variante(archivo.getVariante() == null ? null : archivo.getVariante().name())
                .contenido(Base64.getEncoder().encodeToString(archivo.getContenido()))
                .cantidadRegistros(archivo.getCantidad())
                .omitidos(archivo.getOmitidos().stream().map(this::toResponse).toList())
                .advertencias(archivo.getAdvertencias())
                .build();
    }

    private LegajoOmitidoResponse toResponse(LegajoOmitidoSijp omitido) {
        return LegajoOmitidoResponse.builder()
                .legajoId(omitido.getLegajoId())
                .apellidoNombre(omitido.getApellidoNombre())
                .motivo(omitido.getMotivo())
                .build();
    }

}
