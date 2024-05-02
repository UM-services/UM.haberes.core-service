package um.haberes.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import um.haberes.rest.kotlin.model.LegajoInasistencia
import java.util.Optional

interface LegajoInasistenciaRepository : JpaRepository<LegajoInasistencia?, Long?> {

    fun findByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(
        legajoId: Long,
        anho: Int,
        mes: Int,
        facultadId: Int,
        geograficaId: Int
    ): Optional<LegajoInasistencia?>?;

}