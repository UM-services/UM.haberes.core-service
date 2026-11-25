package um.haberes.core.service.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.service.NovedadUploadService;
import um.haberes.core.util.transfer.NovedadesTransferencia;

@ExtendWith(MockitoExtension.class)
class NovedadFileServiceTest {

    @Mock
    private NovedadService novedadService;
    @Mock
    private NovedadUploadService novedadUploadService;
    @Mock
    private CodigoService codigoService;
    @Mock
    private PersonaService personaService;

    @InjectMocks
    private NovedadFileService service;

    private static Novedad novedad(Long legajoId, Integer anho, Integer mes, Integer codigoId, Long novedadId) {
        return Novedad.builder()
                .novedadId(novedadId)
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .codigoId(codigoId)
                .dependenciaId(null)
                .importe(new BigDecimal("100.00"))
                .value("")
                .build();
    }

    @Test
    void transfer_copiaUnaVezPorClaveYReutilizaElIdDelDestino() {
        when(codigoService.findAllByTransferible((byte) 1))
                .thenReturn(List.of(Codigo.builder().codigoId(7).transferible((byte) 1).build()));
        // Destino: la clave del legajo 1001 ya existe con novedad_id 99.
        when(novedadService.findAllByPeriodo(2026, 11))
                .thenReturn(List.of(novedad(1001L, 2026, 11, 7, 99L)));
        // Origen: 1001 (existe en el destino), 1002 (nueva) y 1001 repetida (el único permite
        // duplicados con dependencia NULL).
        when(novedadService.findAllByCodigos(List.of(7), 2026, 10)).thenReturn(List.of(
                novedad(1001L, 2026, 10, 7, 501L),
                novedad(1002L, 2026, 10, 7, 502L),
                novedad(1001L, 2026, 10, 7, 503L)));

        NovedadesTransferencia resultado = service.transfer(2026, 11);

        assertEquals(1, resultado.nuevas());
        assertEquals(1, resultado.actualizadas());
        assertEquals(0, resultado.sinCambios());
        assertEquals(2, resultado.total());

        ArgumentCaptor<List<Novedad>> captor = ArgumentCaptor.forClass(List.class);
        verify(novedadService).saveAll(captor.capture());
        List<Novedad> guardadas = captor.getValue();
        assertEquals(2, guardadas.size());
        assertEquals(99L, guardadas.get(0).getNovedadId(), "la fila existente se actualiza por id");
        assertEquals(2026, guardadas.get(0).getAnho());
        assertEquals(11, guardadas.get(0).getMes());
        assertEquals("Transferido", guardadas.get(0).getObservaciones());
        assertEquals((byte) 1, guardadas.get(0).getImportado());
        assertEquals(null, guardadas.get(1).getNovedadId(), "la fila nueva se inserta");
    }

    @Test
    void transfer_reejecucionIdenticaNoEscribeNada() {
        when(codigoService.findAllByTransferible((byte) 1))
                .thenReturn(List.of(Codigo.builder().codigoId(7).transferible((byte) 1).build()));
        // Destino ya transferido: mismos importe/value, observaciones y flags que escribiría.
        Novedad yaTransferida = novedad(1001L, 2026, 11, 7, 99L);
        yaTransferida.setObservaciones("Transferido");
        yaTransferida.setImportado((byte) 1);
        when(novedadService.findAllByPeriodo(2026, 11)).thenReturn(List.of(yaTransferida));
        when(novedadService.findAllByCodigos(List.of(7), 2026, 10))
                .thenReturn(List.of(novedad(1001L, 2026, 10, 7, 501L)));

        NovedadesTransferencia resultado = service.transfer(2026, 11);

        assertEquals(0, resultado.nuevas());
        assertEquals(0, resultado.actualizadas());
        assertEquals(1, resultado.sinCambios());
        assertEquals(1, resultado.total());
        verify(novedadService, never()).saveAll(anyList());
    }

    @Test
    void transfer_sinCodigosTransferiblesNoConsultaNovedades() {
        when(codigoService.findAllByTransferible((byte) 1)).thenReturn(List.of());

        NovedadesTransferencia resultado = service.transfer(2026, 11);

        assertEquals(0, resultado.total());
        verify(novedadService, never()).findAllByPeriodo(any(), any());
        verify(novedadService, never()).findAllByCodigos(any(), any(), any());
        verify(novedadService, never()).saveAll(anyList());
    }
}
