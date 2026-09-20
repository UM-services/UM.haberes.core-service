package um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.entity.CursoDesarraigoEntity;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.mapper.CursoDesarraigoMapper;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.repository.JpaCursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class JpaCursoDesarraigoRepositoryAdapter implements CursoDesarraigoRepository {

    private final JpaCursoDesarraigoRepository jpaCursoDesarraigoRepository;
    private final CursoDesarraigoMapper cursoDesarraigoMapper;

    @Override
    public List<CursoDesarraigo> findAll() {
        return toDomainList(jpaCursoDesarraigoRepository.findAll());
    }

    @Override
    public CursoDesarraigo save(CursoDesarraigo cursoDesarraigo) {
        return cursoDesarraigoMapper
                .toDomain(jpaCursoDesarraigoRepository.save(cursoDesarraigoMapper.toEntity(cursoDesarraigo)));
    }

    @Override
    public List<CursoDesarraigo> saveAll(List<CursoDesarraigo> cursoDesarraigos) {
        List<CursoDesarraigoEntity> entities = cursoDesarraigos.stream()
                .map(cursoDesarraigoMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCursoDesarraigoRepository.saveAll(entities));
    }

    @Override
    public Optional<CursoDesarraigo> findByCursoDesarraigoId(Long cursoDesarraigoId) {
        return jpaCursoDesarraigoRepository.findByCursoDesarraigoId(cursoDesarraigoId)
                .map(cursoDesarraigoMapper::toDomain);
    }

    @Override
    public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaCursoDesarraigoRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes));
    }

    @Override
    public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMesAndVersion(Long legajoId, Integer anho, Integer mes,
            Integer version) {
        return toDomainList(jpaCursoDesarraigoRepository.findAllByLegajoIdAndAnhoAndMesAndVersion(legajoId, anho, mes,
                version));
    }

    @Override
    public Optional<CursoDesarraigo> findByLegajoIdAndAnhoAndMesAndCursoId(Long legajoId, Integer anho, Integer mes,
            Long cursoId) {
        return jpaCursoDesarraigoRepository.findByLegajoIdAndAnhoAndMesAndCursoId(legajoId, anho, mes, cursoId)
                .map(cursoDesarraigoMapper::toDomain);
    }

    @Override
    public void deleteByCursoDesarraigoId(Long cursoDesarraigoId) {
        jpaCursoDesarraigoRepository.deleteById(cursoDesarraigoId);
    }

    private List<CursoDesarraigo> toDomainList(List<CursoDesarraigoEntity> entities) {
        return entities.stream()
                .map(cursoDesarraigoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
