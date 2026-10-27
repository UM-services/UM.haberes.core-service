package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionVersionRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAllCargoLiquidacionesUseCaseImplTest {

    private static final OffsetDateTime FECHA_DESDE =
            OffsetDateTime.of(2026, 8, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    private static final OffsetDateTime FECHA_HASTA =
            OffsetDateTime.of(2026, 8, 31, 0, 0, 0, 0, ZoneOffset.UTC);

    @Mock
    private CargoLiquidacionRepository cargoLiquidacionRepository;

    @Mock
    private CargoLiquidacionVersionRepository cargoLiquidacionVersionRepository;

    @Mock
    private AuditBulkService auditBulk;

    @Mock
    private AuditBulkScope scope;

    @Test
    void siempreEscribeVersionesYCalculaElSiguienteNumeroPorLegajoYPeriodo() {
        List<CargoLiquidacion> cargos = List.of(cargo(1L), cargo(2L));
        when(auditBulk.begin(any())).thenReturn(scope);
        when(cargoLiquidacionRepository.saveAll(cargos)).thenReturn(cargos);
        when(cargoLiquidacionVersionRepository.findLastVersion(12345L, 2026, 8))
                .thenReturn(Optional.of(version(3)));

        useCase().saveAllCargoLiquidaciones(cargos, null);

        verify(cargoLiquidacionVersionRepository).findLastVersion(12345L, 2026, 8);
        List<CargoLiquidacionVersion> saved = captureVersions();
        assertThat(saved).hasSize(2);
        assertThat(saved).allSatisfy(version -> assertThat(version.getVersion()).isEqualTo(4));
        assertThat(saved).allSatisfy(version -> {
            assertThat(version.getLegajoId()).isEqualTo(12345L);
            assertThat(version.getAnho()).isEqualTo(2026);
            assertThat(version.getMes()).isEqualTo(8);
            assertThat(version.getFechaDesde()).isEqualTo(FECHA_DESDE);
            assertThat(version.getFechaHasta()).isEqualTo(FECHA_HASTA);
        });
        verify(scope).close();
    }

    @Test
    void sinVersionPreviaEmpiezaEnUno() {
        List<CargoLiquidacion> cargos = List.of(cargo(1L));
        when(auditBulk.begin(any())).thenReturn(scope);
        when(cargoLiquidacionRepository.saveAll(cargos)).thenReturn(cargos);
        when(cargoLiquidacionVersionRepository.findLastVersion(12345L, 2026, 8))
                .thenReturn(Optional.empty());

        useCase().saveAllCargoLiquidaciones(cargos, null);

        assertThat(captureVersions()).singleElement()
                .satisfies(version -> assertThat(version.getVersion()).isEqualTo(1));
    }

    @Test
    void versionExplicitaSeRespetaYNoConsultaElContador() {
        List<CargoLiquidacion> cargos = List.of(cargo(1L));
        when(auditBulk.begin(any())).thenReturn(scope);
        when(cargoLiquidacionRepository.saveAll(cargos)).thenReturn(cargos);

        useCase().saveAllCargoLiquidaciones(cargos, 7);

        verify(cargoLiquidacionVersionRepository, never()).findLastVersion(any(), any(), any());
        assertThat(captureVersions()).singleElement()
                .satisfies(version -> assertThat(version.getVersion()).isEqualTo(7));
    }

    @Test
    void calculaUnaVersionPorCadaLegajoDelLote() {
        CargoLiquidacion primero = cargo(1L);
        CargoLiquidacion otro = cargoConLegajo(88888L);
        List<CargoLiquidacion> cargos = List.of(primero, otro);
        when(auditBulk.begin(any())).thenReturn(scope);
        when(cargoLiquidacionRepository.saveAll(cargos)).thenReturn(cargos);
        when(cargoLiquidacionVersionRepository.findLastVersion(12345L, 2026, 8))
                .thenReturn(Optional.of(version(2)));
        when(cargoLiquidacionVersionRepository.findLastVersion(88888L, 2026, 8))
                .thenReturn(Optional.empty());

        useCase().saveAllCargoLiquidaciones(cargos, null);

        List<CargoLiquidacionVersion> saved = captureVersions();
        assertThat(saved).hasSize(2);
        assertThat(saved).extracting(CargoLiquidacionVersion::getVersion).containsExactly(3, 1);
    }

    @Test
    void listaVaciaNoAbreScopeNiEscribeVersiones() {
        when(cargoLiquidacionRepository.saveAll(List.of())).thenReturn(List.of());

        useCase().saveAllCargoLiquidaciones(List.of(), null);

        verifyNoInteractions(auditBulk);
        verifyNoInteractions(cargoLiquidacionVersionRepository);
    }

    private SaveAllCargoLiquidacionesUseCaseImpl useCase() {
        return new SaveAllCargoLiquidacionesUseCaseImpl(cargoLiquidacionRepository,
                cargoLiquidacionVersionRepository, auditBulk);
    }

    @SuppressWarnings("unchecked")
    private List<CargoLiquidacionVersion> captureVersions() {
        ArgumentCaptor<List<CargoLiquidacionVersion>> captor = ArgumentCaptor.forClass(List.class);
        verify(cargoLiquidacionVersionRepository).saveAll(captor.capture());
        return captor.getValue();
    }

    private CargoLiquidacion cargo(Long cargoLiquidacionId) {
        return cargoConLegajo(12345L, cargoLiquidacionId);
    }

    private CargoLiquidacion cargoConLegajo(Long legajoId) {
        return cargoConLegajo(legajoId, 1L);
    }

    private CargoLiquidacion cargoConLegajo(Long legajoId, Long cargoLiquidacionId) {
        return CargoLiquidacion.builder()
                .cargoLiquidacionId(cargoLiquidacionId)
                .legajoId(legajoId)
                .anho(2026)
                .mes(8)
                .dependenciaId(1)
                .fechaDesde(FECHA_DESDE)
                .fechaHasta(FECHA_HASTA)
                .categoriaId(2)
                .build();
    }

    private CargoLiquidacionVersion version(Integer version) {
        return CargoLiquidacionVersion.builder()
                .legajoId(12345L)
                .anho(2026)
                .mes(8)
                .version(version)
                .build();
    }
}
