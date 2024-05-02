package um.haberes.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import um.haberes.rest.kotlin.model.Clase
import java.util.Optional

interface ClaseRepository : JpaRepository<Clase?, Int?> {

    fun findTopByOrderByClaseIdDesc(): Optional<Clase?>?

    fun findByClaseId(claseId: Int?): Optional<Clase?>?
}
