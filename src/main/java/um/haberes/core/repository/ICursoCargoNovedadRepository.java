/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CursoCargoNovedad;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoCargoNovedadRepository extends JpaRepository<CursoCargoNovedad, Long> {

    List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes,
                                                                         Byte autorizado, Byte rechazado);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAltaAndAutorizadoAndRechazado(Long cursoId,
                                                                                          Integer anho, Integer mes, Byte alta, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndCambioAndAutorizadoAndRechazado(Long cursoId,
                                                                                            Integer anho, Integer mes, Byte cambio, Byte autorizado, Byte rechazado, Sort and);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndLegajoId(Long cursoId, Integer anho,
                                                                                  Integer mes, Byte autorizado, Long legajoId);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndRechazadoAndLegajoId(Long cursoId, Integer anho,
                                                                                 Integer mes, Byte rechazado, Long legajoId);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndRechazadoAndLegajoId(Long cursoId,
                                                                                              Integer anho, Integer mes, Byte autorizado, Byte rechazado, Long legajoId);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
                                                                         Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByCursoIdAndAnhoAndMesAndBajaAndAutorizadoAndRechazado(Long cursoId,
                                                                                          Integer anho, Integer mes, Byte baja, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndAutorizado(Integer anho, Integer mes, Byte autorizado, Sort sort);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndRechazado(Integer anho, Integer mes, Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndAutorizadoAndRechazado(Integer anho, Integer mes,
                                                                                Byte baja, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndAutorizado(Integer anho, Integer mes, Byte baja,
                                                                    Byte autorizado, Sort sort);

    List<CursoCargoNovedad> findAllByAnhoAndMesAndBajaAndRechazado(Integer anho, Integer mes, Byte baja,
                                                                   Byte rechazado, Sort sort);

    List<CursoCargoNovedad> findAllByCursoFacultadIdAndAnhoAndMes(Integer facultadId, Integer anho, Integer mes);

    List<CursoCargoNovedad> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndAltaOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte alta);

    List<CursoCargoNovedad> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndCambioOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte cambio);

    List<CursoCargoNovedad> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndBajaOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte baja);

    Optional<CursoCargoNovedad> findByCursoCargoNovedadId(Long cursoCargoNovedadId);

    Optional<CursoCargoNovedad> findByLegajoIdAndCursoIdAndAnhoAndMes(Long legajoId, Long cursoId, Integer anho,
                                                                      Integer mes);

    Optional<CursoCargoNovedad> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
                                                                                    Integer mes, Integer cargoTipoId, Long legajoId);

    @Modifying
    void deleteAllByLegajoIdAndCursoIdAndAnhoAndMesAndAutorizadoAndRechazado(Long legajoId, Long cursoId,
                                                                             Integer anho, Integer mes, Byte autorizado, Byte rechazado);

    @Modifying
    void deleteByCursoCargoNovedadId(Long cursoCargoNovedadId);

}
