package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.mapper.EscalaHoraEtecMapper;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.repository.JpaEscalaHoraEtecRepository;

@Component
@RequiredArgsConstructor
public class JpaEscalaHoraEtecRepositoryAdapter implements EscalaHoraEtecRepository {

    private final JpaEscalaHoraEtecRepository repository;

    private final EscalaHoraEtecMapper mapper;

    @Override
    public List<EscalaHoraEtec> findAllByControlEtecId(Long controlEtecId) {
        return repository.findAllByControlEtecIdOrderByHorasDesdeAsc(controlEtecId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * El derived delete deja los DELETE encolados y Hibernate ejecuta los INSERT antes que
     * los DELETE en el flush: al reemplazar la escala, el tramo que arranca en 0 se insertaría
     * mientras el anterior sigue en la tabla y chocaría con `uq_etec_hora_escala_desde`.
     * El flush fuerza el borrado antes de que el caso de uso inserte los tramos nuevos.
     */
    @Override
    public void deleteAllByControlEtecId(Long controlEtecId) {
        repository.deleteAllByControlEtecId(controlEtecId);
        repository.flush();
    }

    @Override
    public List<EscalaHoraEtec> saveAll(List<EscalaHoraEtec> tramos) {
        return repository.saveAll(tramos.stream().map(mapper::toEntity).toList()).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
