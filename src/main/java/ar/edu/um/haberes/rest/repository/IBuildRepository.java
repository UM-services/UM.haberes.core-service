/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.Build;

/**
 * @author daniel
 *
 */
@Repository
public interface IBuildRepository extends JpaRepository<Build, Long> {

	public Optional<Build> findTopByOrderByBuildDesc();

}
