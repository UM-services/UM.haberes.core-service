package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.mapper;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacionFiltro;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.FilaAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.LegajoAcreditar;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.PreparacionAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.RechazoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.ArchivoAcreditacionRequest;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.ArchivoAcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.FilaAcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.PreparacionAcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.RechazoAcreditacionResponse;

@Component
public class ArchivoAcreditacionDtoMapper {

    public ArchivoAcreditacionFiltro toDomain(ArchivoAcreditacionRequest request) {
        return ArchivoAcreditacionFiltro.builder()
                .anho(request.getAnho())
                .mes(request.getMes())
                .fechaPago(request.getFechaPago())
                .numEnvio(request.getNumEnvio())
                .salida(request.getSalida())
                .dependenciaId(request.getDependenciaId())
                .codigoId(request.getCodigoId())
                .cci(request.getCci())
                .individual(request.getIndividual())
                .legajos(request.getLegajos() == null ? new ArrayList<>()
                        : request.getLegajos().stream()
                                .map(legajo -> LegajoAcreditar.builder()
                                        .legajoId(legajo.getLegajoId())
                                        .cbu(legajo.getCbu())
                                        .acreditado(legajo.getAcreditado())
                                        .build())
                                .toList())
                .build();
    }

    public ArchivoAcreditacionResponse toResponse(ArchivoAcreditacion archivo) {
        return ArchivoAcreditacionResponse.builder()
                .nombreArchivo(archivo.getNombreArchivo())
                .contenido(Base64.getEncoder().encodeToString(archivo.getContenido()))
                .cantidad(archivo.getCantidad())
                .total(archivo.getTotal())
                .periodoAcreditado(archivo.isPeriodoAcreditado())
                .rechazos(archivo.getRechazos().stream().map(this::toResponse).toList())
                .advertencias(archivo.getAdvertencias())
                .build();
    }

    public RechazoAcreditacionResponse toResponse(RechazoAcreditacion rechazo) {
        return RechazoAcreditacionResponse.builder()
                .legajoId(rechazo.getLegajoId())
                .apellidoNombre(rechazo.getApellidoNombre())
                .motivo(rechazo.getMotivo())
                .importe(rechazo.getImporte())
                .subtotal(rechazo.getSubtotal())
                .build();
    }

    public PreparacionAcreditacionResponse toResponse(PreparacionAcreditacion preparacion) {
        return PreparacionAcreditacionResponse.builder()
                .anho(preparacion.getAnho())
                .mes(preparacion.getMes())
                .totalAcreditado(preparacion.getTotalAcreditado())
                .filas(preparacion.getFilas().stream().map(this::toResponse).toList())
                .sinCbu(preparacion.getSinCbu())
                .sinLiquidacion(preparacion.getSinLiquidacion())
                .build();
    }

    public FilaAcreditacionResponse toResponse(FilaAcreditacion fila) {
        return FilaAcreditacionResponse.builder()
                .legajoBancoId(fila.getLegajoBancoId())
                .legajoId(fila.getLegajoId())
                .cbu(fila.getCbu())
                .fijo(fila.getFijo())
                .porcentaje(fila.getPorcentaje())
                .resto(fila.getResto())
                .acreditado(fila.getAcreditado())
                .apellidoNombre(fila.getApellidoNombre())
                .cuil(fila.getCuil())
                .build();
    }
}
