/**
 * 
 */
package um.haberes.core.repository;

import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface JpaCursoRepositoryCustom {

	public List<CursoEntity> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
																	   List<String> conditions);

}
