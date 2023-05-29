/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;

import um.haberes.rest.model.Curso;

/**
 * @author daniel
 *
 */
public interface ICursoRepositoryCustom {

	public List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
			List<String> conditions);

}
