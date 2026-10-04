package um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper.FacultadMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.mapper.GeograficaMapper;

@Component
@RequiredArgsConstructor
public class CursoMapper {

    private final FacultadMapper facultadMapper;
    private final GeograficaMapper geograficaMapper;

    public CursoEntity toEntity(Curso domain) {
        if (domain == null) {
            return null;
        }
        CursoEntity entity = new CursoEntity();
        entity.setCursoId(domain.getCursoId());
        entity.setFacultadId(domain.getFacultadId());
        entity.setGeograficaId(domain.getGeograficaId());
        entity.setNivelId(domain.getNivelId());
        if (domain.getNombre() != null) {
            entity.setNombre(domain.getNombre());
        }
        if (domain.getAnual() != null) {
            entity.setAnual(domain.getAnual());
        }
        if (domain.getSemestre1() != null) {
            entity.setSemestre1(domain.getSemestre1());
        }
        if (domain.getSemestre2() != null) {
            entity.setSemestre2(domain.getSemestre2());
        }
        if (domain.getAdicionalCargaHoraria() != null) {
            entity.setAdicionalCargaHoraria(domain.getAdicionalCargaHoraria());
        }
        return entity;
    }

    public Curso toDomain(CursoEntity entity) {
        if (entity == null) {
            return null;
        }
        Curso.CursoBuilder builder = Curso.builder()
                .cursoId(entity.getCursoId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .nivelId(entity.getNivelId())
                .facultad(facultadMapper.toDomainModel(entity.getFacultad()))
                .geografica(geograficaMapper.toDomainModel(entity.getGeografica()));
        if (entity.getNombre() != null) {
            builder.nombre(entity.getNombre());
        }
        if (entity.getAnual() != null) {
            builder.anual(entity.getAnual());
        }
        if (entity.getSemestre1() != null) {
            builder.semestre1(entity.getSemestre1());
        }
        if (entity.getSemestre2() != null) {
            builder.semestre2(entity.getSemestre2());
        }
        if (entity.getAdicionalCargaHoraria() != null) {
            builder.adicionalCargaHoraria(entity.getAdicionalCargaHoraria());
        }
        return builder.build();
    }
}
