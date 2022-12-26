/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.Situacion;

/**
 * @author daniel
 *
 */
public interface ISituacionRepository extends JpaRepository<Situacion, Integer> {

}
