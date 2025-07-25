/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Anotador;

/**
 * @author daniel
 */
@Repository
public interface AnotadorRepository extends JpaRepository<Anotador, Long> {

    List<Anotador> findAllByLegajoIdOrderByAnotadorIdDesc(Long legajoId);

    List<Anotador> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
                                                                Byte rechazado);

    List<Anotador> findTop1000ByAnhoAndMes(Integer anho, Integer mes);

    List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByAnotadorId(Integer anho,
                                                                                                  Integer mes, Byte autorizado, Byte rechazado, List<Long> legajos);

    List<Anotador> findTop1000ByAnhoAndMesAndLegajoIdInOrderByAnotadorIdDesc(Integer anho, Integer mes,
                                                                             List<Long> legajos);

    List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByLegajoId(Integer anho,
                                                                                                Integer mes, Byte autorizado, Byte rechazado, Integer facultadId);

    List<Anotador> findTop1000ByAnhoAndMesAndFacultadIdOrderByAnotadorIdDesc(Integer anho, Integer mes,
                                                                             Integer facultadId);

    Optional<Anotador> findByAnotadorId(Long anotadorId);

    List<Anotador> findAllByAnhoAndMesAndAutorizadoAndRechazadoOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado);

    List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado, List<Long> legajos);

    List<Anotador> findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Byte autorizado, Byte rechazado, Integer facultadId);

    List<Anotador> findTop1000ByAnhoAndMesOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes);

    List<Anotador> findTop1000ByAnhoAndMesAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, List<Long> legajos);

    List<Anotador> findTop1000ByAnhoAndMesAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
        Integer anho, Integer mes, Integer facultadId);

}
