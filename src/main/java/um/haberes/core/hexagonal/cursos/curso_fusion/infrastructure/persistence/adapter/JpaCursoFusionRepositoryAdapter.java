package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.entity.CursoFusionEntity;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.mapper.CursoFusionMapper;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.repository.JpaCursoFusionRepository;

@Component
@RequiredArgsConstructor
public class JpaCursoFusionRepositoryAdapter implements CursoFusionRepository {

    private final JpaCursoFusionRepository jpaCursoFusionRepository;
    private final CursoFusionMapper cursoFusionMapper;

    @Override
    public List<CursoFusion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaCursoFusionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes));
    }

    @Override
    public List<CursoFusion> findAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
        return toDomainList(
                jpaCursoFusionRepository.findAllByLegajoIdAndAnhoAndMesAndFacultadId(legajoId, anho, mes, facultadId));
    }

    @Override
    public List<CursoFusion> findAllByPeriodo(Integer anho, Integer mes) {
        return toDomainList(jpaCursoFusionRepository.findAllByAnhoAndMes(anho, mes));
    }

    @Override
    public CursoFusion save(CursoFusion cursoFusion) {
        return cursoFusionMapper.toDomain(jpaCursoFusionRepository.save(cursoFusionMapper.toEntity(cursoFusion)));
    }

    @Override
    public List<CursoFusion> saveAll(List<CursoFusion> cursoFusiones) {
        if (cursoFusiones == null || cursoFusiones.isEmpty()) {
            return List.of();
        }
        List<CursoFusionEntity> entities = cursoFusiones.stream()
                .map(cursoFusionMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCursoFusionRepository.saveAll(entities));
    }

    @Override
    public void deleteByCursoFusionId(Long cursoFusionId) {
        jpaCursoFusionRepository.deleteByCursoFusionId(cursoFusionId);
    }

    @Override
    public void deleteAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId) {
        jpaCursoFusionRepository.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes,
                facultadId, geograficaId);
    }

    @Override
    public void deleteAllByLegajos(List<Long> legajoIds, Integer anho, Integer mes) {
        jpaCursoFusionRepository.deleteAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes);
    }

    @Override
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        jpaCursoFusionRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    private List<CursoFusion> toDomainList(List<CursoFusionEntity> entities) {
        return entities.stream()
                .map(cursoFusionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
