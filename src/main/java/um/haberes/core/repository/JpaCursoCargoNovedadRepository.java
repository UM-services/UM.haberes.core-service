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
import um.haberes.core.model.CursoCargoNovedadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCursoCargoNovedadRepository extends JpaRepository<CursoCargoNovedadEntity, Long> {

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes,
                                                                         Byte autorizado, Byte rechazado);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndAltaAndAutorizadoAndRechazado(Long cursoId,
                                                                                          Integer anho, Integer mes, Byte alta, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndCambioAndAutorizadoAndRechazado(Long cursoId,
                                                                                            Integer anho, Integer mes, Byte cambio, Byte autorizado, Byte rechazado, Sort and);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndLegajoId(Long cursoId, Integer anho,
                                                                                  Integer mes, Byte autorizado, Long legajoId);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndRechazadoAndLegajoId(Long cursoId, Integer anho,
                                                                                 Integer mes, Byte rechazado, Long legajoId);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndAutorizadoAndRechazadoAndLegajoId(Long cursoId,
                                                                                              Integer anho, Integer mes, Byte autorizado, Byte rechazado, Long legajoId);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndAutorizadoAndRechazado(Integer anho, Integer mes, Byte autorizado,
                                                                         Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByCursoIdAndAnhoAndMesAndBajaAndAutorizadoAndRechazado(Long cursoId,
                                                                                          Integer anho, Integer mes, Byte baja, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndAutorizado(Integer anho, Integer mes, Byte autorizado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndRechazado(Integer anho, Integer mes, Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndBajaAndAutorizadoAndRechazado(Integer anho, Integer mes,
                                                                                Byte baja, Byte autorizado, Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndBajaAndAutorizado(Integer anho, Integer mes, Byte baja,
                                                                    Byte autorizado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByAnhoAndMesAndBajaAndRechazado(Integer anho, Integer mes, Byte baja,
                                                                   Byte rechazado, Sort sort);

    List<CursoCargoNovedadEntity> findAllByCursoFacultadIdAndAnhoAndMes(Integer facultadId, Integer anho, Integer mes);

    List<CursoCargoNovedadEntity> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndAltaOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte alta);

    List<CursoCargoNovedadEntity> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndCambioOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte cambio);

    List<CursoCargoNovedadEntity> findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndBajaOrderByCursoNombre(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, Byte baja);

    Optional<CursoCargoNovedadEntity> findByCursoCargoNovedadId(Long cursoCargoNovedadId);

    Optional<CursoCargoNovedadEntity> findByLegajoIdAndCursoIdAndAnhoAndMes(Long legajoId, Long cursoId, Integer anho,
                                                                      Integer mes);

    Optional<CursoCargoNovedadEntity> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
                                                                                    Integer mes, Integer cargoTipoId, Long legajoId);

    @Modifying
    void deleteAllByLegajoIdAndCursoIdAndAnhoAndMesAndAutorizadoAndRechazado(Long legajoId, Long cursoId,
                                                                             Integer anho, Integer mes, Byte autorizado, Byte rechazado);

    @Modifying
    void deleteByCursoCargoNovedadId(Long cursoCargoNovedadId);

}
