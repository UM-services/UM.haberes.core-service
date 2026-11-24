package um.haberes.core.repository.view;

import java.util.List;

import um.haberes.core.model.view.NovedadDuplicada;

/**
 * Consulta de novedades duplicadas por período.
 *
 * <p>La vista {@code vw_novedad_duplicada} agrupa toda la tabla {@code novedad} y recién después
 * aplica el filtro de período: sobre 377k filas tarda ~1,75 s por legajo. Esta consulta filtra
 * por {@code anho}/{@code mes} antes de agrupar y usa el índice
 * {@code idx_novedad_periodo_dup} ({@code sql/026}).</p>
 */
public interface JpaNovedadDuplicadaRepositoryCustom {

    List<NovedadDuplicada> findAllByPeriodo(Integer anho, Integer mes);

}
