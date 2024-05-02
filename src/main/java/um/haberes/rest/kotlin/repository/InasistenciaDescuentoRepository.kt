package um.haberes.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import um.haberes.rest.kotlin.model.InasistenciaDescuento
import java.util.Optional

interface InasistenciaDescuentoRepository : JpaRepository<InasistenciaDescuento?, Long?> {

    fun findByFacultadIdAndGeograficaIdAndDesdeGreaterThanEqualAndHastaLessThanEqual(
        facultadId: Int,
        geograficaId: Int,
        inasistencias: Int,
        inasistencias1: Int
    ): Optional<InasistenciaDescuento?>?;

}