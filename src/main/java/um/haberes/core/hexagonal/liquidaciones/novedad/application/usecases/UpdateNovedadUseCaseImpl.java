package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.UpdateNovedadUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class UpdateNovedadUseCaseImpl implements UpdateNovedadUseCase {

    private final NovedadRepository novedadRepository;

    @Transactional
    @Override
    public Optional<Novedad> updateNovedad(Long novedadId, Novedad novedad) {
        return novedadRepository.findByNovedadId(novedadId).map(existing -> {
            Novedad.NovedadBuilder builder = Novedad.builder()
                    .novedadId(novedadId)
                    .legajoId(novedad.getLegajoId())
                    .anho(novedad.getAnho())
                    .mes(novedad.getMes())
                    .codigoId(novedad.getCodigoId())
                    .dependenciaId(novedad.getDependenciaId())
                    .observaciones(novedad.getObservaciones())
                    .importado(novedad.getImportado())
                    .novedadUploadId(novedad.getNovedadUploadId());
            if (novedad.getImporte() != null) {
                builder.importe(novedad.getImporte());
            }
            if (novedad.getValue() != null) {
                builder.value(novedad.getValue());
            }
            return novedadRepository.save(builder.build());
        });
    }
}
