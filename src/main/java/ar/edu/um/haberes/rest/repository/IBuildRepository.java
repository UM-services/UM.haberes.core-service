/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import ar.edu.um.haberes.rest.kotlin.model.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface IBuildRepository extends JpaRepository<Build, Long> {

	public Optional<Build> findTopByOrderByBuildDesc();

}
