/**
 * 
 */
package um.haberes.core.repository;

import um.haberes.core.kotlin.model.Curso;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface CursoRepositoryCustom {

	public List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
																	   List<String> conditions);

}
