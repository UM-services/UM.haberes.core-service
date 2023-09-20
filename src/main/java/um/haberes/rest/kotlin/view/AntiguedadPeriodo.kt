package um.haberes.rest.kotlin.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import um.haberes.rest.model.Auditable

@Entity
@Table(name = "vw_antiguedad_periodo")
@Immutable
data class AntiguedadPeriodo(

    @Id
    var antiguedadId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var mesesDocentes: Int? = null,
    var mesesAdministrativos: Int? = null,
    var periodo: Long? = null

) : Auditable()
