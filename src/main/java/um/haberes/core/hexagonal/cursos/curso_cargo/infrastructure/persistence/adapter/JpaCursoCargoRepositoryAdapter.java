package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.entity.CursoCargoEntity;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.mapper.CursoCargoMapper;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.repository.JpaCursoCargoRepository;

@Component
@RequiredArgsConstructor
public class JpaCursoCargoRepositoryAdapter implements CursoCargoRepository {

    private static final Sort LEGAJO_ORDER = Sort.by("curso.facultadId").ascending()
            .and(Sort.by("curso.geograficaId").ascending())
            .and(Sort.by("cargoTipoId").ascending())
            .and(Sort.by("curso.nombre").ascending());

    private static final Sort CURSO_ORDER = Sort.by("cargoTipo.aCargo").descending()
            .and(Sort.by("cargoTipoId").ascending());

    private static final Sort ADICIONAL_ORDER = Sort.by("cursoCargoId");

    private final JpaCursoCargoRepository jpaCursoCargoRepository;
    private final CursoCargoMapper cursoCargoMapper;

    @Override
    public CursoCargo save(CursoCargo cursoCargo) {
        return cursoCargoMapper
                .toDomain(jpaCursoCargoRepository.save(cursoCargoMapper.toEntity(cursoCargo)));
    }

    @Override
    public List<CursoCargo> saveAll(List<CursoCargo> cursoCargos) {
        List<CursoCargoEntity> entities = cursoCargos.stream()
                .map(cursoCargoMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCursoCargoRepository.saveAll(entities));
    }

    @Override
    public Optional<CursoCargo> findByCursoCargoId(Long cursoCargoId) {
        return jpaCursoCargoRepository.findByCursoCargoId(cursoCargoId).map(cursoCargoMapper::toDomain);
    }

    @Override
    public Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
            Integer mes, Integer cargoTipoId, Long legajoId) {
        return jpaCursoCargoRepository
                .findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId)
                .map(cursoCargoMapper::toDomain);
    }

    @Override
    public Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndLegajoId(Long cursoId, Integer anho, Integer mes,
            Long legajoId) {
        return jpaCursoCargoRepository.findByCursoIdAndAnhoAndMesAndLegajoId(cursoId, anho, mes, legajoId)
                .map(cursoCargoMapper::toDomain);
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes,
                LEGAJO_ORDER));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoNivelId(Long legajoId, Integer anho, Integer mes,
            Integer nivelId) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoNivelId(legajoId, anho, mes,
                nivelId, LEGAJO_ORDER));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoNivelIdIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> nivelIds) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoNivelIdIn(legajoId, anho,
                mes, nivelIds, LEGAJO_ORDER));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndDesarraigo(Long legajoId, Integer anho, Integer mes,
            Byte desarraigo) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndDesarraigo(legajoId, anho, mes,
                desarraigo));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoAdicionalCargaHoraria(Long legajoId, Integer anho,
            Integer mes, Byte adicionalCargaHoraria) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoAdicionalCargaHoraria(
                legajoId, anho, mes, adicionalCargaHoraria, ADICIONAL_ORDER));
    }

    @Override
    public List<CursoCargo> findAllByCursoIdAndAnhoAndMes(Long cursoId, Integer anho, Integer mes) {
        return toDomainList(
                jpaCursoCargoRepository.findAllByCursoIdAndAnhoAndMes(cursoId, anho, mes, CURSO_ORDER));
    }

    @Override
    public List<CursoCargo> findTopByCursoId(Long cursoId) {
        return toDomainList(jpaCursoCargoRepository.findTopByCursoId(cursoId));
    }

    @Override
    public List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes) {
        return toDomainList(jpaCursoCargoRepository.findAllByAnhoAndMes(anho, mes));
    }

    @Override
    public List<CursoCargo> findTopByAnhoAndMes(Integer anho, Integer mes) {
        return toDomainList(jpaCursoCargoRepository.findTopByAnhoAndMes(anho, mes));
    }

    @Override
    public List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
        return toDomainList(jpaCursoCargoRepository.findAllByAnhoAndMesAndDesarraigo(anho, mes, desarraigo));
    }

    @Override
    public List<CursoCargo> findAllByCursoIdIn(List<Long> cursoIds) {
        return toDomainList(jpaCursoCargoRepository.findAllByCursoIdIn(cursoIds));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return toDomainList(jpaCursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(legajoId, anho,
                mes, facultadId));
    }

    @Override
    public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCargoTipoIdAndCursoFacultadIdAndCursoGeograficaIdAndCursoAnualAndCursoSemestre1AndCursoSemestre2(
            Long legajoId, Integer anho, Integer mes, Integer cargoTipoId, Integer facultadId, Integer geograficaId,
            Byte anual, Byte semestre1, Byte semestre2) {
        return toDomainList(jpaCursoCargoRepository
                .findAllByLegajoIdAndAnhoAndMesAndCargoTipoIdAndCursoFacultadIdAndCursoGeograficaIdAndCursoAnualAndCursoSemestre1AndCursoSemestre2(
                        legajoId, anho, mes, cargoTipoId, facultadId, geograficaId, anual, semestre1, semestre2));
    }

    @Override
    public void deleteByCursoCargoId(Long cursoCargoId) {
        jpaCursoCargoRepository.deleteByCursoCargoId(cursoCargoId);
    }

    @Override
    public void deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho, Integer mes,
            Integer cargoTipoId, Long legajoId) {
        jpaCursoCargoRepository.deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId,
                legajoId);
    }

    private List<CursoCargo> toDomainList(List<CursoCargoEntity> entities) {
        return entities.stream()
                .map(cursoCargoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
