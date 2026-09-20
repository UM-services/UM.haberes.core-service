/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.AnotadorEntity;

/**
 * @author daniel
 */
@Repository
public interface JpaAnotadorRepository extends JpaRepository<AnotadorEntity, Long> {

    List<AnotadorEntity> findAllByLegajoIdOrderByAnotadorIdDesc(Long legajoId);

    List<AnotadorEntity> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
                                                                Byte rechazado);

    List<AnotadorEntity> findTop1000ByAnhoAndMes(Integer anho, Integer mes);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByAnotadorId(Integer anho,
                                                                                                  Integer mes, Byte autorizado, Byte rechazado, List<Long> legajos);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndLegajoIdInOrderByAnotadorIdDesc(Integer anho, Integer mes,
                                                                             List<Long> legajos);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByLegajoId(Integer anho,
                                                                                                Integer mes, Byte autorizado, Byte rechazado, Integer facultadId);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndFacultadIdOrderByAnotadorIdDesc(Integer anho, Integer mes,
                                                                             Integer facultadId);

    Optional<AnotadorEntity> findByAnotadorId(Long anotadorId);

    List<AnotadorEntity> findAllByAnhoAndMesAndAutorizadoAndRechazadoOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado, List<Long> legajos);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado, Integer facultadId);

    List<AnotadorEntity> findTop1000ByAnhoAndMesOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, List<Long> legajos);

    List<AnotadorEntity> findTop1000ByAnhoAndMesAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Integer facultadId);

}
