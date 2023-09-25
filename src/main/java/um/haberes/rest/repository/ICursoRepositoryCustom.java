/**
 * 
 */
package um.haberes.rest.repository;

import um.haberes.rest.kotlin.model.Curso;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface ICursoRepositoryCustom {

	public List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
																	   List<String> conditions);

}
