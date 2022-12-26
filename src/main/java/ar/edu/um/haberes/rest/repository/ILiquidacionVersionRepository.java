/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.LiquidacionVersion;

/**
 * @author daniel
 *
 */
@Repository
public interface ILiquidacionVersionRepository extends JpaRepository<LiquidacionVersion, Long>{

}
